package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.repository.GradeRepository;
import org.satel.ressatel.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.*;

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
                    roleRepository.getGradeIdByEmployeeIdAndRoleId(role.getId(), employee.getId()) == null ? null : gradeRepository.getReferenceById(roleRepository.getGradeIdByEmployeeIdAndRoleId(role.getId(), employee.getId()))
            );
        });
        return map;
    }

    public Map<String, Grade> getNameToMainRoleMap(Employee employee) {
        Map<String, Grade> map = new HashMap<>();
        Set<Role> roles = employee.getRoles();
        roles.forEach(role -> {
            map.put(
                    role.getName(),
                    roleRepository.getGradeIdByEmployeeIdAndRoleId(role.getId(), employee.getId()) == null ? null : gradeRepository.getReferenceById(roleRepository.getGradeIdByEmployeeIdAndRoleId(role.getId(), employee.getId()))
            );
        });
        return map;
    }

    public Map<Role, Grade> getExtraRoleMap(Employee employee) {
        Map<Role, Grade> map = new HashMap<>();
        Set<Role> roles = employee.getExtraRoles();
        roles.forEach(role -> {
            map.put(
                    role,
                    gradeRepository.getReferenceById(roleRepository.getGradeIdByEmployeeIdAndExtraRoleId(role.getId(), employee.getId()))
            );
        });
        return map;
    }

    public Map<String, Grade> getNameToExtraRoleMap(Employee employee) {
        Map<String, Grade> map = new HashMap<>();
        Set<Role> roles = employee.getExtraRoles();
        roles.forEach(role -> {
            map.put(
                    role.getName(),
                    gradeRepository.getReferenceById(roleRepository.getGradeIdByEmployeeIdAndExtraRoleId(role.getId(), employee.getId()))
            );
        });
        return map;
    }

    public TreeNode<org.satel.ressatel.bean.list.role.Role> getTreeNodeOfRoles() {
        DefaultTreeNode<org.satel.ressatel.bean.list.role.Role> root = new DefaultTreeNode<>(new org.satel.ressatel.bean.list.role.Role(0, "????????", null, "Folder"), null);
        List<org.satel.ressatel.entity.Role> roles = getRoles();
        Map<Integer, TreeNode<org.satel.ressatel.bean.list.role.Role>> nodes = new HashMap<>();
        roles.forEach(role -> {
            nodes.put(
                    role.getId(),
                    new DefaultTreeNode<>(
                            new org.satel.ressatel.bean.list.role.Role(role.getId(), role.getName(), null, "Folder")
                    )
            );
        });
        new ArrayList<>(nodes.values()).forEach(node -> {
            node.setParent(root);
            root.getChildren().add(node);
        });
        return root;
    }

    private List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getById(Integer roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void setGradeIdForEmployeeRole(Integer employeeId, Integer roleId, Integer gradeId) {
        roleRepository.setGradeIdForEmployeeRole(employeeId, roleId, gradeId);
    }

    public void setGradeIdForEmployeeExtraRole(Integer employeeId, Integer extraRoleId, Integer gradeId) {
        roleRepository.setGradeIdForEmployeeExtraRole(employeeId, extraRoleId, gradeId);
    }
}
