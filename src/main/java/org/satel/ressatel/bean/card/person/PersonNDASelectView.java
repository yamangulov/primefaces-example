package org.satel.ressatel.bean.card.person;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Person;
import org.satel.ressatel.service.NDAStatusService;
import org.satel.ressatel.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("personNDASelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class PersonNDASelectView {
    private String id;
    private String option;
    private String nda;
    private List<String> ndas;
    private final NDAStatusService ndaStatusService;
    private final PersonService personService;

    @Inject
    public PersonNDASelectView(NDAStatusService ndaStatusService, PersonService personService) {
        this.ndaStatusService = ndaStatusService;
        this.personService = personService;
    }

    public void onload() {
        ndas = ndaStatusService.getNDAStatusNames();
        Person person = personService.getByStringId(id);
        if (person.getNdaStatus() != null) {
            nda = ndaStatusService.getById(person.getNdaStatus());
        }
    }

}
