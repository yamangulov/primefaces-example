package org.satel.ressatel.repository;

import org.satel.ressatel.entity.FinDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinDepartmentRepository extends JpaRepository<FinDepartment, Integer> {
    FinDepartment findByName(String name);
}
