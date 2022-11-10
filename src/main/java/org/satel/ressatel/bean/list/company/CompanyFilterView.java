package org.satel.ressatel.bean.list.company;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import org.satel.ressatel.service.CompanyService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("companyFilterView")
@ViewScoped
@Getter
@Setter
public class CompanyFilterView implements Serializable {
    private List<Company> companies;
    private List<Company> filteredCompanies;
    private List<FilterMeta> filterBy;

    private boolean globalFilterOnly;
    private final CompanyService companyService;

    @Inject
    public CompanyFilterView(CompanyService companyService) {
        this.companyService = companyService;
        this.init();
    }

    public void init() {
        companies = companyService.getShortList();
        globalFilterOnly = false;
        filterBy = new ArrayList<>();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        Company company = (Company) value;
        return company.getName().toLowerCase().contains(filterText)
                || company.getInn().toLowerCase().contains(filterText)
                || company.getSkills().toLowerCase().contains(filterText);
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
