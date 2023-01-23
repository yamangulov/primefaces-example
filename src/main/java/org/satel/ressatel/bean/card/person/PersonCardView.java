package org.satel.ressatel.bean.card.person;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.bean.card.file.PersonFileDownloadView;
import org.satel.ressatel.bean.card.file.PersonFileUploadView;
import org.satel.ressatel.entity.*;
import org.satel.ressatel.service.*;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component("personCardView")
@ViewScoped
@Getter
@Setter
@Log4j2
public class PersonCardView {
    private String id;
    private Person person;
    private String firstName;
    private String secondName;
    private String surname;
    private String inn;
    private Integer age;
    private Integer costPriceRate;
    private String estimation;
    private String taxes;
    private String ndaStatus;
    private String socnetworks;
    private List<String> socnetworksList;
    private Location location;
    private Contact contactObj;
    private EnglishLevel englishLevel;
    private Grade grade;
    private String russianResume;
    private String englishResume;
    private List<String> hierarchySkillsProfile;
    private Set<Role> specialties;
    private PossibleEmployment possibleEmployment;
    private Set<ProjectExperience> satelProjectExperienceList;
    private final PersonFileDownloadView personFileDownloadView;
    private final PersonFileUploadView personFileUploadView;
    private final PersonNDASelectView personNDASelectView;
    private final PersonTaxSelectView personTaxSelectView;
    private final PersonGradeSelectView personGradeSelectView;
    private final PersonEnglishLevelSelectView personEnglishLevelSelectView;
    private final PersonService personService;
    private final NDAStatusService ndaStatusService;
    private final TaxService taxService;
    private final GradeService gradeService;
    private final EnglishLevelService englishLevelService;
    private final LocationService locationService;
    private final ContactService contactService;
    private final PossibleEmploymentService possibleEmploymentService;
    private final FileService fileService;

    @Inject
    public PersonCardView(
            PersonFileDownloadView personFileDownloadView,
            PersonFileUploadView personFileUploadView,
            PersonNDASelectView personNDASelectView,
            PersonTaxSelectView personTaxSelectView,
            PersonGradeSelectView personGradeSelectView,
            PersonEnglishLevelSelectView personEnglishLevelSelectView,
            PersonService personService,
            NDAStatusService ndaStatusService,
            TaxService taxService,
            GradeService gradeService,
            EnglishLevelService englishLevelService,
            LocationService locationService,
            ContactService contactService,
            PossibleEmploymentService possibleEmploymentService,
            FileService fileService) {
        this.personFileDownloadView = personFileDownloadView;
        this.personFileUploadView = personFileUploadView;
        this.personNDASelectView = personNDASelectView;
        this.personTaxSelectView = personTaxSelectView;
        this.personGradeSelectView = personGradeSelectView;
        this.personEnglishLevelSelectView = personEnglishLevelSelectView;
        this.personService = personService;
        this.ndaStatusService = ndaStatusService;
        this.taxService = taxService;
        this.gradeService = gradeService;
        this.englishLevelService = englishLevelService;
        this.locationService = locationService;
        this.contactService = contactService;
        this.possibleEmploymentService = possibleEmploymentService;
        this.fileService = fileService;
    }

    public void onload() {
        if (Integer.parseInt(id) == 0) {
            person = new Person();
            person.setLocation(new Location());
            person.setContact(new Contact());
            person.setOccupation(new Occupation());
            person.setPossibleEmployment(new PossibleEmployment());
            personService.createOrUpdatePerson(person);
            id = String.valueOf(person.getId());
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            try {
                context.redirect(context.getRequestContextPath()  + id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.person = personService.getByStringId(id);
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.surname = person.getSurname();
        this.inn = person.getInn();
        this.age = person.getAge();
        this.costPriceRate = person.getCostPriceRate();
        this.estimation = person.getEstimation();
        if (person.getTaxes() != null) {
            this.taxes = taxService.getById(person.getTaxes());
        }
        if (person.getNdaStatus() != null) {
            this.ndaStatus = ndaStatusService.getById(person.getNdaStatus());
        }
        if (person.getSocnetworks() != null && !person.getSocnetworks().isBlank()) {
            this.socnetworks = person.getSocnetworks();
            this.socnetworksList = getPersonSocnetworkList(socnetworks);
            this.socnetworksList.forEach(item -> {
                if (item.isBlank()) {
                    item = null;
                }
            });
        } else {
            this.socnetworksList = new ArrayList<>();
        }
        if (person.getLocation() == null) {
            Location loc = new Location();
            locationService.createOrUpdate(loc);
            this.location = loc;
            person.setLocation(loc);
        } else {
            this.location = person.getLocation();   
        }
        if (person.getPossibleEmployment() == null) {
            PossibleEmployment pos = new PossibleEmployment();
            possibleEmploymentService.createOrUpdate(pos);
            this.possibleEmployment = pos;
            person.setPossibleEmployment(pos);
        } else {
            this.possibleEmployment = person.getPossibleEmployment();
        }
        this.grade = person.getGrade();
        this.englishLevel = person.getEnglishLevel();
        if (person.getContact() == null) {
            Contact con = new Contact();
            contactService.createOrUpdate(con);
            this.contactObj = con;
            person.setContact(con);
        } else {
            this.contactObj = person.getContact();
        }
        this.satelProjectExperienceList = person.getSatelProjectExperiences();
        this.personFileDownloadView.setId(id);
        this.personFileDownloadView.setFileType(4);
        this.personFileUploadView.setId(Integer.getInteger(id));
        this.personFileUploadView.setFileType(4);
    }

    private List<String> getPersonSocnetworkList(String socnetworks) {
        return new ArrayList<>(Arrays.asList(socnetworks.split(", ")));
    }

    public void onsubmit() {
        person.setFirstName(firstName);
        person.setSecondName(secondName);
        person.setSurname(surname);
        person.setInn(inn);
        person.setAge(age);
        person.setCostPriceRate(costPriceRate);
        person.setEstimation(estimation);
        if (personTaxSelectView.getTax() != null && !personTaxSelectView.getTax().isBlank()) {
            Tax tax = taxService.getByName(personTaxSelectView.getTax());
            person.setTaxes(tax.getId());
            taxes = tax.getName();
        }
        if (personNDASelectView.getNda() != null && !personNDASelectView.getNda().isBlank()) {
            NDAStatus status = ndaStatusService.getByName(personNDASelectView.getNda());
            person.setNdaStatus(status.getId());
            ndaStatus = status.getName();
        }
        person.setGrade(gradeService.getByName(personGradeSelectView.getGrade()));
        person.setEnglishLevel(englishLevelService.getByName(personEnglishLevelSelectView.getLevel()));
        person.getLocation().setCity(location.getCity());
        person.getLocation().setPersonalAddress(location.getPersonalAddress());
        person.setSocnetworks(String.join(", ", socnetworksList));

        List<File> files = personFileUploadView.getFiles();
        files.forEach(file -> file.setPerson(person));
        fileService.createOrUpdate(files);
        personFileUploadView.setFiles(new ArrayList<>());
        personFileDownloadView.setStreamedContentListByPersonIdAndFileType(id, 4);

        personService.createOrUpdatePerson(person);

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSocnetwork() {
        socnetworksList.add("");
    }
}
