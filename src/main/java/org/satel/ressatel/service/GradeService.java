package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.Grade;
import org.satel.ressatel.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class GradeService {
    private final GradeRepository gradeRepository;

    @Inject
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    public List<String> getGradeNames() {
        List<Grade> grades = getGrades();
        List<String> names = new ArrayList<>();
        grades.forEach(grade -> names.add(grade.getName()));
        return names;
    }

    public Grade getByName(String name) {
        return gradeRepository.findByName(name);
    }
}
