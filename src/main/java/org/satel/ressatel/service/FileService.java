package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.CompanyCommand;
import org.satel.ressatel.entity.Employee;
import org.satel.ressatel.entity.File;
import org.satel.ressatel.entity.Person;
import org.satel.ressatel.repository.FileRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ApplicationScoped
@Log4j2
public class FileService {
    private final FileRepository fileRepository;
    private final CompanyCommandService companyCommandService;

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final PersonService personService;

    @Inject
    public FileService(FileRepository fileRepository, CompanyCommandService companyCommandService, EmployeeService employeeService, CompanyService companyService, PersonService personService) {
        this.fileRepository = fileRepository;
        this.companyCommandService = companyCommandService;
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.personService = personService;
    }

    public List<File> getFilesByCompanyIdAndType(String companyId, Integer fileType) {
        CompanyCommand companyCommand = companyCommandService.getByCompany(companyService.getById(Integer.valueOf(companyId)));
        return fileRepository.findByTypeAndCompanyCommand(fileType, companyCommand);
    }

    public List<File> getFilesByResourcesCompanyIdAndType(String resourcesCompanyId, Integer fileType) {
        CompanyCommand resourcesCompanyCommand = companyCommandService.getByCompany(companyService.getById(Integer.valueOf(resourcesCompanyId)));
        return fileRepository.findByTypeAndResourcesCompanyCommand(fileType, resourcesCompanyCommand);
    }

    public void createOrUpdate(List<File> files) {
        fileRepository.saveAll(files);
    }

    public List<File> getFilesByEmployeeIdAndType(String employeeId, Integer fileType) {
        Employee employee = employeeService.getByStringId(employeeId);
        return fileRepository.findByTypeAndEmployee(fileType, employee);
    }

    public List<File> getFilesByPersonIdAndType(String personId, Integer fileType) {
        Person person = personService.getByStringId(personId);
        return fileRepository.findByTypeAndPerson(fileType, person);
    }
}
