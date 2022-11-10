package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.primefaces.util.Constants;
import org.satel.ressatel.bean.list.employee.Employee;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.entity.Specialty;
import org.satel.ressatel.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@ApplicationScoped
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverHost;

    @Value("${server.protocol}")
    private String serverProtocol;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getShortList() {
        List<Employee> employees = new ArrayList<>();
        List<org.satel.ressatel.entity.Employee> fullEmployees = getList();
        fullEmployees.forEach(fullEmployee -> {
            Employee employee = new Employee(
                    getFullName(fullEmployee),
                    getSkills(fullEmployee),
                    getEmployeeDepartment(fullEmployee),
                    getSpecialties(fullEmployee),
                    getArchived(fullEmployee),
                    getLinkToLK(fullEmployee)
            );
            employees.add(employee);
        });
        return employees;
    }

    private String getLinkToLK(org.satel.ressatel.entity.Employee fullEmployee) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serverProtocol);
        stringBuilder.append("://");
        stringBuilder.append(serverHost);
        stringBuilder.append(":");
        stringBuilder.append(serverPort);
        stringBuilder.append("/employee/");
        stringBuilder.append(fullEmployee.getId());
        return stringBuilder.toString();
    }

    private String getArchived(org.satel.ressatel.entity.Employee fullEmployee) {
        if (fullEmployee.getArchived() == null) {
            return null;
        }
        return fullEmployee.getArchived() ? "+" : "-";
    }

    private String getSpecialties(org.satel.ressatel.entity.Employee fullEmployee) {
        List<Specialty> specialties = new ArrayList<>(fullEmployee.getSpecialties());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < specialties.size(); i++) {
            stringBuilder.append(specialties.get(i).getName());
            if (i != specialties.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String getEmployeeDepartment(org.satel.ressatel.entity.Employee fullEmployee) {
//        вернуть после заполнения org_department
//        return fullEmployee.getEmployeeDepartment().getOrgDepartment().getName()
//                + ", " + fullEmployee.getEmployeeDepartment().getFinDepartment().getName();
        if (fullEmployee.getEmployeeDepartment() == null
                || fullEmployee.getEmployeeDepartment().getFinDepartment() == null) {
            return null;
        }
        return fullEmployee.getEmployeeDepartment().getFinDepartment().getName();
    }

    private String getSkills(org.satel.ressatel.entity.Employee fullEmployee) {
        List<Skill> skills = new ArrayList<>(fullEmployee.getSkills());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            stringBuilder.append(skills.get(i).getName());
            if (i != skills.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private static String getFullName(org.satel.ressatel.entity.Employee fullEmployee) {
        if (fullEmployee.getSurname() == null
                || fullEmployee.getFirstName() == null
                || fullEmployee.getSecondName() == null) {
            return null;
        }
        return fullEmployee.getSurname() + Constants.SPACE + fullEmployee.getFirstName().substring(0, 1).toUpperCase() + "." +
                Constants.SPACE + fullEmployee.getSecondName().substring(0, 1).toUpperCase() + ".";
    }

    public List<org.satel.ressatel.entity.Employee> getList() {
        return employeeRepository.findAll();
    }

    public org.satel.ressatel.entity.Employee getByStringId(String id) {
        return employeeRepository.findById(Integer.valueOf(id)).orElse(null);
    }

    public org.satel.ressatel.entity.Employee getById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void createOrUpdateEmployee(org.satel.ressatel.entity.Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    public List<String> getHierarchicalSkills(org.satel.ressatel.entity.Employee employee) {
        Set<Skill> skills = employee.getSkills();
        if (skills != null && !skills.isEmpty()) {
            List<String> strings = new ArrayList<>();
            skills.forEach(skill -> {
                if (skill.getParent() == null) {
                    AtomicReference<Integer> level = new AtomicReference<>(0);
                    addSkills(skill, strings, skills, level);
                }
            });
            List<String> hierarchy = new ArrayList<>();
            strings.forEach(s -> {
                String first = s.substring(0, 1);
                String other = s.substring(1);
                hierarchy.add("-".repeat(Integer.parseInt(first)) + other);
            });
            return hierarchy;
        }
        return null;
    }

    private void addSkills(Skill skill, List<String> strings, Set<Skill> skills, AtomicReference<Integer> level) {
        strings.add(level.get() + skill.getName());
        skills.forEach(skl -> {
            if (skl.getParent() != null && skl.getParent().getId().intValue() == skill.getId().intValue()) {
                level.getAndSet(level.get() + 1);
                addSkills(skl, strings, skills, level);
                level.getAndSet(level.get() - 1);
            }
        });
    }

    public List<String> getSkillStringList(Integer id) {
        org.satel.ressatel.entity.Employee employee = getById(id);
        List<String> skills = new ArrayList<>();
        if (!employee.getSkills().isEmpty()) {
            employee.getSkills().forEach(skill -> skills.add(skill.getName()));
        }
        return skills;
    }
}
