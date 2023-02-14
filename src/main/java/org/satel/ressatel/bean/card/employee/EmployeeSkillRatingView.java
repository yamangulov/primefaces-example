package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.entity.SkillGrade;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.RoleService;
import org.satel.ressatel.service.SkillService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("employeeSkillRatingView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class EmployeeSkillRatingView {
    private String id;
    private List<Role> mainRoles;
    private List<Role> extraRoles;
    private List<Map.Entry<Skill, SkillGrade>> mainSkillList;
    private Map<Role, List<Map.Entry<Skill, SkillGrade>>> extraSkillMap;
    private final EmployeeService employeeService;
    private final SkillService skillService;
    private final RoleService roleService;

    @Inject
    public EmployeeSkillRatingView(EmployeeService employeeService, SkillService skillService, RoleService roleService) {
        this.employeeService = employeeService;
        this.skillService = skillService;
        this.roleService = roleService;
        this.extraSkillMap = new HashMap<>();
    }

    public List<Map.Entry<Skill, SkillGrade>> getRoleExtraSkillList(Integer roleId) {
        return extraSkillMap.get(roleService.getById(roleId));
    }


    public void onload() {
        Employee employee = employeeService.getByStringId(id);
        Role role = mainRoles.get(0); // сейчас только 1 главная роль
        if (!skillService.getSkillToSkillGradeMap(employee, role).isEmpty()) {
            mainSkillList = new ArrayList<>(skillService.getSkillToSkillGradeMap(employee, role).entrySet());
        }
        extraRoles.forEach(extraRole -> {
            if (!skillService.getExtraSkillToSkillGradeMap(employee, extraRole).isEmpty()) {
                List<Map.Entry<Skill, SkillGrade>> extraSkillList = new ArrayList<>(skillService.getExtraSkillToSkillGradeMap(employee, extraRole).entrySet());
                extraSkillMap.put(extraRole, extraSkillList);
            }
        });
    }
}
