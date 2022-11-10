package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.EnglishLevel;
import org.satel.ressatel.repository.EnglishLevelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class EnglishLevelService {
    private final EnglishLevelRepository englishLevelRepository;

    @Inject
    public EnglishLevelService(EnglishLevelRepository englishLevelRepository) {
        this.englishLevelRepository = englishLevelRepository;
    }

    public List<EnglishLevel> getEnglishLevels() {
        return englishLevelRepository.findAll();
    }

    public List<String> getEnglishLevelNames() {
        List<EnglishLevel> englishLevels = getEnglishLevels();
        List<String> names = new ArrayList<>();
        englishLevels.forEach(grade -> names.add(grade.getName()));
        return names;
    }

    public EnglishLevel getByName(String name) {
        return englishLevelRepository.findByName(name);
    }
}
