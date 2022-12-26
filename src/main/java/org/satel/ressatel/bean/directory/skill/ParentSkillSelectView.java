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
//        parentSkills = skillService.getSkillNames();
    }

    public void onloadManageSkillDialog(Skill skill) {
        log.info("parentSkillSelectView {}", this);
        log.info("skill on load {}", skill.getName());
        log.info("parent {}", skill.getParent() == null ? null : skill.getParent().getName());
        if (skill.getParent() != null) {
            this.parentSkill = skill.getParent().getName();
        } else {
            this.parentSkill = null;
        }
    }
}
