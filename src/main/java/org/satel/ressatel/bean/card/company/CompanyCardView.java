package org.satel.ressatel.bean.card.company;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.bean.card.file.FileDownloadView;
import org.satel.ressatel.bean.card.file.FileUploadView;
import org.satel.ressatel.bean.card.file.ResourceFileDownloadView;
import org.satel.ressatel.bean.card.file.ResourceFileUploadView;
import org.satel.ressatel.bean.card.general.PickListView;
import org.satel.ressatel.entity.*;
import org.satel.ressatel.service.*;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component("companyCardView")
@ViewScoped
@Getter
@Setter
@Log4j2
public class CompanyCardView implements Serializable {
    private String id;
    private Company company;
    private String name;
    private String inn;
    private String site;
    private Integer taxes;
    private String ndaStatus;
    private String socnetworks;
    private List<String> socnetworksList;
    private Location location;
    private Contact contactObj;
    private CompanyContactPerson companyContactPerson;
    private String skillsProfile;
    private List<String> hierarchySkillsProfile;
    private String skillsComment;
    List<CompanyProjectExperience> satelProjectExperienceList;
    private CompanyCommand companyCommand;
    private String commandDescr;
    private String commandConditions;
    private CompanyService companyService;
    private FileService fileService;
    private SkillService skillService;
    private CompanySkillToSkillsService companySkillToSkillsService;
    private CompanyCommandService companyCommandService;

    private FileDownloadView fileDownloadView;
    private ResourceFileDownloadView resourceFileDownloadView;
    private FileUploadView fileUploadView;
    private ResourceFileUploadView resourceFileUploadView;
    private PickListView pickListView;

    @Inject
    public CompanyCardView(
            CompanyService companyService,
            FileService fileService,
            SkillService skillService,
            CompanySkillToSkillsService companySkillToSkillsService,
            CompanyCommandService companyCommandService,
            FileDownloadView fileDownloadView,
            ResourceFileDownloadView resourceFileDownloadView,
            FileUploadView fileUploadView,
            ResourceFileUploadView resourceFileUploadView,
            PickListView pickListView) {
        this.companyService = companyService;
        this.fileService = fileService;
        this.companySkillToSkillsService = companySkillToSkillsService;
        this.companyCommandService = companyCommandService;
        this.fileDownloadView = fileDownloadView;
        this.resourceFileDownloadView = resourceFileDownloadView;
        this.fileUploadView = fileUploadView;
        this.resourceFileUploadView = resourceFileUploadView;
        this.pickListView = pickListView;
        this.skillService = skillService;
    }

