package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.bean.list.company.Company;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@ApplicationScoped
@Log4j2
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverHost;

    @Value("${server.protocol}")
    private String serverProtocol;


    @Inject
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public List<Company> getShortList() {
        List<Company> companies = new ArrayList<>();
        List<org.satel.ressatel.entity.Company> fullCompanies = getList();
        fullCompanies.forEach(fullCompany -> {
            Company company = new Company(
                    fullCompany.getName(),
                    fullCompany.getInn(),
                    getSkills(fullCompany),
                    getLink(fullCompany)
            );
            companies.add(company);
        });
        return companies;
    }

    private String getLink(org.satel.ressatel.entity.Company fullCompany) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serverProtocol);
        stringBuilder.append("://");
        stringBuilder.append(serverHost);
        stringBuilder.append(":");
        stringBuilder.append(serverPort);
        stringBuilder.append("/company/");
        stringBuilder.append(fullCompany.getId());
        return stringBuilder.toString();
    }

    public List<org.satel.ressatel.entity.Company> getList() {
        return companyRepository.findAll();
    }

    public List<String> getCompanyHierarchicalSkillsRepresentation(org.satel.ressatel.entity.Company fullCompany) {
        if (fullCompany.getCompanySkill() != null) {
            List<Skill> skills = new ArrayList<>();
            if (fullCompany.getCompanySkill() != null
                    && fullCompany.getCompanySkill().getCompanySkillToSkills() != null) {
                fullCompany.getCompanySkill().getCompanySkillToSkills().forEach(companySkillToSkills -> skills.add(companySkillToSkills.getSkill()));
                return getHierarchicalSkillsRepresentation(skills);
            }
        }
        return null;
    }

    private List<String> getHierarchicalSkillsRepresentation(List<Skill> skills) {
        List<String> strings = new ArrayList<>();
        skills.forEach(skill -> {
            if (skill.getParent() == null) {
                AtomicReference<Integer> level = new AtomicReference<>(0);
                addSkills(skill, strings, skills, level);
            }
        });
        List<String> hierarchy = new ArrayList<>();
        strings.forEach(s -> {
            String first = s.substring(0, 1);
            String other = s.substring(1);
            hierarchy.add("-".repeat(Integer.parseInt(first)) + other);
        });
        return hierarchy;
    }

    private void addSkills(Skill skill, List<String> strings, List<Skill> skills, AtomicReference<Integer> level) {
        strings.add(level.get() + skill.getName());
        skills.forEach(skl -> {
            if (skl.getParent() != null && skl.getParent().getId().intValue() == skill.getId().intValue()) {
                level.getAndSet(level.get() + 1);
                addSkills(skl, strings, skills, level);
                level.getAndSet(level.get() - 1);
            }
        });
    }

    public String getSkills(org.satel.ressatel.entity.Company fullCompany) {
        if (fullCompany.getCompanySkill() != null) {
            List<Skill> skills = new ArrayList<>();
            if (fullCompany.getCompanySkill() != null
                    && fullCompany.getCompanySkill().getCompanySkillToSkills() != null) {
                fullCompany.getCompanySkill().getCompanySkillToSkills().forEach(companySkillToSkills -> skills.add(companySkillToSkills.getSkill()));
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < skills.size(); i++) {
                    stringBuilder.append(skills.get(i).getName());
                    if (i != skills.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                return stringBuilder.toString();
            }
        }
        return null;
    }

    public List<String> getSkillStringList(Integer companyId) {
        org.satel.ressatel.entity.Company fullCompany = getByStringId(String.valueOf(companyId));
        List<String> skills = new ArrayList<>();
        if (fullCompany.getCompanySkill() != null) {
            fullCompany.getCompanySkill().getCompanySkillToSkills().forEach(companySkillToSkills -> skills.add(companySkillToSkills.getSkill().getName()));
        }
        return skills;
    }

    public org.satel.ressatel.entity.Company getByStringId(String id) {
        return companyRepository.findById(Integer.valueOf(id)).orElse(null);
    }

    public org.satel.ressatel.entity.Company getById(Integer id) {
        return companyRepository.getReferenceById(id);
    }

    public void createOrUpdateCompany(org.satel.ressatel.entity.Company company) {
        companyRepository.save(company);
    }
}
