package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EducationGradeService;
import org.satel.ressatel.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("educationGradeSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class EducationGradeSelectView {
    private String id;
    private String option;
    private String educationGrade;
    private List<String> educationGrades;
    private final EducationGradeService educationGradeService;
    private final EmployeeService employeeService;

    @Inject
    public EducationGradeSelectView(EducationGradeService educationGradeService, EmployeeService employeeService) {
        this.educationGradeService = educationGradeService;
        this.employeeService = employeeService;
    }

    public void onload() {
        educationGrades = educationGradeService.getEducationGradeNames();
        Employee employee = employeeService.getByStringId(id);
        if (employee.getEducation() != null && employee.getEducation().getEducationGrade() != null) {
            educationGrade = employee.getEducation().getEducationGrade().getName();
        }
    }

}
