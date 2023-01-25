package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.role.Role;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.RoleService;
import org.satel.ressatel.service.SkillService;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component("employeeRolesSelectionEditableView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class EmployeeRolesSelectionEditableView {
    private String id;

    private SkillService skillService;
    private EmployeeService employeeService;
    private RoleService roleService;
    private TreeNode<Role>[] selectedNodes;
    private TreeNode<Role> selectedNode;
    private TreeNode<Role> rootMain;
    private TreeNode<Role> rootExtra;

    @Inject
    public EmployeeRolesSelectionEditableView(SkillService skillService, EmployeeService employeeService, RoleService roleService) {
        this.skillService = skillService;
        this.employeeService = employeeService;
        this.roleService = roleService;
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
        checkSelectedMainNodes(rootMain, idsMain);
        checkSelectedExtraNodes(rootExtra, idsExtra);
    }

    private void checkSelectedMainNodes(TreeNode<Role> root, Set<Integer> ids) {
        root.setSelected(ids.contains(root.getData().getId()));
    }

    private void checkSelectedExtraNodes(TreeNode<Role> root, Set<Integer> ids) {
        root.setSelected(ids.contains(root.getData().getId()));
    }

    private TreeNode<Role> createCheckboxRoles() {
        return roleService.getTreeNodeOfRoles();
    }

    private TreeNode<Role> createCheckboxExtraRoles() {
        return roleService.getTreeNodeOfRoles();
    }

    public void onsubmitAll(TreeNode<Role>[] nodes) {
        onsubmit();
        onsubmitExtra(nodes);
    }
    public void onsubmit() {
        if (selectedNode != null) {
            log.info("selected main node is {}", selectedNode);
        }
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
        selectedNodes = new DefaultTreeNode[0];
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
