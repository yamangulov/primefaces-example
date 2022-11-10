package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.FinDepartment;
import org.satel.ressatel.repository.FinDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class FinDepartmentService {
    private final FinDepartmentRepository finDepartmentRepository;

    @Inject
    public FinDepartmentService(FinDepartmentRepository finDepartmentRepository) {
        this.finDepartmentRepository = finDepartmentRepository;
    }

    public List<FinDepartment> getFinDepartments() {
        return finDepartmentRepository.findAll();
    }

    public List<String> getFinDepartmentsNames() {
        List<FinDepartment> finDepartments = getFinDepartments();
        List<String> names = new ArrayList<>();
        finDepartments.forEach(finDepartment -> names.add(finDepartment.getName()));
        return names;
    }

    public FinDepartment getById(String id) {
        return finDepartmentRepository.findById(Integer.valueOf(id)).orElse(null);
    }

    public FinDepartment getByName(String name) {
        return finDepartmentRepository.findByName(name);
    }

    public void createOrUpdate(FinDepartment finDep) {
        finDepartmentRepository.saveAndFlush(finDep);
    }
}
