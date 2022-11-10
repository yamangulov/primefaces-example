package org.satel.ressatel.repository;

import org.satel.ressatel.entity.EducationGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationGradeRepository extends JpaRepository<EducationGrade, Integer> {
    EducationGrade findByName(String name);
}
