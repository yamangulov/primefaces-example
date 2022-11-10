package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.EmployeeDepartment;
import org.satel.ressatel.repository.EmployeeDepartmentRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class EmployeeDepartmentService {
    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    @Inject
    public EmployeeDepartmentService(EmployeeDepartmentRepository employeeDepartmentRepository) {
        this.employeeDepartmentRepository = employeeDepartmentRepository;
    }

    public EmployeeDepartment getById(String id) {
        return employeeDepartmentRepository.getReferenceById(Integer.valueOf(id));
    }
}
