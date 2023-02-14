package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.skill.Skill;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.SkillGrade;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.RoleService;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component("employeeExtraSkillsSelectionEditableView")
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@ViewScoped
@RequestScoped
@Getter
@Setter
@Log4j2
public class EmployeeExtraSkillsSelectionEditableView {
    private String id;
    private String roleId;

    private SkillService skillService;
    private EmployeeService employeeService;
    private RoleService roleService;
    private TreeNode<Skill>[] selectedNodes;
    private TreeNode<Skill> root;
    private List<String> leaves = new ArrayList<>();

    @Inject
    public EmployeeExtraSkillsSelectionEditableView(SkillService skillService, EmployeeService employeeService, RoleService roleService) {
        this.skillService = skillService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    private void init() {
        root = skillService.getTreeNodeOfSkillsByRole(this.roleId);
    }

    public void onload() {
        init();
        Employee employee = employeeService.getByStringId(id);
        Set<org.satel.ressatel.entity.Skill> skills = employee.getExtraSkills();
        Set<Integer> ids = skills.stream().map(org.satel.ressatel.entity.Skill::getId).collect(Collectors.toSet());
        Map<String, SkillGrade> skillGradeMap = skillService.getExtraSkillNameToSkillGradeMap(employee, roleService.getById(Integer.valueOf(roleId)));
        selectAndGradeNodes(root, ids, skillGradeMap);
    }

    private void selectAndGradeNodes(TreeNode<Skill> root, Set<Integer> ids,
                                     Map<String, SkillGrade> skillGradeMap) {
        root.setSelected(ids.contains(root.getData().getId()));
        SkillGrade skillGrade = skillGradeMap.get(root.getData().getName());
        String comment = skillService.getExtraCommentByEmployeeIdAndSkillId(Integer.valueOf(id), root.getData().getId());
        if (comment != null && !comment.isBlank()) {
            root.getData().setComment(comment);
        }
        if (skillGrade != null) {
            root.getData().setSkillGrade(String.valueOf(skillGrade.getId()));
        }
        if (root.getChildCount() == 0) {
            leaves.add(root.getData().getName());
        }
        if (root.getChildCount() != 0) {
            root.getChildren().forEach(skillTreeNode -> {
                selectAndGradeNodes(skillTreeNode, ids, skillGradeMap);
            });
        }
    }

    public void onunselect(NodeUnselectEvent event) {
        event.getTreeNode().setSelected(false);
    }

    public void onselect(NodeSelectEvent event) {
        event.getTreeNode().setSelected(true);
    }

    private void collectLevelLeaves(TreeNode<Skill> levelRoot, List<TreeNode<Skill>> nodeList) {
        nodeList.addAll(levelRoot.getChildren().stream().filter(skillTreeNode -> skillTreeNode.isSelected() && skillTreeNode.getChildCount() == 0).collect(Collectors.toList()));
    }

    private void collectLeaves(TreeNode<Skill> root, List<TreeNode<Skill>> leaves) {
        collectLevelLeaves(root, leaves);
        root.getChildren().forEach(node -> collectLeaves(node, leaves));
    }

    @SuppressWarnings("unchecked")
    public void onsubmit(TreeNode<Skill> root) {
        List<TreeNode<Skill>> leaves = new ArrayList<>();
        collectLeaves(root, leaves);
        TreeNode<Skill>[] nodes = leaves.toArray(TreeNode[]::new);

        String employeeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("employeeId");
        String roleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("roleId");
        Employee employee = employeeService.getByStringId(employeeId);

        if (nodes != null && nodes.length > 0) {
            Set<org.satel.ressatel.entity.Skill> skills = new HashSet<>();
            Map<Integer, Integer> skillIdToSkillGradeId = new HashMap<>();
            Map<Integer, String> skillIdToComment = new HashMap<>();
            for (TreeNode<Skill> node : nodes) {
                String comment = node.getData().getComment();
                Integer skillId = node.getData().getId();
                org.satel.ressatel.entity.Skill skill = skillService.getById(skillId);
                Integer skillGradeId = node.getData().getSkillGrade() == null ? 1 : Integer.parseInt(node.getData().getSkillGrade());
                if (skill != null) {
                    skills.add(skill);
                    skillIdToSkillGradeId.put(skillId, skillGradeId);
                }
                if (comment != null && !comment.isBlank()) {
                    skillIdToComment.put(skillId, comment);
                }
            }

            skillService.deleteByEmployeeIdAndRoleId(Integer.valueOf(employeeId), Integer.valueOf(roleId));
            skillIdToSkillGradeId.forEach((skillId, skillGradeId)  -> {
                skillService.addByAllParameters(
                        Integer.valueOf(employeeId),
                        Integer.valueOf(roleId),
                        skillId,
                        skillGradeId,
                        skillIdToComment.get(skillId)
                );
            });

        } else {
            employee.setExtraSkills(null);
            employeeService.createOrUpdateEmployee(employee);
        }

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
