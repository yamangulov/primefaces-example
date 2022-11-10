package org.satel.ressatel.bean.directory.skill;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("parentSkillSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class ParentSkillSelectView {
    private String id;
    private String option;
    private String parentSkill;
    private List<String> parentSkills;
    private final SkillService skillService;
    @Inject
    public ParentSkillSelectView(SkillService skillService) {
        this.skillService = skillService;
    }

    public void onloadManageSkillDialog(Skill skill) {
        parentSkills = skillService.getSkillNames();
        if (skill.getParent() != null) {
            this.parentSkill = skill.getParent().getName();
        }
    }
}
