package org.satel.ressatel.bean.directory.skill;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.satel.ressatel.service.SkillService;
import org.satel.ressatel.utils.SkillUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("skillsDirectoryCardView")
@ViewScoped
@Getter
@Setter
public class SkillsDirectoryCardView {
    private List<String> hierarchySkillsProfile;
    private final SkillService skillService;
    private final SkillUtils skillUtils;
    @Inject
    public SkillsDirectoryCardView(SkillService skillService, SkillUtils skillUtils) {
        this.skillService = skillService;
        this.skillUtils = skillUtils;
    }

    public void onload() {
        this.hierarchySkillsProfile = skillUtils.getHierarchicalSkillsRepresentation(skillService.getSkills());
    }
}
