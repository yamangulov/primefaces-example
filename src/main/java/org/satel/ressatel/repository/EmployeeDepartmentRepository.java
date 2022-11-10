package org.satel.ressatel.repository;

import org.satel.ressatel.entity.EmployeeDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer> {
}
