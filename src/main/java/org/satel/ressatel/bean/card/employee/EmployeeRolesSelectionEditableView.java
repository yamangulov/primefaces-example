package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.role.Role;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component("employeeRolesSelectionEditableView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class EmployeeRolesSelectionEditableView {
    private String id;
    private EmployeeService employeeService;
    private RoleService roleService;
    private TreeNode<Role>[] selectedNodes;
    private TreeNode<Role> selectedNode;
    private TreeNode<Role> rootMain;
    private TreeNode<Role> rootExtra;
    private EmployeeRatingView employeeRatingView;

    @Inject
    public EmployeeRolesSelectionEditableView(EmployeeService employeeService, RoleService roleService, EmployeeRatingView employeeRatingView) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.employeeRatingView = employeeRatingView;
        init();
    }

    private void init() {
        rootMain = createCheckboxRoles();
        rootExtra = createCheckboxExtraRoles();
    }

    public void onload() {
        Employee employee = employeeService.getByStringId(id);
        Set<org.satel.ressatel.entity.Role> mainRoles = employee.getRoles();
        Set<Integer> idsMain = mainRoles.stream().map(org.satel.ressatel.entity.Role::getId).collect(Collectors.toSet());
        Set<org.satel.ressatel.entity.Role> extraRoles = employee.getExtraRoles();
        Set<Integer> idsExtra = extraRoles.stream().map(org.satel.ressatel.entity.Role::getId).collect(Collectors.toSet());
        Map<String, Grade> mainRoleMap = roleService.getNameToMainRoleMap(employee);
        Map<String, Grade> extraRoleMap = roleService.getNameToExtraRoleMap(employee);
        selectAndGradeNodes(rootMain, idsMain, mainRoleMap);
        selectAndGradeNodes(rootExtra, idsExtra, extraRoleMap);
    }

    private void selectAndGradeNodes(TreeNode<Role> root, Set<Integer> ids,
                                     Map<String, Grade> roleMap) {
        root.setSelected(ids.contains(root.getData().getId()));
        Grade grade = roleMap.get(root.getData().getName());
        if (grade != null) {
            root.getData().setGrade(String.valueOf(grade.getId()));
        }
        if (root.getChildCount() != 0) {
            root.getChildren().forEach(roleTreeNode -> {
                selectAndGradeNodes(roleTreeNode, ids, roleMap);
            });
        }
    }

    private TreeNode<Role> createCheckboxRoles() {
        // повтор вызова метода необходим, чтобы в двух деревьях были действительно разные объекты TreeNode<Role>
        return roleService.getTreeNodeOfRoles();
    }

    private TreeNode<Role> createCheckboxExtraRoles() {
        // повтор вызова метода необходим, чтобы в двух деревьях были действительно разные объекты TreeNode<Role>
        return roleService.getTreeNodeOfRoles();
    }

    public void onsubmitAll(TreeNode<Role>[] nodes) {
        onsubmit();
        onsubmitExtra(nodes);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onsubmit() {
        Employee employee = employeeService.getByStringId(id);
        if (selectedNode != null) {
            log.info("selected main node is {}", selectedNode);
            Set<org.satel.ressatel.entity.Role> roles = new HashSet<>();
            org.satel.ressatel.entity.Role role = roleService.getById(selectedNode.getData().getId());
            if (role != null) {
                roles.add(role);
            }
            employee.setRoles(roles);
        } else {
            employee.setRoles(null);
        }
        employeeService.createOrUpdateEmployee(employee);
//        selectedNode = new DefaultTreeNode<>();
    }

    @SuppressWarnings("unchecked")
    public void onsubmitExtra(TreeNode<Role>[] nodes) {
        Employee employee = employeeService.getByStringId(id);
        if (nodes != null && nodes.length > 0) {
            for (TreeNode<Role> node : nodes) {
                log.info("selected node from page {}", node);
            }
            Set<org.satel.ressatel.entity.Role> roles = new HashSet<>();
            for (TreeNode<Role> node : nodes) {
                org.satel.ressatel.entity.Role role = roleService.getById(node.getData().getId());
                if (role != null) {
                    roles.add(role);
                }
            }
            employee.setExtraRoles(roles);
        } else {
            employee.setExtraRoles(null);
        }
        employeeService.createOrUpdateEmployee(employee);
//        selectedNodes = new DefaultTreeNode[0];
    }
}
