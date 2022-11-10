package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.EnglishLevelService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("englishLevelSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class EnglishLevelSelectView {
    private String id;
    private String option;
    private String level;
    private List<String> levels;
    private final EnglishLevelService gradeService;
    private final EmployeeService employeeService;

    @Inject
    public EnglishLevelSelectView(EnglishLevelService gradeService, EmployeeService employeeService) {
        this.gradeService = gradeService;
        this.employeeService = employeeService;
    }

    public void onload() {
        levels = gradeService.getEnglishLevelNames();
        Employee employee = employeeService.getByStringId(id);
        if (employee.getEnglishLevel() != null) {
            level = employee.getEnglishLevel().getName();
        }
    }

}
