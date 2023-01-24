package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.repository.GradeRepository;
import org.satel.ressatel.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@ApplicationScoped
@Log4j2
public class RoleService {
    private final RoleRepository roleRepository;
    private final GradeRepository gradeRepository;

    @Inject
    public RoleService(RoleRepository roleRepository, GradeRepository gradeRepository) {
        this.roleRepository = roleRepository;
        this.gradeRepository = gradeRepository;
    }

    public Set<Role> getMainRoleNameSet(Employee employee) {
        return employee.getRoles();
    }

    public Map<Role, Grade> getMainRoleMap(Employee employee) {
        Map<Role, Grade> map = new HashMap<>();
        Set<Role> roles = employee.getRoles();
        roles.forEach(role -> {
            map.put(
                    role,
                    gradeRepository.getReferenceById(roleRepository.getGradeIdByRoleId(role.getId()))
            );
        });
        return map;
    }

    public Set<Role> getExtraRoleNameSet(Employee employee) {
        return employee.getExtraRoles();
    }

    public Map<Role, Grade> getExtraRoleMap(Employee employee) {
        Map<Role, Grade> map = new HashMap<>();
        Set<Role> roles = employee.getExtraRoles();
        roles.forEach(role -> {
            map.put(
                    role,
                    gradeRepository.getReferenceById(roleRepository.getGradeIdByExtraRoleId(role.getId()))
            );
        });
        return map;
    }
}
