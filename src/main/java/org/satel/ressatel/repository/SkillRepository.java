package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findByName(String name);

    @Query(value = "select skill_grade_id from employees_to_skills where ( skill_id = :skill_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getSkillGradeIdByEmployeeIdAndSkillId(Integer skill_id, Integer employee_id);

    @Query(value = "select skill_grade_id from employees_to_extra_skills where ( skill_id = :skill_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getExtraSkillGradeIdByEmployeeIdAndSkillId(Integer skill_id, Integer employee_id);

    @Query(value = "select skill_id from employees_to_skills where ( role_id = :role_id and employee_id = :employee_id )", nativeQuery = true)
    Set<Integer> getSkillIdsByEmployeeIdAndRoleId(Integer employee_id, Integer role_id);

    @Query(value = "select skill_id from employees_to_extra_skills where ( role_id = :role_id and employee_id = :employee_id )", nativeQuery = true)
    Set<Integer> getExtraSkillIdsByEmployeeIdAndRoleId(Integer employee_id, Integer role_id);

    @Query(value = "select extra_role_id from employees_to_extra_roles where employee_id = :employeeId", nativeQuery = true)
    List<Integer> getExtraRoleIdsByEmployeeId(Integer employeeId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update employees_to_skills set skill_grade_id = :skillGradeId, role_id = :roleId where ( employee_id = :employeeId and skill_id = :skillId )", nativeQuery = true)
    void setSkillGradeIdForEmployeeSkillAndRole(Integer employeeId, Integer skillId, Integer skillGradeId, Integer roleId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "delete from employees_to_extra_skills where ( ( employee_id = :employeeId and role_id = :roleId ) or ( employee_id = :employeeId and role_id is null ) )", nativeQuery = true)
    void deleteByEmployeeIdAndRoleId(Integer employeeId, Integer roleId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "insert into employees_to_extra_skills values ( :employeeId, :skillId, :skillGradeId, :roleId, :comment )", nativeQuery = true)
    void addByAllParameters(Integer employeeId, Integer roleId, Integer skillId, Integer skillGradeId, String comment);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update employees_to_skills set comment = :comment where ( employee_id = :employeeId and skill_id = :skillId and role_id = :roleId)", nativeQuery = true)
    void setCommentForEmployeeSkillAndRole(Integer employeeId, Integer skillId, String comment, Integer roleId);

    @Query(value = "select comment from employees_to_skills where ( skill_id = :skillId and employee_id = :employeeId )", nativeQuery = true)
    String getCommentByEmployeeIdAndSkillId(Integer employeeId, Integer skillId);

    @Query(value = "select comment from employees_to_extra_skills where ( skill_id = :skillId and employee_id = :employeeId )", nativeQuery = true)
    String getExtraCommentByEmployeeIdAndSkillId(Integer employeeId, Integer skillId);
}
