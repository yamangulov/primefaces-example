package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ApplicationScoped
@Log4j2
public class RoleService {
    private final RoleRepository roleRepository;

    @Inject
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getMainRoleNameSet(Employee employee) {
        return employee.getRoles();
    }

    public Set<Role> getExtraRoleNameSet(Employee employee) {
        return employee.getExtraRoles();
    }
}
