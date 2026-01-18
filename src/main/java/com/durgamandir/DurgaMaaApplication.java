package com.durgamandir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * DurgaMaa Monolithic Application
 * 
 * Consolidated microservices (donation + booking) into single app
 * Optimized for t3.micro EC2 instance
 * 
 * Note: CORS configuration is handled in SecurityConfig.java
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
}

