package org.satel.ressatel.bean.card.general;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DualListModel;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.service.CompanyService;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("pickListView")
@RequestScoped
@Log4j2
public class PickListView {
    private String companyId;

    private final SkillService skillService;
    private final CompanyService companyService;
    private DualListModel<String> skills;

    private List<String> strSkillsSourceOuter;
    private List<String> strSkillsTargeteOuter;

    @Inject
    public PickListView(SkillService skillService, CompanyService companyService) {
        this.skillService = skillService;
        this.companyService = companyService;
        init();
    }

    private void init() {
        List<String> strSkillsSourceInner = new ArrayList<>();
        List<String> strSkillsTargetInner = new ArrayList<>();

        List<Skill> allSkills = skillService.getSkills();
        allSkills.forEach(skill -> {
            strSkillsSourceInner.add(skill.getName());
        });

        skills = new DualListModel<>(strSkillsSourceInner, strSkillsTargetInner);
        this.strSkillsSourceOuter = strSkillsSourceInner;
        this.strSkillsTargeteOuter = strSkillsTargetInner;
    }

    public void onload() {
        List<String> companySkillList = companyService.getSkillStringList(Integer.valueOf(companyId));
        if (!companySkillList.isEmpty()) {
            strSkillsSourceOuter = new ArrayList<>();
            strSkillsTargeteOuter.addAll(companySkillList);
            strSkillsTargeteOuter = new ArrayList<>();
            strSkillsSourceOuter.removeAll(companySkillList);
        }
    }

    public DualListModel<String> getSkills() {
        return skills;
    }

    public void setSkills(DualListModel<String> skills) {
        this.skills = skills;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
