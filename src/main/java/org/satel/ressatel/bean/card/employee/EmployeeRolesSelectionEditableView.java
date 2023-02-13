package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RateEvent;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.bean.list.role.Role;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.GradeService;
import org.satel.ressatel.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.IOException;
import java.util.*;
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
    private GradeService gradeService;
    private TreeNode<Role>[] selectedNodes;
    private TreeNode<Role> selectedNode;
    private TreeNode<Role> rootMain;
    private TreeNode<Role> rootExtra;
    private EmployeeRatingView employeeRatingView;

    @Inject
    public EmployeeRolesSelectionEditableView(EmployeeService employeeService, RoleService roleService, GradeService gradeService, EmployeeRatingView employeeRatingView) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.gradeService = gradeService;
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

    public void onMainRate(RateEvent<String> rateEvent) {
        Integer selectedRoleId =
                (Integer) rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id");
        unselectOther(rootMain, selectedRoleId);
        PrimeFaces.current().ajax().update(rateEvent.getComponent().getParent().getParent());
//        log.info("main rate event: role_id {} rating {}, role_value {}",
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id"),
//                rateEvent.getRating(),
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("value"));
    }

    private void unselectOther(TreeNode<Role> rootMain, Integer selectedRoleId) {
        rootMain.getChildren().forEach(roleTreeNode ->  {
                roleTreeNode.setSelectable(true);
                roleTreeNode.setSelected(Objects.equals(roleTreeNode.getData().getId(), selectedRoleId));
            });
    }

    public void onMainCancel(AjaxBehaviorEvent rateEvent) {
//        log.info("main cancel event: role_id {}, role_value {}",
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id"),
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("value"));
    }

    public void onExtraRate(RateEvent<String> rateEvent) {
//        Integer role_id = (Integer) rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id");
//        Integer rating = Integer.parseInt(rateEvent.getRating());
//        log.info("extra rate event: role_id {} rating {}, role_value {}",
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id"),
//                rateEvent.getRating(),
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("value"));
    }

    public void onExtraCancel(AjaxBehaviorEvent rateEvent) {
//        Integer role_id = (Integer) rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id");

//        log.info("extra cancel event: role_id {}, role_value {}",
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("role_id"),
//                rateEvent.getComponent().getParent().getChildren().get(0).getAttributes().get("value"));
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
        // повтор вызова метода необходим, чтобы в дереве основной роли был отдельный объект TreeNode<Role>
        return roleService.getTreeNodeOfRoles();
    }

    private TreeNode<Role> createCheckboxExtraRoles() {
        // повтор вызова метода необходим, чтобы в дереве дополнительных ролей был отдельный объект TreeNode<Role>
        return roleService.getTreeNodeOfRoles();
    }

    public void onsubmitAll(TreeNode<Role>[] nodes) {
        String employeeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("employeeId");

        onsubmit(employeeId);
        onsubmitExtra(nodes, employeeId);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onsubmit(String employeeId) {
        Employee employee = employeeService.getByStringId(employeeId);
        if (selectedNode != null) {
            Set<org.satel.ressatel.entity.Role> roles = new HashSet<>();
            Integer roleId = selectedNode.getData().getId();
            Integer gradeId =
                    selectedNode.getData().getGrade() == null ? 1 : Integer.parseInt(selectedNode.getData().getGrade());
            org.satel.ressatel.entity.Role role = roleService.getById(roleId);
            if (role != null) {
                roles.add(role);
            }
            employee.setRoles(roles);
            employeeService.createOrUpdateEmployee(employee);
            roleService.setGradeIdForEmployeeRole(employee.getId(), roleId, gradeId);
        } else {
            employee.setRoles(null);
            employeeService.createOrUpdateEmployee(employee);
        }

//        selectedNode = new DefaultTreeNode<>();
    }

    public void onsubmitExtra(TreeNode<Role>[] nodes, String employeeId) {
        Employee employee = employeeService.getByStringId(employeeId);
        if (nodes != null && nodes.length > 0) {
            Set<org.satel.ressatel.entity.Role> roles = new HashSet<>();
            Map<Integer, Integer> roleIdToGradeId = new HashMap<>();
            for (TreeNode<Role> node : nodes) {
                Integer roleId = node.getData().getId();
                org.satel.ressatel.entity.Role role = roleService.getById(roleId);
                Integer gradeId =
                        node.getData().getGrade() == null ? 1 : Integer.parseInt(node.getData().getGrade());
                if (role != null) {
                    roles.add(role);
                    roleIdToGradeId.put(roleId, gradeId);
                }
            }
            employee.setExtraRoles(roles);
            employeeService.createOrUpdateEmployee(employee);
            roleIdToGradeId.forEach((roleId, gradeId) -> {
                roleService.setGradeIdForEmployeeExtraRole(employee.getId(), roleId, gradeId);
            });
        } else {
            employee.setExtraRoles(null);
            employeeService.createOrUpdateEmployee(employee);
        }

//        selectedNodes = new DefaultTreeNode[0];
    }
}
