package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.Location;
import org.satel.ressatel.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class LocationService {
    private final LocationRepository locationRepository;

    @Inject
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void createOrUpdate(Location loc) {
        locationRepository.saveAndFlush(loc);
    }
}
