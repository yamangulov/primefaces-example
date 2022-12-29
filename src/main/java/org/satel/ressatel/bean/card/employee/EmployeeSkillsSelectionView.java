package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.skill.Skill;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

@Component("employeeSkillsSelectionView")
@RequestScoped
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
        init();
    }

    private void init() {
        root = createCheckboxSkills();
    }

    public void onload() {

    }

    private TreeNode<Skill> createCheckboxSkills() {
        return skillService.getTreeNodeOfSkills();
    }

    public void onsubmit(TreeNode<Skill>[] nodes) {

    }
}
