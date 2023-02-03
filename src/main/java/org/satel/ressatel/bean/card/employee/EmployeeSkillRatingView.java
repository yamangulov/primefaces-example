package org.satel.ressatel.bean.card.employee;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.entity.SkillGrade;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.SkillService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("employeeSkillRatingView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class EmployeeSkillRatingView {
    private String id;
    private List<Map.Entry<Skill, SkillGrade>> skillList;
    private final EmployeeService employeeService;
    private final SkillService skillService;

    @Inject
    public EmployeeSkillRatingView(EmployeeService employeeService, SkillService skillService) {
        this.employeeService = employeeService;
        this.skillService = skillService;
    }


    public void onload() {
        Employee employee = employeeService.getByStringId(id);
        if (!skillService.getSkillMap(employee).isEmpty()) {
            skillList = new ArrayList<>(skillService.getSkillMap(employee).entrySet());
        }
    }

}
