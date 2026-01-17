package com.durgamandir.donation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve uploaded files from uploads/ directory
        String uploadPath = Paths.get("uploads").toAbsolutePath().toString().replace("\\", "/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
        
        // Serve event media files
        String eventMediaPath = Paths.get("uploads/events").toAbsolutePath().toString().replace("\\", "/");
        registry.addResourceHandler("/uploads/events/**")
                .addResourceLocations("file:" + eventMediaPath + "/");
        
        // Serve team member images
        String teamPath = Paths.get("uploads/team").toAbsolutePath().toString().replace("\\", "/");
        registry.addResourceHandler("/uploads/team/**")
                .addResourceLocations("file:" + teamPath + "/");
    }
}

