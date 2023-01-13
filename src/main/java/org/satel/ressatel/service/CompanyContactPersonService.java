package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.repository.CompanyContactPersonRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class CompanyContactPersonService {
    private final CompanyContactPersonRepository companyContactPersonRepository;

    @Inject
    public CompanyContactPersonService(CompanyContactPersonRepository companyContactPersonRepository) {
        this.companyContactPersonRepository = companyContactPersonRepository;
    }
}
