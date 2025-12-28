package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.LocationResponse;
import com.durgamandir.donation.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationService {
    
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final ThanaRepository thanaRepository;
    private final VillageRepository villageRepository;
    
    public LocationService(CountryRepository countryRepository,
                           StateRepository stateRepository,
                           DistrictRepository districtRepository,
                           ThanaRepository thanaRepository,
                           VillageRepository villageRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.thanaRepository = thanaRepository;
        this.villageRepository = villageRepository;
    }
    
    public List<LocationResponse> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(c -> new LocationResponse(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }
    
    public List<LocationResponse> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountryId(countryId)
                .stream()
                .map(s -> new LocationResponse(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }
    
    public List<LocationResponse> getDistrictsByStateId(Long stateId) {
        return districtRepository.findByStateId(stateId)
                .stream()
                .map(d -> new LocationResponse(d.getId(), d.getName()))
                .collect(Collectors.toList());
    }
    
    public List<LocationResponse> getThanasByDistrictId(Long districtId) {
        return thanaRepository.findByDistrictId(districtId)
                .stream()
                .map(t -> new LocationResponse(t.getId(), t.getName()))
                .collect(Collectors.toList());
    }
    
    public List<LocationResponse> getVillagesByThanaId(Long thanaId) {
        return villageRepository.findByThanaIdAndActiveTrue(thanaId)
                .stream()
                .map(v -> new LocationResponse(v.getId(), v.getName()))
                .collect(Collectors.toList());
    }
    
    // Helper methods for resolving names from IDs
    public String getCountryName(Long countryId) {
        return countryRepository.findById(countryId)
                .map(c -> c.getName())
                .orElse("Unknown");
    }
    
    public String getStateName(Long stateId) {
        return stateRepository.findById(stateId)
                .map(s -> s.getName())
                .orElse("Unknown");
    }
    
    public String getDistrictName(Long districtId) {
        return districtRepository.findById(districtId)
                .map(d -> d.getName())
                .orElse("Unknown");
    }
    
    public String getThanaName(Long thanaId) {
        return thanaRepository.findById(thanaId)
                .map(t -> t.getName())
                .orElse("Unknown");
    }
    
    public String getVillageName(Long villageId) {
        return villageRepository.findById(villageId)
                .map(v -> v.getName())
                .orElse("Unknown");
    }
}

