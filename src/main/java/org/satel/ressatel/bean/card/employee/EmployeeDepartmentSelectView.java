package org.satel.ressatel.bean.card.employee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.service.EmployeeService;
import org.satel.ressatel.service.FinDepartmentService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("employeeDepartmentSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class EmployeeDepartmentSelectView {
    private String id;
    private String option;
    private String finDepartment;
    private List<String> finDepartments;
    private final FinDepartmentService finDepartmentService;
    private final EmployeeService employeeService;

    @Inject
    public EmployeeDepartmentSelectView(FinDepartmentService finDepartmentService, EmployeeService employeeService) {
        this.finDepartmentService = finDepartmentService;
        this.employeeService = employeeService;
    }

    public void onload() {
        finDepartments = finDepartmentService.getFinDepartmentsNames();
        Employee employee = employeeService.getByStringId(id);
        if (employee.getEmployeeDepartment().getFinDepartment() != null) {
            finDepartment = employee.getEmployeeDepartment().getFinDepartment().getName();
        }
    }

}
