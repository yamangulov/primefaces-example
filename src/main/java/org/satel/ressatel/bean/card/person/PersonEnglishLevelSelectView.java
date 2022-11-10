package org.satel.ressatel.bean.card.person;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Person;
import org.satel.ressatel.service.EnglishLevelService;
import org.satel.ressatel.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("personEnglishLevelSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class PersonEnglishLevelSelectView {
    private String id;
    private String option;
    private String level;
    private List<String> levels;
    private final EnglishLevelService gradeService;
    private final PersonService personService;

    @Inject
    public PersonEnglishLevelSelectView(EnglishLevelService gradeService, PersonService personService) {
        this.gradeService = gradeService;
        this.personService = personService;
    }

    public void onload() {
        levels = gradeService.getEnglishLevelNames();
        Person person = personService.getByStringId(id);
        if (person.getEnglishLevel() != null) {
            level = person.getEnglishLevel().getName();
        }
    }

}
