package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Company;
import org.satel.ressatel.entity.CompanyCommand;
import org.satel.ressatel.repository.CompanyCommandRepository;
import org.springframework.stereotype.Component;

@Component
@ApplicationScoped
@Log4j2
public class CompanyCommandService {
    private final CompanyCommandRepository companyCommandRepository;

    @Inject
    public CompanyCommandService(CompanyCommandRepository companyCommandRepository) {
        this.companyCommandRepository = companyCommandRepository;
    }

    public CompanyCommand getById(Integer id) {
        return companyCommandRepository.findById(id).orElse(null);
    }

    public CompanyCommand getByCompany(Company company) {
        return companyCommandRepository.getByCompany(company);
    }

    public void createOrUpdate(CompanyCommand companyCommand) {
        companyCommandRepository.save(companyCommand);
    }
}
