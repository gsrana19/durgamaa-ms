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
    
    /**
     * Get default location IDs for temple location (India -> Jharkhand -> Hazaribag -> Ichak -> Mangura)
     * Used when creating donation records from payment confirmations
     * @return array [countryId, stateId, districtId, thanaId, villageId] or null if not found
     */
    public Long[] getDefaultLocationIds() {
        try {
            // Find India
            Long countryId = countryRepository.findByName("India")
                    .map(c -> c.getId())
                    .orElse(null);
            if (countryId == null) return null;
            
            // Find Jharkhand
            Long stateId = stateRepository.findByNameAndCountryId("Jharkhand", countryId)
                    .map(s -> s.getId())
                    .orElse(null);
            if (stateId == null) return null;
            
            // Find Hazaribag
            Long districtId = districtRepository.findByNameAndStateId("Hazaribag", stateId)
                    .map(d -> d.getId())
                    .orElse(null);
            if (districtId == null) return null;
            
            // Find Ichak
            Long thanaId = thanaRepository.findByNameAndDistrictId("Ichak", districtId)
                    .map(t -> t.getId())
                    .orElse(null);
            if (thanaId == null) return null;
            
            // Find Mangura
            Long villageId = villageRepository.findByNameAndThanaId("Mangura", thanaId)
                    .map(v -> v.getId())
                    .orElse(null);
            
            return new Long[]{countryId, stateId, districtId, thanaId, villageId};
        } catch (Exception e) {
            return null;
        }
    }
}






