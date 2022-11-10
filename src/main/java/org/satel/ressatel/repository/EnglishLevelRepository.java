package org.satel.ressatel.repository;

import org.satel.ressatel.entity.EnglishLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishLevelRepository extends JpaRepository<EnglishLevel, Integer> {
    EnglishLevel findByName(String name);
}
