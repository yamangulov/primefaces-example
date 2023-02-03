package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findByName(String name);

    @Query(value = "select skill_grade_id from employees_to_skills where ( skill_id = :skill_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getSkillGradeIdByEmployeeIdAndSkillId(Integer skill_id, Integer employee_id);
}
