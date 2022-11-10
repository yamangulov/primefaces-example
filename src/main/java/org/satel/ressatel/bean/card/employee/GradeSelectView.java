package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.GradeService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("gradeSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class GradeSelectView {
    private String id;
    private String option;
    private String grade;
    private List<String> grades;
    private final GradeService gradeService;
    private final EmployeeService employeeService;

    @Inject
    public GradeSelectView(GradeService gradeService, EmployeeService employeeService) {
        this.gradeService = gradeService;
        this.employeeService = employeeService;
    }

    public void onload() {
        grades = gradeService.getGradeNames();
        Employee employee = employeeService.getByStringId(id);
        if (employee.getGrade() != null) {
            grade = employee.getGrade().getName();
        }
    }
}
