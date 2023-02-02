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
import java.io.Serializable;
import java.util.List;

@Component("skillsDirectoryCardEditView")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
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

    public void saveSkill() {
        if (this.selectedSkill.getId() == null) {
//            log.info("selectedSkill name {} parent name {}",
//                    selectedSkill.getName(), selectedSkill.getParent().getName());
            //TODO здесь добавляем создание нового skill в БД
            this.skills.add(this.selectedSkill);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Skill Added"));
        }
        else {
            //TODO здесь добавляем обновление skill в БД
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Skill Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageSkillDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
    }

    public void deleteSkill() {
        //TODO здесь добавляем удаление skill из БД
        this.skills.remove(this.selectedSkill);
        this.selectedSkills.remove(this.selectedSkill);
        this.selectedSkill = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Skill Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
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

    public void deleteSelectedSkills() {
        //TODO здесь добавляем удаление нескольких выбранных skills из БД
        this.skills.removeAll(this.selectedSkills);
        this.selectedSkills = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Skills Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-skills");
        PrimeFaces.current().executeScript("PF('dtSkills').clearFilters()");
    }
}
