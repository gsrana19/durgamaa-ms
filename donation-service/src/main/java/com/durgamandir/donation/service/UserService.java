package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.AdminSignupRequest;
import com.durgamandir.donation.entity.User;
import com.durgamandir.donation.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User createAdmin(AdminSignupRequest request) {
        // Only allow admin signup if no admin exists
        if (userRepository.countByRole("ROLE_ADMIN") > 0) {
            throw new RuntimeException("Admin already exists. Please use login.");
        }
        
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("User ID already exists");
        }
        
        User user = new User();
        user.setUserId(request.getUserId());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_ADMIN");
        
        return userRepository.save(user);
    }
    
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public void updateLastLoginTime(String userId) {
        User user = findByUserId(userId);
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
    }
    
    public String getLastLoginTime(String userId) {
        User user = findByUserId(userId);
        if (user.getLastLoginTime() == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return user.getLastLoginTime().format(formatter);
    }
}






