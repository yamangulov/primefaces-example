package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.EducationGrade;
import org.satel.ressatel.repository.EducationGradeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class EducationGradeService {
    private final EducationGradeRepository educationGradeRepository;

    @Inject
    public EducationGradeService(EducationGradeRepository educationGradeRepository) {
        this.educationGradeRepository = educationGradeRepository;
    }

    public List<EducationGrade> getEducationGrades() {
        return educationGradeRepository.findAll();
    }

    public List<String> getEducationGradeNames() {
        List<EducationGrade> educationGrades = getEducationGrades();
        List<String> names = new ArrayList<>();
        educationGrades.forEach(educationGrade -> names.add(educationGrade.getName()));
        return names;
    }

    public EducationGrade getByName(String name) {
        return educationGradeRepository.findByName(name);
    }
}
