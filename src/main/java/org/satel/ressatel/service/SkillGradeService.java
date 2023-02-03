package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.repository.SkillGradeRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class SkillGradeService {
    private final SkillGradeRepository skillGradeRepository;

    @Inject
    public SkillGradeService(SkillGradeRepository skillGradeRepository) {
        this.skillGradeRepository = skillGradeRepository;
    }
}