    public void onload() {
        if (Integer.parseInt(id) == 0) {
            company = new Company();
            company.setLocation(new Location());
            company.setContact(new Contact());
            company.setCompanyContactPerson(new CompanyContactPerson());
            company.setCompanySkill(new CompanySkill());
            CompanyCommand companyCommand = new CompanyCommand();
            companyCommandService.createOrUpdate(companyCommand);
            company.setCompanyCommand(companyCommand);
            companyService.createOrUpdateCompany(company);
            id = String.valueOf(company.getId());
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            try {
                context.redirect(context.getRequestContextPath()  + id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.company = companyService.getByStringId(this.id);
        this.name = company.getName();
        this.inn = company.getInn();
        this.site = (company.getSite() == null || company.getSite().isBlank()) ? null : company.getSite();
        this.taxes = company.getTaxes();
        this.ndaStatus = company.getNdaStatus();
        if (company.getSocnetworks() != null && !company.getSocnetworks().isBlank()) {
            this.socnetworks = company.getSocnetworks();
            this.socnetworksList = getCompanySocnetworksList(socnetworks);
            this.socnetworksList.forEach(item -> {
                if (item.isBlank()) {
                    item = null;
                }
            });
        } else {
            this.socnetworksList = new ArrayList<>();
        }
        if (company.getLocation() == null) {
            company.setLocation(new Location());
            companyService.createOrUpdateCompany(company);
        }
        this.location = company.getLocation();
        if (company.getContact() == null) {
            company.setContact(new Contact());
            companyService.createOrUpdateCompany(company);
        }
        this.contactObj = company.getContact();
        if (company.getCompanyContactPerson() == null) {
            company.setCompanyContactPerson(new CompanyContactPerson());
            companyService.createOrUpdateCompany(company);
        }
        this.companyContactPerson = company.getCompanyContactPerson();
        this.skillsProfile = getCompanySkillProfile();
        this.hierarchySkillsProfile = companyService.getCompanyHierarchicalSkillsRepresentation(company);
        if (company.getCompanySkill() != null) {
            this.skillsComment = company.getCompanySkill().getDescr();
        }
        this.satelProjectExperienceList = company.getSatelProjectExperiences();
        if (company.getCompanyCommand() == null) {
            companyCommand = new CompanyCommand();
            companyCommandService.createOrUpdate(companyCommand);
            companyCommand.setCompany(company);
            companyService.createOrUpdateCompany(company);
        }
        this.commandDescr =
                company.getCompanyCommand() != null ? company.getCompanyCommand().getDescr() : null;
        this.commandConditions =
                company.getCompanyCommand() != null ? company.getCompanyCommand().getConditions() : null;
        this.fileDownloadView.setId(id);
        this.fileDownloadView.setFileType(1);
        this.resourceFileDownloadView.setId(id);
        this.resourceFileDownloadView.setFileType(2);
        this.fileUploadView.setId(Integer.getInteger(id));
        this.fileUploadView.setFileType(1);
        this.resourceFileUploadView.setId(Integer.getInteger(id));
        this.resourceFileUploadView.setFileType(2);
    }

    private List<String> getCompanySocnetworksList(String socnetworks) {
        return new ArrayList<>(Arrays.asList(socnetworks.split(", ")));
    }

    private String getCompanySkillProfile() {
        return companyService.getSkills(company);
    }

    public void onsubmit() {
        this.company = companyService.getByStringId(this.id);
        company.setName(name);
        company.setInn(inn);
        if (company.getLocation() == null) {
            company.setLocation(new Location());
            companyService.createOrUpdateCompany(company);
        }
        company.getLocation().setCity(location.getCity());
        company.getLocation().setOfficeAddress(location.getOfficeAddress());
        if (company.getContact() == null) {
            company.setContact(new Contact());
            companyService.createOrUpdateCompany(company);
        }
        company.getContact().setEmail(contactObj.getEmail());
        company.getContact().setPhone(contactObj.getPhone());
        company.getContact().setMobile(contactObj.getMobile());
        if (company.getCompanyContactPerson() == null) {
            company.setCompanyContactPerson(new CompanyContactPerson());
            companyService.createOrUpdateCompany(company);
        }
        company.getCompanyContactPerson().setFirstName(companyContactPerson.getFirstName());
        company.getCompanyContactPerson().setSecondName(companyContactPerson.getSecondName());
        company.getCompanyContactPerson().setSurname(companyContactPerson.getSurname());
        company.getCompanyContactPerson().setPhone(companyContactPerson.getPhone());
        company.getCompanyContactPerson().setEmail(companyContactPerson.getEmail());
        company.getCompanyContactPerson().setTelegram(companyContactPerson.getTelegram());
        company.setSite(site);
        company.setSocnetworks(String.join(", ", socnetworksList));
        company.setTaxes(taxes);
        company.setNdaStatus(ndaStatus);
        company.getCompanySkill().setDescr(skillsComment);
        Integer companySkillId = company.getCompanySkill().getId();

        Set<CompanySkillToSkills> oldCompanySkillToSkills = company.getCompanySkill().getCompanySkillToSkills();
        companySkillToSkillsService.deleteAll(oldCompanySkillToSkills);

        Set<Skill> newSkills = skillService.getSkillsByNames(pickListView.getSkills().getTarget());
        newSkills = SkillService.getSkillsWithParents(newSkills);
        List<CompanySkillToSkills> newCompanySkillToSkills = new ArrayList<>();
        newSkills.forEach(skill -> {
            CompanySkillToSkills skillToSkills = new CompanySkillToSkills();
            skillToSkills.setCompanySkillId(companySkillId);
            skillToSkills.setSkill(skill);
            newCompanySkillToSkills.add(skillToSkills);
        });
        companySkillToSkillsService.saveAll(newCompanySkillToSkills);

        if (companyCommand == null) {
            companyCommand = new CompanyCommand();
            companyCommand.setCompany(company);
            companyCommandService.createOrUpdate(companyCommand);
        }
        companyCommand.setDescr(commandDescr);
        companyCommand.setConditions(commandConditions);
        company.setCompanyCommand(companyCommand);

        List<File> files = fileUploadView.getFiles();
        files.forEach(file -> file.setCompanyCommand(company.getCompanyCommand()));
        fileService.createOrUpdate(files);
        fileUploadView.setFiles(new ArrayList<>());
        fileDownloadView.setStreamedContentListByCompanyIdAndFileType(id, 1);

        List<File> resourceFiles = resourceFileUploadView.getFiles();
        resourceFiles.forEach(file -> file.setResourcesCompanyCommand(company.getCompanyCommand()));
        fileService.createOrUpdate(resourceFiles);
        resourceFileUploadView.setFiles(new ArrayList<>());
        resourceFileDownloadView.setStreamedContentListByCompanyIdAndFileType(id, 2);

        companyService.createOrUpdateCompany(company);
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
