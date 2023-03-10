package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.skill.Skill;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.SkillService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component("employeeSkillsSelectionView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class EmployeeSkillsSelectionView {
    private String id;

    private SkillService skillService;
    private EmployeeService employeeService;
    private TreeNode<Skill>[] selectedNodes;
    private TreeNode<Skill> root;

    @Inject
    public EmployeeSkillsSelectionView(SkillService skillService, EmployeeService employeeService) {
        this.skillService = skillService;
        this.employeeService = employeeService;
    }

    private void init() {
        root = skillService.getTreeNodeOfSkills();
    }

    public void onload() {
        init();
        Employee employee = employeeService.getByStringId(id);
        Set<org.satel.ressatel.entity.Skill> skills = employee.getSkills();
        Set<Integer> ids = skills.stream().map(org.satel.ressatel.entity.Skill::getId).collect(Collectors.toSet());
        checkSelectedNodesRecursively(root, ids);
    }

    private void checkSelectedNodesRecursively(TreeNode<Skill> root, Set<Integer> ids) {
        root.setSelected(ids.contains(root.getData().getId()));
        root.setSelectable(false);
        if (root.getChildCount() != 0) {
            root.getChildren().forEach(skillTreeNode -> {
                checkSelectedNodesRecursively(skillTreeNode, ids);
            });
        }
    }

    public void onsubmit(TreeNode<Skill>[] nodes) {
        Employee employee = employeeService.getByStringId(id);
        if (nodes != null && nodes.length > 0) {
            Set<org.satel.ressatel.entity.Skill> skills = new HashSet<>();
            for (TreeNode<Skill> node : nodes) {
                org.satel.ressatel.entity.Skill skill = skillService.getById(node.getData().getId());
                if (skill != null) {
                    skills.add(skill);
                }
            }
            employee.setSkills(skills);
        } else {
            employee.setSkills(null);
        }
        employeeService.createOrUpdateEmployee(employee);
    }
}
