package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.NDAStatus;
import org.satel.ressatel.repository.NDAStatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScoped
public class NDAStatusService {
    private final NDAStatusRepository ndaStatusRepository;

    @Inject
    public NDAStatusService(NDAStatusRepository ndaStatusRepository) {
        this.ndaStatusRepository = ndaStatusRepository;
    }

    public String getById(Integer id) {
        NDAStatus ndaStatus = ndaStatusRepository.findById(id).orElse(null);
        if (ndaStatus != null) {
            return ndaStatus.getName();
        }
        return null;
    }

    List<NDAStatus> getNDAStatuses() {
        return ndaStatusRepository.findAll();
    }

    public List<String> getNDAStatusNames() {
        List<NDAStatus> taxes = getNDAStatuses();
        List<String> names = new ArrayList<>();
        taxes.forEach(tax -> {
            names.add(tax.getName());
        });
        return names;
    }

    public NDAStatus getByName(String name) {
        return ndaStatusRepository.findByName(name);
    }
}
