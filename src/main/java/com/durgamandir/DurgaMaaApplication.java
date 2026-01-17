package com.durgamandir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * DurgaMaa Monolithic Application
 * 
 * Consolidated microservices (donation + booking) into single app
 * Optimized for t3.micro EC2 instance
 * 
 * @author DurgaMaa Dev Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class DurgaMaaApplication {

    public static void main(String[] args) {
        // Set memory-efficient JVM options for t3.micro
        System.setProperty("java.awt.headless", "true");
        
        SpringApplication app = new SpringApplication(DurgaMaaApplication.class);
        
        // Reduce startup time
        app.setLogStartupInfo(true);
        app.setRegisterShutdownHook(true);
        
        app.run(args);
    }

    /**
     * CORS configuration for frontend integration
     * Allows requests from React frontend and Nginx reverse proxy
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://127.0.0.1:3000",
                        "http://127.0.0.1:3001"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .exposedHeaders("Set-Cookie", "Authorization")
                    .maxAge(3600);
            }
        };
    }
}

