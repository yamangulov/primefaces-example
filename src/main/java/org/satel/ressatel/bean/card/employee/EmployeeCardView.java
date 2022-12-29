package org.satel.ressatel.bean.card.employee;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.bean.card.file.EmployeeFileDownloadView;
import org.satel.ressatel.bean.card.file.EmployeeFileUploadView;
import org.satel.ressatel.entity.*;
import org.satel.ressatel.service.*;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("employeeCardView")
@ViewScoped
@Getter
@Setter
@Log4j2
public class EmployeeCardView {
    private String id;
    private Employee employee;
    private String firstName;
    private String secondName;
    private String surname;
    private Integer age;
    private Integer workExperience;
    private Integer costPriceRate;
    private String estimation;
    private String selfInfo;
    private String chefInfo;
    private Boolean archived;
    private Location location;
    private Contact contactObj;
    private Education education;
    private Grade grade;
    private EmployeeDepartment employeeDepartment;
    private String russianResume;
    private String englishResume;
    private EnglishLevel englishLevel;
    private List<String> hierarchySkillsProfile;
    private String skills;
    private Set<ProjectExperience> satelProjectExperienceList;
    private Occupation occupation;

    private EmployeeService employeeService;
    private FileService fileService;
    private EmployeeDepartmentService employeeDepartmentService;
    private FinDepartmentService finDepartmentService;
    private OrgDepartmentService orgDepartmentService;
    private GradeService gradeService;
    private EducationGradeService educationGradeService;
    private EnglishLevelService englishLevelService;
    private SkillService skillService;

    private EmployeeFileDownloadView employeeFileDownloadView;
    private EmployeeFileUploadView employeeFileUploadView;
    private EmployeeSkillsSelectionView employeeSkillsSelectionView;
    private EmployeeDepartmentSelectView employeeDepartmentSelectView;
    private EducationGradeSelectView educationGradeSelectView;
    private GradeSelectView gradeSelectView;
    private EnglishLevelSelectView englishLevelSelectView;

    @Inject
    public EmployeeCardView(EmployeeService employeeService,
                            FileService fileService,
                            SkillService skillService,
                            EmployeeFileDownloadView employeeFileDownloadView,
                            EmployeeFileUploadView employeeFileUploadView,
                            EmployeeSkillsSelectionView employeeSkillsSelectionView,
                            EmployeeDepartmentSelectView employeeDepartmentSelectView,
                            EducationGradeSelectView educationGradeSelectView,
                            GradeSelectView gradeSelectView,
                            EnglishLevelSelectView englishLevelSelectView,
                            EmployeeDepartmentService employeeDepartmentService,
                            FinDepartmentService finDepartmentService,
                            OrgDepartmentService orgDepartmentService,
                            GradeService gradeService,
                            EducationGradeService educationGradeService,
                            EnglishLevelService englishLevelService) {
        this.employeeService = employeeService;
        this.fileService = fileService;
        this.employeeFileDownloadView = employeeFileDownloadView;
        this.employeeFileUploadView = employeeFileUploadView;
        this.employeeSkillsSelectionView = employeeSkillsSelectionView;
        this.employeeDepartmentSelectView = employeeDepartmentSelectView;
        this.educationGradeSelectView = educationGradeSelectView;
        this.gradeSelectView = gradeSelectView;
        this.englishLevelSelectView = englishLevelSelectView;
        this.employeeDepartmentService = employeeDepartmentService;
        this.finDepartmentService = finDepartmentService;
        this.orgDepartmentService = orgDepartmentService;
        this.gradeService = gradeService;
        this.educationGradeService = educationGradeService;
        this.englishLevelService = englishLevelService;
        this.skillService = skillService;
    }

    public void onload() {
        if (Integer.parseInt(id) == 0) {
            employee = new Employee();
            employee.setEmployeeDepartment(new EmployeeDepartment());
            employee.setLocation(new Location());
            employee.setContact(new Contact());
            employee.setEducation(new Education());
            employee.setOccupation(new Occupation());
            employeeService.createOrUpdateEmployee(employee);
            id = String.valueOf(employee.getId());
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            try {
                context.redirect(context.getRequestContextPath()  + id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.employee = employeeService.getByStringId(this.id);
        this.firstName = employee.getFirstName();
        this.secondName = employee.getSecondName();
        this.surname = employee.getSurname();
        this.employeeDepartment = employee.getEmployeeDepartment();
        this.age = employee.getAge();
        this.workExperience = employee.getWorkExperience();
        this.costPriceRate = employee.getCostPriceRate();
        this.estimation = employee.getEstimation();
        this.selfInfo = employee.getSelfInfo();
        this.chefInfo = employee.getChefInfo();
        this.archived = employee.getArchived();
        this.location = employee.getLocation();
        this.education = employee.getEducation();
        this.grade = employee.getGrade();
        this.englishLevel = employee.getEnglishLevel();
        this.occupation = employee.getOccupation();
        this.contactObj = employee.getContact();
        this.satelProjectExperienceList = employee.getProjectExperiences();
        this.employeeFileDownloadView.setId(id);
        this.employeeFileDownloadView.setFileType(3);
        this.employeeFileUploadView.setFileType(3);
        this.hierarchySkillsProfile = employeeService.getHierarchicalSkills(employee);
    }

    public void onsubmit() {
        employee.setFirstName(firstName);
        employee.setSecondName(secondName);
        employee.setSurname(surname);
        employee.getLocation().setCity(location.getCity());
        employee.getLocation().setPersonalAddress(location.getPersonalAddress());
        employee.getContact().setEmail(contactObj.getEmail());
        employee.getContact().setPhone(contactObj.getPhone());
        employee.getContact().setMobile(contactObj.getMobile());
        employee.setAge(age);
        employee.setWorkExperience(workExperience);
        employee.setCostPriceRate(costPriceRate);
        employee.setEstimation(estimation);
        employee.setSelfInfo(selfInfo);
        employee.setChefInfo(chefInfo);
        employee.setArchived(archived);
        employee.setEducation(education);
        employee.setGrade(gradeService.getByName(gradeSelectView.getGrade()));
        employee.getEducation().setEducationGrade(educationGradeService.getByName(educationGradeSelectView.getEducationGrade()));
        employee.setEnglishLevel(englishLevelService.getByName(englishLevelSelectView.getLevel()));
        employee.getEmployeeDepartment().setFinDepartment(finDepartmentService.getByName(employeeDepartmentSelectView.getFinDepartment()));
        List<File> files = employeeFileUploadView.getFiles();
        files.forEach(file -> file.setEmployee(employee));
        fileService.createOrUpdate(files);
        employeeFileUploadView.setFiles(new ArrayList<>());
        employeeFileDownloadView.setStreamedContentListByEmployeeIdAndFileType(id, 3);

        //TODO перенести функционал в отдельную карточку редактирования skill профиля с учетом его изменения на новый
        //employee.setSkills(skillService.getSkillsByNames(employeeSkillsPickListView.getSkills().getTarget()));

        employeeService.createOrUpdateEmployee(employee);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
