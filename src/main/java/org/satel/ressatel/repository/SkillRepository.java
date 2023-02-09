package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findByName(String name);

    @Query(value = "select skill_grade_id from employees_to_skills where ( skill_id = :skill_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getSkillGradeIdByEmployeeIdAndSkillId(Integer skill_id, Integer employee_id);

    @Query(value = "select skill_id from employees_to_skills where ( role_id = :role_id and employee_id = :employee_id )", nativeQuery = true)
    Set<Integer> getSkillIdsByEmployeeIdAndRoleId(Integer employee_id, Integer role_id);

    @Query(value = "select skill_id from employees_to_skills where employee_id = :employeeId", nativeQuery = true)
    List<Integer> getSkillIdsByEmployeeId(String employeeId);

    @Query(value = "select extra_role_id from employees_to_extra_roles where employee_id = :employeeId", nativeQuery = true)
    List<Integer> getExtraRoleIdsByEmployeeId(Integer employeeId);

}
