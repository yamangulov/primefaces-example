package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Integer> {
    Tax findByName(String name);
}
