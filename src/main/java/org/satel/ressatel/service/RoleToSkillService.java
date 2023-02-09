package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.entity.RoleToSkill;
import org.satel.ressatel.repository.RoleToSkillRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ApplicationScoped
@Log4j2
public class RoleToSkillService {
    private final RoleToSkillRepository roleToSkillRepository;

    @Inject
    public RoleToSkillService(RoleToSkillRepository roleToSkillRepository) {
        this.roleToSkillRepository = roleToSkillRepository;
    }

    public Set<RoleToSkill> getRoleToSkillByRole(Role role) {
        return roleToSkillRepository.findRoleToSkillByRole(role);
    }

}
