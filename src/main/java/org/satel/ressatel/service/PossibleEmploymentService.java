package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.PossibleEmployment;
import org.satel.ressatel.repository.PossibleEmploymentRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class PossibleEmploymentService {
    private final PossibleEmploymentRepository possibleEmploymentRepository;

    @Inject
    public PossibleEmploymentService(PossibleEmploymentRepository possibleEmploymentRepository) {
        this.possibleEmploymentRepository = possibleEmploymentRepository;
    }

    public void createOrUpdate(PossibleEmployment pos) {
        possibleEmploymentRepository.saveAndFlush(pos);
    }
}
