package org.satel.ressatel.repository;

import org.satel.ressatel.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Grade findByName(String name);
}
