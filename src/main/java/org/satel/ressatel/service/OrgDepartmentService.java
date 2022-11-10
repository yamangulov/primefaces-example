package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.OrgDepartment;
import org.satel.ressatel.repository.OrgDepartmentRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class OrgDepartmentService {
    private final OrgDepartmentRepository orgDepartmentRepository;

    @Inject
    public OrgDepartmentService(OrgDepartmentRepository orgDepartmentRepository) {
        this.orgDepartmentRepository = orgDepartmentRepository;
    }

    public void createOrUpdate(OrgDepartment orgDep) {
        orgDepartmentRepository.saveAndFlush(orgDep);
    }
}
