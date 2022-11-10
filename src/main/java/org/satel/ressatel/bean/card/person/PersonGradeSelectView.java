package org.satel.ressatel.bean.card.person;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Person;
import org.satel.ressatel.service.GradeService;
import org.satel.ressatel.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("personGradeSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class PersonGradeSelectView {
    private String id;
    private String option;
    private String grade;
    private List<String> grades;
    private final GradeService gradeService;
    private final PersonService personService;

    @Inject
    public PersonGradeSelectView(GradeService gradeService, PersonService personService) {
        this.gradeService = gradeService;
        this.personService = personService;
    }

    public void onload() {
        grades = gradeService.getGradeNames();
        Person person = personService.getByStringId(id);
        if (person.getGrade() != null) {
            grade = person.getGrade().getName();
        }
    }

}
