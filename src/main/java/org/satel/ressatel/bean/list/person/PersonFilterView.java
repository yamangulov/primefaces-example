package org.satel.ressatel.bean.list.person;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import org.satel.ressatel.service.PersonService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("personFilterView")
@ViewScoped
@Getter
@Setter
public class PersonFilterView implements Serializable {

    private List<Person> persons;
    private List<Person> filteredPersons;
    private List<FilterMeta> filterBy;

    private boolean globalFilterOnly;
    private final PersonService personService;

    @Inject
    public PersonFilterView(PersonService personService) {
        this.personService = personService;
        this.init();
    }

    public void init() {
        persons = personService.getShortList();
        globalFilterOnly = false;
        filterBy = new ArrayList<>();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Person person = (Person) value;
        return person.getName().toLowerCase().contains(filterText)
                || person.getInn().toLowerCase().contains(filterText)
                || person.getSpecialties().toLowerCase().contains(filterText)
                || person.getSkills().toLowerCase().contains(filterText);
    }

    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }
}
