package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Role;
import org.satel.ressatel.entity.RoleToSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleToSkillRepository extends JpaRepository<RoleToSkill, Integer> {
    Set<RoleToSkill> findRoleToSkillByRole(Role role);
}
