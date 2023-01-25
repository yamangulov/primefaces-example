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

    public TreeNode<org.satel.ressatel.bean.list.role.Role> getTreeNodeOfRoles() {
        DefaultTreeNode<org.satel.ressatel.bean.list.role.Role> root = new DefaultTreeNode<>(new org.satel.ressatel.bean.list.role.Role(0, "Роли", "Folder"), null);
        List<org.satel.ressatel.entity.Role> roles = getRoles();
        Map<Integer, TreeNode<org.satel.ressatel.bean.list.role.Role>> nodes = new HashMap<>();
        roles.forEach(role -> {
            nodes.put(
                    role.getId(),
                    new DefaultTreeNode<>(
                            new org.satel.ressatel.bean.list.role.Role(role.getId(), role.getName(), "Folder")
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
}
