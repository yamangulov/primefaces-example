package org.satel.ressatel.bean.card.person;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.satel.ressatel.entity.Person;
import org.satel.ressatel.service.PersonService;
import org.satel.ressatel.service.TaxService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("personTaxSelectView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class PersonTaxSelectView {
    private String id;
    private String option;
    private String tax;
    private List<String> taxes;
    private final TaxService taxService;
    private final PersonService personService;

    @Inject
    public PersonTaxSelectView(TaxService taxService, PersonService personService) {
        this.taxService = taxService;
        this.personService = personService;
    }

    public void onload() {
        taxes = taxService.getTaxNames();
        Person person = personService.getByStringId(id);
        if (person.getTaxes() != null) {
            tax = taxService.getById(person.getTaxes());
        }
    }

}
