package org.satel.ressatel.bean.list.employee;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import org.satel.ressatel.service.EmployeeService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("employeeFilterView")
@ViewScoped
@Getter
@Setter
@Slf4j
public class EmployeeFilterView implements Serializable {

    private List<Employee> employees;
    private List<Employee> filteredEmployees;
    private List<FilterMeta> filterBy;

    private boolean globalFilterOnly;
    private final EmployeeService employeeService;

    @Inject
    public EmployeeFilterView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.init();
    }

    public void init() {
        employees = employeeService.getShortList();
        globalFilterOnly = false;
        filterBy = new ArrayList<>();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Employee employee = (Employee) value;
        return employee.getName().toLowerCase().contains(filterText)
                || employee.getSpecialties().toLowerCase().contains(filterText)
                || employee.getEmployeeDepartment().toLowerCase().contains(filterText)
                || employee.getSkills().toLowerCase().contains(filterText)
                || employee.getArchived().toLowerCase().contains(filterText);
    }

    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }
}
