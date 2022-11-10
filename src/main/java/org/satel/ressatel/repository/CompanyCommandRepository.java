package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Company;
import org.satel.ressatel.entity.CompanyCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCommandRepository extends JpaRepository<CompanyCommand, Integer> {
    CompanyCommand getByCompany(Company company);
}
