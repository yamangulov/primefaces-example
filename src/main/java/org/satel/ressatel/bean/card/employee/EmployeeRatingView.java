package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("employeeRatingView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class EmployeeRatingView {
    private String id;
    private String mainRoleName;
    private Integer mainGradeId;
    private List<Role> mainRoles;
    private List<Map.Entry<Role, Grade>> extraRoleEntryList;

    private final RoleService roleService;
    private final EmployeeService employeeService;
    private final EmployeeSkillRatingView employeeSkillRatingView;

    @Inject
    public EmployeeRatingView(RoleService roleService, EmployeeService employeeService, EmployeeSkillRatingView employeeSkillRatingView) {
        this.roleService = roleService;
        this.employeeService = employeeService;
        this.employeeSkillRatingView = employeeSkillRatingView;
        this.mainRoles = new ArrayList<>();
    }


    public void onload() {
        Employee employee = employeeService.getByStringId(id);
        if (!roleService.getMainRoleMap(employee).isEmpty()) {
            roleService.getMainRoleMap(employee).forEach((role1, grade1) -> {
                mainRoles.add(role1);
                mainRoleName = role1.getName();
                mainGradeId = grade1 == null ? null : grade1.getId();
            });
        }
        this.employeeSkillRatingView.setMainRoles(mainRoles);
        if (!roleService.getExtraRoleMap(employee).isEmpty()) {
            extraRoleEntryList = new ArrayList<>(roleService.getExtraRoleMap(employee).entrySet());
        }
    }

}
