package org.satel.ressatel.repository;

import org.satel.ressatel.entity.CompanyCommand;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.File;
import org.satel.ressatel.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findByTypeAndCompanyCommand(Integer type, CompanyCommand companyCommand);
    List<File> findByTypeAndResourcesCompanyCommand(Integer type, CompanyCommand resourcesCompanyCommand);
    List<File> findByTypeAndEmployee(Integer type, Employee employee);
    List<File> findByTypeAndPerson(Integer type, Person person);
}
