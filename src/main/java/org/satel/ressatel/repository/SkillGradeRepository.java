package org.satel.ressatel.repository;

import org.satel.ressatel.entity.SkillGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillGradeRepository extends JpaRepository<SkillGrade, Integer> {
}
