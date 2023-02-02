package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select grade_id from employees_to_roles where ( role_id = :role_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getGradeIdByEmployeeIdAndRoleId(Integer role_id, Integer employee_id);

    @Query(value = "select grade_id from employees_to_extra_roles where ( extra_role_id = :extra_role_id and employee_id = :employee_id )", nativeQuery = true)
    Integer getGradeIdByEmployeeIdAndExtraRoleId(Integer extra_role_id, Integer employee_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update employees_to_roles set grade_id = :grade_id where ( employee_id = :employee_id and role_id = :role_id )", nativeQuery = true)
    void setGradeIdForEmployeeRole(Integer employee_id, Integer role_id, Integer grade_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update employees_to_extra_roles set grade_id = :grade_id where ( employee_id = :employee_id and extra_role_id = :extra_role_id )", nativeQuery = true)
    void setGradeIdForEmployeeExtraRole(Integer employee_id, Integer extra_role_id, Integer grade_id);
}
