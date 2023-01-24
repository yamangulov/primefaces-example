package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select grade_id from employees_to_roles where role_id = :role_id", nativeQuery = true)
    Integer getGradeIdByRoleId(Integer role_id);

    @Query(value = "select grade_id from employees_to_extra_roles where extra_role_id = :extra_role_id", nativeQuery = true)
    Integer getGradeIdByExtraRoleId(Integer extra_role_id);
}
