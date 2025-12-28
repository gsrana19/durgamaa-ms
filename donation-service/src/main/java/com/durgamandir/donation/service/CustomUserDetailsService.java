package com.durgamandir.donation.service;

import com.durgamandir.donation.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserService userService;
    
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userService.findByUserId(userId);
        
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(
                    new SimpleGrantedAuthority(user.getRole())
                );
            }
            
            @Override
            public String getPassword() {
                return user.getPasswordHash();
            }
            
            @Override
            public String getUsername() {
                return user.getUserId();
            }
            
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}

