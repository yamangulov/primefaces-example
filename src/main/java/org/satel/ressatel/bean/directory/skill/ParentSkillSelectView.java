package org.satel.ressatel.bean.directory.skill;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.PrimeFaces;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.service.SkillService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Component("parentSkillSelectView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@RequestScoped
@Getter
@Setter
@Log4j2
public class ParentSkillSelectView {
    private Skill skill;
    private String name;
    private String parentSkill;
    private List<String> parentSkills;
    private final SkillService skillService;
    @Inject
    public ParentSkillSelectView(SkillService skillService) {
        this.skillService = skillService;
        parentSkills = skillService.getSkillNames();
    }

    public void onchange(String name) {
        //this.name = name;
        log.info("name {}", name);
    }
    public void onselect(Skill skill) {
        this.skill = skill;
        log.info("skill {}", skill);
    }

    public void saveSkill(List<Skill> skills, Skill skill) {
        this.skill = skill;
        String message;
        if (this.skill.getId() == null) {
           message = "Компетенция добавлена";
            skills.add(this.skill);
        }
        else {
            message = "Компетенция обновлена";
        }
        Skill parentSkill = skillService.getByName(this.parentSkill);
        skill.setParent(parentSkill);
        skillService.createOrUpdate(skill);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));

        PrimeFaces.current().executeScript("PF('manageSkillDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
    }

    public void onloadManageSkillDialog(Skill skill) {
        if (skill.getParent() != null) {
            this.parentSkill = skill.getParent().getName();
        } else {
            this.parentSkill = null;
        }
    }
}
