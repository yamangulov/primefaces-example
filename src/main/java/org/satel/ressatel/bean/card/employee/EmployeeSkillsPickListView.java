package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DualListModel;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("employeeSkillsPickListView")
@RequestScoped
@Log4j2
public class EmployeeSkillsPickListView {
    private String id;

    private SkillService skillService;
    private EmployeeService employeeService;
    private DualListModel<String> skills;
    private List<Skill> allSkills;
    private List<Skill> selectedSkills;

    private List<String> strSkillsSourceOuter;
    private List<String> strSkillsTargeteOuter;

    @Inject
    public EmployeeSkillsPickListView(SkillService skillService, EmployeeService employeeService) {
        this.skillService = skillService;
        this.employeeService = employeeService;
        init();
    }

    private void init() {
        List<String> strSkillsSourceInner = new ArrayList<>();
        List<String> strSkillsTargetInner = new ArrayList<>();

        this.allSkills = skillService.getSkills();
        allSkills.forEach(skill -> {
            strSkillsSourceInner.add(skill.getName());
        });

        skills = new DualListModel<>(strSkillsSourceInner, strSkillsTargetInner);
        this.strSkillsSourceOuter = strSkillsSourceInner;
        this.strSkillsTargeteOuter = strSkillsTargetInner;
    }

    public void onload() {
        List<String> companySkillList = employeeService.getSkillStringList(Integer.valueOf(id));
        if (!companySkillList.isEmpty()) {
            strSkillsTargeteOuter.addAll(companySkillList);
            strSkillsSourceOuter.removeAll(companySkillList);
        }
    }

    public List<Skill> getSelectedSkills() {
        selectedSkills = skillService.getSkillListByNames(strSkillsTargeteOuter);
        return selectedSkills;
    }

    public List<Skill> getSelectedSkillsWithParents(List<Skill> selectedSkills) {
        Set<Skill> parentsSet = new HashSet<>();
        selectedSkills.forEach(skill -> {
            List<Skill> skills = new ArrayList<>();
            parentsSet.addAll(getParents(skill, skills));
        });
        List<Skill> newList = new ArrayList<>(selectedSkills);
        newList.addAll(parentsSet);
        return newList;
    }

    List<Skill> getParents(Skill skill, List<Skill> skills) {
        if (skill.getParent() != null) {
            skills.add(skill.getParent());
            getParents(skill.getParent(), skills);
        }
        return skills;
    }

    public DualListModel<String> getSkills() {
        return skills;
    }

    public void setSkills(DualListModel<String> skills) {
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
