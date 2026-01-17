package com.durgamandir.donation.config;

import com.durgamandir.donation.entity.*;
import com.durgamandir.donation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    private ThanaRepository thanaRepository;
    
    @Autowired
    private VillageRepository villageRepository;
    
    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
    private UpdateRepository updateRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialize admin user - update or create
        User existingAdmin = userRepository.findByUserId("admin").orElse(null);
        if (existingAdmin == null) {
            // Create new admin user
            User admin = new User();
            admin.setUserId("admin");
            String encodedPassword = passwordEncoder.encode("admin123");
            admin.setPasswordHash(encodedPassword);
            admin.setRole("ROLE_ADMIN");
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("========================================");
            System.out.println("Admin user created successfully!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("Encoded Password: " + encodedPassword);
            System.out.println("========================================");
        } else {
            // Update existing admin password to ensure it's correct
            String encodedPassword = passwordEncoder.encode("admin123");
            existingAdmin.setPasswordHash(encodedPassword);
            existingAdmin.setRole("ROLE_ADMIN");
            userRepository.save(existingAdmin);
            System.out.println("========================================");
            System.out.println("Admin user password reset!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("Encoded Password: " + encodedPassword);
            System.out.println("========================================");
        }
        
        // Initialize location data
        Country india = null;
        if (countryRepository.count() == 0) {
            india = new Country();
            india.setName("India");
            india.setCode("IN");
            india = countryRepository.save(india);
        } else {
            india = countryRepository.findAll().stream()
                .filter(c -> "India".equals(c.getName()))
                .findFirst()
                .orElse(null);
        }
        
        if (india != null && stateRepository.count() == 0) {
            State jharkhand = new State();
            jharkhand.setName("Jharkhand");
            jharkhand.setCode("JH");
            jharkhand.setCountry(india);
            jharkhand = stateRepository.save(jharkhand);
            
            State bihar = new State();
            bihar.setName("Bihar");
            bihar.setCode("BR");
            bihar.setCountry(india);
            bihar = stateRepository.save(bihar);
            
            State westBengal = new State();
            westBengal.setName("West Bengal");
            westBengal.setCode("WB");
            westBengal.setCountry(india);
            stateRepository.save(westBengal);
            
            State up = new State();
            up.setName("Uttar Pradesh");
            up.setCode("UP");
            up.setCountry(india);
            stateRepository.save(up);
            
            // Districts
            District hazaribag = new District();
            hazaribag.setName("Hazaribag");
            hazaribag.setState(jharkhand);
            hazaribag = districtRepository.save(hazaribag);
            
            District ranchi = new District();
            ranchi.setName("Ranchi");
            ranchi.setState(jharkhand);
            ranchi = districtRepository.save(ranchi);
            
            District dhanbad = new District();
            dhanbad.setName("Dhanbad");
            dhanbad.setState(jharkhand);
            districtRepository.save(dhanbad);
            
            District patna = new District();
            patna.setName("Patna");
            patna.setState(bihar);
            districtRepository.save(patna);
            
            District gaya = new District();
            gaya.setName("Gaya");
            gaya.setState(bihar);
            districtRepository.save(gaya);
            
            // Thanas
            Thana ichak = new Thana();
            ichak.setName("Ichak");
            ichak.setDistrict(hazaribag);
            ichak = thanaRepository.save(ichak);
            
            Thana hazaribagThana = new Thana();
            hazaribagThana.setName("Hazaribag");
            hazaribagThana.setDistrict(hazaribag);
            thanaRepository.save(hazaribagThana);
            
            Thana kanke = new Thana();
            kanke.setName("Kanke");
            kanke.setDistrict(ranchi);
            thanaRepository.save(kanke);
            
            Thana ranchiThana = new Thana();
            ranchiThana.setName("Ranchi");
            ranchiThana.setDistrict(ranchi);
            thanaRepository.save(ranchiThana);
            
            // Villages
            Village mangura = new Village();
            mangura.setName("Mangura");
            mangura.setThana(ichak);
            mangura.setActive(true);
            villageRepository.save(mangura);
            
            Village ichakVillage = new Village();
            ichakVillage.setName("Ichak");
            ichakVillage.setThana(ichak);
            ichakVillage.setActive(true);
            villageRepository.save(ichakVillage);
            
            Village katkamsandi = new Village();
            katkamsandi.setName("Katkamsandi");
            katkamsandi.setThana(ichak);
            katkamsandi.setActive(true);
            villageRepository.save(katkamsandi);
        }
        
        // Initialize sample donations
        if (donationRepository.count() == 0) {
            Country defaultCountry = countryRepository.findAll().stream()
                .filter(c -> "India".equals(c.getName()))
                .findFirst()
                .orElse(null);
            State defaultState = stateRepository.findAll().stream()
                .filter(s -> "Jharkhand".equals(s.getName()))
                .findFirst()
                .orElse(null);
            District defaultDistrict = districtRepository.findAll().stream()
                .filter(d -> "Hazaribag".equals(d.getName()))
                .findFirst()
                .orElse(null);
            Thana defaultThana = thanaRepository.findAll().stream()
                .filter(t -> "Ichak".equals(t.getName()))
                .findFirst()
                .orElse(null);
            Village defaultVillage = villageRepository.findAll().stream()
                .filter(v -> "Mangura".equals(v.getName()))
                .findFirst()
                .orElse(null);
            
            if (defaultCountry != null && defaultState != null && defaultDistrict != null 
                && defaultThana != null && defaultVillage != null) {
                
                Donation d1 = new Donation();
                d1.setName("Ram Kumar");
                d1.setEmail("ram@example.com");
                d1.setPhone("+91-9876543210");
                d1.setAmount(new BigDecimal("5000.00"));
                d1.setCountryId(defaultCountry.getId());
                d1.setStateId(defaultState.getId());
                d1.setDistrictId(defaultDistrict.getId());
                d1.setThanaId(defaultThana.getId());
                d1.setVillageId(defaultVillage.getId());
                d1.setCity("Hazaribag");
                d1.setShowPublic(true);
                donationRepository.save(d1);
                
                Donation d2 = new Donation();
                d2.setName("Priya Sharma");
                d2.setEmail("priya@example.com");
                d2.setPhone("+91-9876543211");
                d2.setAmount(new BigDecimal("10000.00"));
                d2.setCountryId(defaultCountry.getId());
                d2.setStateId(defaultState.getId());
                d2.setDistrictId(defaultDistrict.getId());
                d2.setThanaId(defaultThana.getId());
                d2.setVillageId(defaultVillage.getId());
                d2.setCity("Hazaribag");
                d2.setShowPublic(true);
                donationRepository.save(d2);
                
                Donation d3 = new Donation();
                d3.setName("Anonymous Devotee");
                d3.setPhone("+91-9876543212");
                d3.setAmount(new BigDecimal("2500.00"));
                d3.setCountryId(defaultCountry.getId());
                d3.setStateId(defaultState.getId());
                d3.setDistrictId(defaultDistrict.getId());
                d3.setThanaId(defaultThana.getId());
                d3.setVillageId(defaultVillage.getId());
                d3.setCity("Hazaribag");
                d3.setShowPublic(false);
                donationRepository.save(d3);
                
                Donation d4 = new Donation();
                d4.setName("Sita Devi");
                d4.setEmail("sita@example.com");
                d4.setPhone("+91-9876543213");
                d4.setAmount(new BigDecimal("15000.00"));
                d4.setCountryId(defaultCountry.getId());
                d4.setStateId(defaultState.getId());
                d4.setDistrictId(defaultDistrict.getId());
                d4.setThanaId(defaultThana.getId());
                d4.setVillageId(defaultVillage.getId());
                d4.setCity("Hazaribag");
                d4.setShowPublic(true);
                donationRepository.save(d4);
                
                Donation d5 = new Donation();
                d5.setName("Krishna Das");
                d5.setEmail("krishna@example.com");
                d5.setPhone("+91-9876543214");
                d5.setAmount(new BigDecimal("7500.00"));
                d5.setCountryId(defaultCountry.getId());
                d5.setStateId(defaultState.getId());
                d5.setDistrictId(defaultDistrict.getId());
                d5.setThanaId(defaultThana.getId());
                d5.setVillageId(defaultVillage.getId());
                d5.setCity("Hazaribag");
                d5.setShowPublic(true);
                donationRepository.save(d5);
            }
        }
        
        // Initialize sample updates
        if (updateRepository.count() == 0) {
            Update u1 = new Update();
            u1.setTitle("Foundation Stone Laid");
            u1.setMessage("The foundation stone of Durga Mandir was laid with great devotion and ceremony. We are grateful for all the support.");
            u1.setCreatedAt(LocalDateTime.now());
            updateRepository.save(u1);
            
            Update u2 = new Update();
            u2.setTitle("Construction Progress");
            u2.setMessage("The construction is progressing well. The main structure is taking shape beautifully.");
            u2.setCreatedAt(LocalDateTime.now());
            updateRepository.save(u2);
            
            Update u3 = new Update();
            u3.setTitle("First Puja Performed");
            u3.setMessage("The first puja was performed at the construction site. Maa Durga's blessings are with us.");
            u3.setCreatedAt(LocalDateTime.now());
            updateRepository.save(u3);
        }
    }
}
