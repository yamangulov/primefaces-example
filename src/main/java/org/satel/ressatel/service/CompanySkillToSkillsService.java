package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.CompanySkillToSkills;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.repository.CompanySkillToSkillsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@ApplicationScoped
@Log4j2
public class CompanySkillToSkillsService {
    private final CompanySkillToSkillsRepository companySkillToSkillsRepository;

    @Inject
    public CompanySkillToSkillsService(CompanySkillToSkillsRepository companySkillToSkillsRepository) {
        this.companySkillToSkillsRepository = companySkillToSkillsRepository;
    }

    public void saveAll(List<CompanySkillToSkills> companySkillToSkillsList) {
        companySkillToSkillsRepository.saveAll(companySkillToSkillsList);
        companySkillToSkillsRepository.flush();
    }

    public CompanySkillToSkills getByCompanySkillIdAndSkill(Integer companySkillId, Skill skill) {
        return companySkillToSkillsRepository
                .findCompanySkillToSkillsByCompanySkillIdAndSkill(companySkillId, skill);
    }

    public void deleteAll(Set<CompanySkillToSkills> oldCompanySkillToSkills) {
        companySkillToSkillsRepository.deleteAll(oldCompanySkillToSkills);
        companySkillToSkillsRepository.flush();
    }
}
