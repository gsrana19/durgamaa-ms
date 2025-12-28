package com.durgamandir.donation.controller;

import com.durgamandir.donation.dto.LocationResponse;
import com.durgamandir.donation.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {
    
    private final LocationService locationService;
    
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    @GetMapping("/countries")
    public ResponseEntity<List<LocationResponse>> getCountries() {
        List<LocationResponse> countries = locationService.getAllCountries();
        return ResponseEntity.ok(countries);
    }
    
    @GetMapping("/states")
    public ResponseEntity<List<LocationResponse>> getStates(@RequestParam Long countryId) {
        List<LocationResponse> states = locationService.getStatesByCountryId(countryId);
        return ResponseEntity.ok(states);
    }
    
    @GetMapping("/districts")
    public ResponseEntity<List<LocationResponse>> getDistricts(@RequestParam Long stateId) {
        List<LocationResponse> districts = locationService.getDistrictsByStateId(stateId);
        return ResponseEntity.ok(districts);
    }
    
    @GetMapping("/thanas")
    public ResponseEntity<List<LocationResponse>> getThanas(@RequestParam Long districtId) {
        List<LocationResponse> thanas = locationService.getThanasByDistrictId(districtId);
        return ResponseEntity.ok(thanas);
    }
    
    @GetMapping("/villages")
    public ResponseEntity<List<LocationResponse>> getVillages(@RequestParam Long thanaId) {
        List<LocationResponse> villages = locationService.getVillagesByThanaId(thanaId);
        return ResponseEntity.ok(villages);
    }
}

