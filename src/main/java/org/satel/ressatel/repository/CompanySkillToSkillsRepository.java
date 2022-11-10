package org.satel.ressatel.repository;

import org.satel.ressatel.entity.CompanySkillToSkills;
import org.satel.ressatel.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySkillToSkillsRepository extends JpaRepository<CompanySkillToSkills, Integer> {
    CompanySkillToSkills findCompanySkillToSkillsByCompanySkillIdAndSkill(Integer companySkillId, Skill skill);
}