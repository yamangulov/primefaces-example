package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.Tax;
import org.satel.ressatel.repository.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class TaxService {
    private final TaxRepository taxRepository;

    @Inject
    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public String getById(Integer id) {
        Tax tax = taxRepository.findById(id).orElse(null);
        if (tax != null) {
            return tax.getName();
        }
        return null;
    }

    List<Tax> getTaxes() {
        return taxRepository.findAll();
    }

    public List<String> getTaxNames() {
        List<Tax> taxes = getTaxes();
        List<String> names = new ArrayList<>();
        taxes.forEach(tax -> {
            names.add(tax.getName());
        });
        return names;
    }

    public Tax getByName(String name) {
        return taxRepository.findByName(name);
    }
}
