package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.entity.*;
import org.satel.ressatel.repository.SkillGradeRepository;
import org.satel.ressatel.repository.SkillRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@ApplicationScoped
@Log4j2
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillGradeRepository skillGradeRepository;
    private final RoleToSkillService roleToSkillService;
    private final RoleService roleService;

    @Inject
    public SkillService(SkillRepository skillRepository, SkillGradeRepository skillGradeRepository, RoleToSkillService roleToSkillService, RoleService roleService) {
        this.skillRepository = skillRepository;
        this.skillGradeRepository = skillGradeRepository;
        this.roleToSkillService = roleToSkillService;
        this.roleService = roleService;
    }

    public static Set<Skill> getSkillsWithParents(Set<Skill> skills) {
        Set<Skill> parentsSet = new HashSet<>();
        skills.forEach(skill -> {
            List<Skill> skillList = new ArrayList<>();
            parentsSet.addAll(getParents(skill, skillList));
        });
        Set<Skill> newSet = new HashSet<>(skills);
        newSet.addAll(parentsSet);
        return newSet;
    }

    private static List<Skill> getParents(Skill skill, List<Skill> skills) {
        if (skill.getParent() != null) {
            skills.add(skill.getParent());
            getParents(skill.getParent(), skills);
        }
        return skills;
    }

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    private Skill getByName(String name) {
        return skillRepository.findByName(name);
    }

    public List<Skill> getSkillListByNames(List<String> names) {
        List<Skill> skills = new ArrayList<>();
        names.forEach(name -> skills.add(getByName(name)));
        return skills;
    }

    public Set<Skill> getSkillsByNames(List<String> names) {
        return new HashSet<>(getSkillListByNames(names));
    }

    public List<String> getSkillNames() {
        List<Skill> skills = getSkills();
        List<String> names = new ArrayList<>();
        skills.forEach(skill -> names.add(skill.getName()));
        return names;
    }

    public String getSkillsAsString(Set<Skill> skills) {
        return skills.stream().map(Skill::getName).collect(Collectors.joining("; "));
    }

    public DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> getTreeNodeOfSkills() {
        List<Skill> skills = getSkills();
        return getSkillDefaultTreeNode(skills);
    }

    public DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> getTreeNodeOfSkillsByRole(String roleId) {
        List<Skill> skills = getSkillsByRole(roleId);
        return getSkillDefaultTreeNode(skills);
    }

    private List<Role> getExtraRolesByEmployeeId(String employeeId) {
        return getExtraRoleIdsByEmployeeId(Integer.valueOf(employeeId))
                .stream().map(roleService::getById).collect(Collectors.toList());
    }
    public List<Skill> getSkillsByRole(String roleId) {
        return roleToSkillService.getRoleToSkillByRole(roleService.getById(Integer.valueOf(roleId))).stream().map(RoleToSkill::getSkill).distinct().collect(Collectors.toList());
    }

    private DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> getSkillDefaultTreeNode(List<Skill> skills) {
        DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> root = new DefaultTreeNode<>(new org.satel.ressatel.bean.list.skill.Skill(0, "Компетенции", null, "Folder"), null);

        Map<Integer, Skill> parents = new HashMap<>();
        Map<Integer, TreeNode<org.satel.ressatel.bean.list.skill.Skill>> nodes = new HashMap<>();

        fillHierarchyOfNodesAndParents(skills, parents, nodes);

        new ArrayList<>(nodes.values()).forEach(node -> {
            Skill parent = parents.get(node.getData().getId());
            TreeNode<org.satel.ressatel.bean.list.skill.Skill> parentNode
                    = parent == null ? root : nodes.get(parent.getId());
            node.setParent(parentNode);
            if (parentNode != null) {
                parentNode.getChildren().add(node);
            }
        });
        return root;
    }

    private void fillHierarchyOfNodesAndParents(List<Skill> skills, Map<Integer, Skill> parents, Map<Integer, TreeNode<org.satel.ressatel.bean.list.skill.Skill>> nodes) {
        if (skills != null && !skills.isEmpty()) {
            Map<Integer, Skill> cycleParents = new HashMap<>();
            Map<Integer, TreeNode<org.satel.ressatel.bean.list.skill.Skill>> cycleNodes = new HashMap<>();
            skills.forEach(skill -> {
                if (skill != null) {
                    cycleParents.put(skill.getId(), skill.getParent() == null ? null : skill.getParent());
                    cycleNodes.put(
                            skill.getId(),
                            new DefaultTreeNode<>(
                                    new org.satel.ressatel.bean.list.skill.Skill(skill.getId(), skill.getName(), null, "Folder")
                            )
                    );
                }
            });
            if (!cycleParents.isEmpty()) {
                parents.putAll(cycleParents);
                nodes.putAll(cycleNodes);
                fillHierarchyOfNodesAndParents(new ArrayList<>(cycleParents.values()), parents, nodes);
            }
        }
    }


    private List<Skill> getSkillsByEmployeeIdAndExtraRolesId(String employeeId) {
        List<Integer> extraRoleIds = skillRepository.getExtraRoleIdsByEmployeeId(Integer.valueOf(employeeId));
        List<Integer> skillIds = new ArrayList<>();
        extraRoleIds.forEach(roleId -> skillIds.addAll(skillRepository.getSkillIdsByEmployeeIdAndRoleId(Integer.valueOf(employeeId), roleId)));
        return skillIds.stream().map(skillRepository::getReferenceById).collect(Collectors.toList());
    }

    public Skill getById(Integer skillId) {
        return skillRepository.findById(skillId).orElse(null);
    }

    public Set<String> getEmployeeSkillNames(Employee employee) {
        return employee.getSkills().stream().map(Skill::getName).collect(Collectors.toSet());
    }

    public Map<Skill, SkillGrade> getMainSkillMap(Employee employee, Role role) {
        Map<Skill, SkillGrade> map = new HashMap<>();
        Set<Integer> skillIds = skillRepository.getSkillIdsByEmployeeIdAndRoleId(employee.getId(), role.getId());
        Set<Skill> skills = skillIds.stream().map(skillRepository::getReferenceById).collect(Collectors.toSet());
        skills.forEach(skill -> {
            map.put(
                    skill,
                    skillGradeRepository.getReferenceById(skillRepository.getSkillGradeIdByEmployeeIdAndSkillId(skill.getId(), employee.getId()))
            );
        });
        return map;
    }

    public List<Integer> getExtraRoleIdsByEmployeeId(Integer employeeId) {
        return skillRepository.getExtraRoleIdsByEmployeeId(employeeId);
    }

    public Map<String, SkillGrade> getSkillNameToSkillGradeMap(Employee employee, Role role) {
        Map<String, SkillGrade> map = new HashMap<>();
        getMainSkillMap(employee, role).forEach((key, value) -> map.put(key.getName(), value));
        return map;
    }

    public void setSkillGradeIdForEmployeeSkillAndRole(Integer employeeId, Integer skillId, Integer skillGradeId, Integer roleId) {
        skillRepository.setSkillGradeIdForEmployeeSkillAndRole(employeeId, skillId, skillGradeId, roleId);
    }
}
