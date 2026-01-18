package com.durgamandir.donation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Service for sending SMS and WhatsApp messages to donors
 * Supports Twilio for SMS and WhatsApp Business API
 */
@Service
public class MessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    @Value("${app.messaging.enabled:true}")
    private boolean messagingEnabled;
    
    @Value("${app.messaging.twilio.account-sid:}")
    private String twilioAccountSid;
    
    @Value("${app.messaging.twilio.auth-token:}")
    private String twilioAuthToken;
    
    @Value("${app.messaging.twilio.from-number:}")
    private String twilioFromNumber;
    
    @Value("${app.messaging.twilio.whatsapp-from:}")
    private String twilioWhatsAppFrom;
    
    @Value("${app.messaging.temple-name:Durga Maa Temple}")
    private String templeName;
    
    private final HttpClient httpClient;
    
    public MessageService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    
    /**
     * Send notification to donor when donation is verified or rejected
     * Tries WhatsApp first, falls back to SMS
     */
    public void sendDonationNotification(String mobile, String donorName, String amount, 
                                        String status, String adminNote) {
        if (!messagingEnabled || mobile == null || mobile.trim().isEmpty()) {
            logger.warn("Messaging disabled or no mobile number provided");
            return;
        }
        
        // Clean mobile number (remove spaces, dashes, etc.)
        String cleanMobile = mobile.replaceAll("[^0-9+]", "");
        
        // Format message
        String message = formatDonationMessage(donorName, amount, status, adminNote);
        
        // Try WhatsApp first (if number starts with country code)
        if (isWhatsAppNumber(cleanMobile)) {
            if (sendWhatsAppMessage(cleanMobile, message)) {
                logger.info("WhatsApp message sent to {}", cleanMobile);
                return;
            }
        }
        
        // Fallback to SMS
        if (sendSMS(cleanMobile, message)) {
            logger.info("SMS sent to {}", cleanMobile);
        } else {
            logger.error("Failed to send message to {}", cleanMobile);
        }
    }
    
    /**
     * Check if number is likely a WhatsApp number (has country code)
     */
    private boolean isWhatsAppNumber(String mobile) {
        // Indian numbers: +91 or 91 prefix, or 10 digits starting with 6-9
        // International: starts with +
        return mobile.startsWith("+") || 
               (mobile.startsWith("91") && mobile.length() >= 12) ||
               (mobile.length() == 10 && mobile.matches("^[6-9]\\d{9}$"));
    }
    
    /**
     * Send WhatsApp message using Twilio
     */
    private boolean sendWhatsAppMessage(String to, String message) {
        if (twilioAccountSid == null || twilioAccountSid.isEmpty() ||
            twilioAuthToken == null || twilioAuthToken.isEmpty()) {
            logger.warn("Twilio credentials not configured");
            return false;
        }
        
        try {
            // Format WhatsApp number (whatsapp:+91XXXXXXXXXX)
            String whatsappTo = formatWhatsAppNumber(to);
            String from = twilioWhatsAppFrom != null && !twilioWhatsAppFrom.isEmpty() 
                    ? twilioWhatsAppFrom 
                    : "whatsapp:" + twilioFromNumber;
            
            String url = String.format(
                "https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json",
                twilioAccountSid
            );
            
            String body = String.format(
                "From=%s&To=%s&Body=%s",
                java.net.URLEncoder.encode(from, "UTF-8"),
                java.net.URLEncoder.encode(whatsappTo, "UTF-8"),
                java.net.URLEncoder.encode(message, "UTF-8")
            );
            
            String auth = twilioAccountSid + ":" + twilioAuthToken;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .timeout(Duration.ofSeconds(30))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                logger.info("WhatsApp message sent successfully to {}", to);
                return true;
            } else {
                logger.error("Failed to send WhatsApp message. Status: {}, Response: {}", 
                           response.statusCode(), response.body());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error sending WhatsApp message to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Send SMS using Twilio
     */
    private boolean sendSMS(String to, String message) {
        if (twilioAccountSid == null || twilioAccountSid.isEmpty() ||
            twilioAuthToken == null || twilioAuthToken.isEmpty()) {
            logger.warn("Twilio credentials not configured");
            return false;
        }
        
        try {
            // Format phone number (ensure country code for India)
            String formattedTo = formatPhoneNumber(to);
            
            String url = String.format(
                "https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json",
                twilioAccountSid
            );
            
            String body = String.format(
                "From=%s&To=%s&Body=%s",
                java.net.URLEncoder.encode(twilioFromNumber, "UTF-8"),
                java.net.URLEncoder.encode(formattedTo, "UTF-8"),
                java.net.URLEncoder.encode(message, "UTF-8")
            );
            
            String auth = twilioAccountSid + ":" + twilioAuthToken;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .timeout(Duration.ofSeconds(30))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                logger.info("SMS sent successfully to {}", to);
                return true;
            } else {
                logger.error("Failed to send SMS. Status: {}, Response: {}", 
                           response.statusCode(), response.body());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error sending SMS to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Format donation notification message
     */
    private String formatDonationMessage(String donorName, String amount, String status, String adminNote) {
        StringBuilder message = new StringBuilder();
        message.append("Namaste from ").append(templeName).append("!\n\n");
        
        if (status.equalsIgnoreCase("VERIFIED")) {
            message.append("✅ Your donation of ₹").append(amount).append(" has been verified.\n\n");
        } else {
            message.append("❌ Your donation of ₹").append(amount).append(" has been rejected.\n\n");
        }
        
        if (adminNote != null && !adminNote.trim().isEmpty()) {
            message.append("Note: ").append(adminNote).append("\n\n");
        }
        
        message.append("Thank you for your contribution!\n");
        message.append("Jai Maa Durga 🙏");
        
        return message.toString();
    }
    
    /**
     * Format phone number with country code
     */
    private String formatPhoneNumber(String mobile) {
        String clean = mobile.replaceAll("[^0-9+]", "");
        
        // If already has +, return as is
        if (clean.startsWith("+")) {
            return clean;
        }
        
        // If starts with 91 and length >= 12, add +
        if (clean.startsWith("91") && clean.length() >= 12) {
            return "+" + clean;
        }
        
        // If 10 digits (Indian number), add +91
        if (clean.length() == 10 && clean.matches("^[6-9]\\d{9}$")) {
            return "+91" + clean;
        }
        
        // Default: assume Indian number
        if (clean.length() == 10) {
            return "+91" + clean;
        }
        
        return clean;
    }
    
    /**
     * Format WhatsApp number
     */
    private String formatWhatsAppNumber(String mobile) {
        String formatted = formatPhoneNumber(mobile);
        if (!formatted.startsWith("whatsapp:")) {
            return "whatsapp:" + formatted;
        }
        return formatted;
    }
}


