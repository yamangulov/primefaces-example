package org.satel.ressatel.bean.directory.skill;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.PrimeFaces;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.service.SkillService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Component("skillsDirectoryCardEditView")
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ViewScoped
//@SessionScoped
//@ApplicationScoped
@Getter
@Setter
@Log4j2
public class SkillsDirectoryCardEditView implements Serializable {
    private List<Skill> skills;

    private Skill selectedSkill;

    private List<Skill> selectedSkills;

    private final SkillService skillService;
    private final ParentSkillSelectView parentSkillSelectView;

    @Inject
    public SkillsDirectoryCardEditView(SkillService skillService, ParentSkillSelectView parentSkillSelectView) {
        this.skillService = skillService;
        this.parentSkillSelectView = parentSkillSelectView;
    }

    public void onload() {
        this.init();
    }
    public void init() {
        this.skills = this.skillService.getSkills();
    }
    public void openNew() {
        this.selectedSkill = new Skill();
    }


    public void deleteSkill() {
        try {
            skillService.deleteSkill(this.selectedSkill);
            this.skills.remove(this.selectedSkill);
            this.selectedSkills.remove(this.selectedSkill);
            this.selectedSkill = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Компетенции удалены"));
        } catch (DataIntegrityViolationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Компетенция не можеть быть удалена, так как в БД имеются сущности, привязанные к ней", null));
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
    }

    public void deleteSelectedSkills() {
        try {
            skillService.deleteSkills(this.selectedSkills);
            this.skills.removeAll(this.selectedSkills);
            this.selectedSkills = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Компетенции удалены"));
        } catch (DataIntegrityViolationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Компетенции не могут быть удалены, так как в БД имеются сущности, привязанные к ним"));
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
        //PrimeFaces.current().executeScript("PF('dtSkills').clearFilters()");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSkills()) {
            int size = this.selectedSkills.size();
            if (size % 10 == 1 && size != 11) {
                return size + " компетенция выбрана";
            }
            if (size % 10 > 1 && size % 10 < 5 && (size < 5 || size > 20)) {
                return size + " компетенции выбрано";
            }
            return size + " компетенций выбрано";
        }

        return "Удалить";
    }

    public boolean hasSelectedSkills() {
        return this.selectedSkills != null && !this.selectedSkills.isEmpty();
    }

}
