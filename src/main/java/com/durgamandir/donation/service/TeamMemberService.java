package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.TeamMemberRequest;
import com.durgamandir.donation.dto.TeamMemberResponse;
import com.durgamandir.donation.entity.TeamMember;
import com.durgamandir.donation.repository.TeamMemberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamMemberService {
    
    private final TeamMemberRepository teamMemberRepository;
    
    @Value("${app.team.upload-dir:./uploads/team}")
    private String uploadDir;
    
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }
    
    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to create team upload directory: " + e.getMessage());
        }
    }
    
    public TeamMemberResponse createTeamMember(TeamMemberRequest request, MultipartFile imageFile) throws IOException {
        // Validate file
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }
        
        // Validate image type
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
        
        // Generate unique filename
        String originalFilename = imageFile.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        
        // Save file
        Path filePath = Paths.get(uploadDir, uniqueFilename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Create team member entity
        TeamMember teamMember = new TeamMember();
        teamMember.setName(request.getName());
        teamMember.setPosition(request.getPosition());
        teamMember.setMobileNumber(request.getMobileNumber());
        teamMember.setImageUrl("/uploads/team/" + uniqueFilename);
        teamMember.setOriginalFileName(originalFilename);
        teamMember.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        
        TeamMember saved = teamMemberRepository.save(teamMember);
        return mapToResponse(saved);
    }
    
    public List<TeamMemberResponse> getAllTeamMembers() {
        List<TeamMember> allMembers = teamMemberRepository.findAllByOrderByDisplayOrderAsc();
        
        // Sort: priests first (position contains "priest" case-insensitive), then others by displayOrder
        allMembers.sort((a, b) -> {
            boolean aIsPriest = a.getPosition() != null && a.getPosition().toLowerCase().contains("priest");
            boolean bIsPriest = b.getPosition() != null && b.getPosition().toLowerCase().contains("priest");
            
            // If one is priest and other is not, priest comes first
            if (aIsPriest && !bIsPriest) return -1;
            if (!aIsPriest && bIsPriest) return 1;
            
            // If both are priests or both are not, sort by displayOrder
            int orderA = a.getDisplayOrder() != null ? a.getDisplayOrder() : 0;
            int orderB = b.getDisplayOrder() != null ? b.getDisplayOrder() : 0;
            return orderA - orderB;
        });
        
        return allMembers.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public TeamMemberResponse getTeamMemberById(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + id));
        return mapToResponse(teamMember);
    }
    
    public TeamMemberResponse updateTeamMember(Long id, TeamMemberRequest request, MultipartFile imageFile) throws IOException {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + id));
        
        // Update basic fields
        teamMember.setName(request.getName());
        teamMember.setPosition(request.getPosition());
        teamMember.setMobileNumber(request.getMobileNumber());
        if (request.getDisplayOrder() != null) {
            teamMember.setDisplayOrder(request.getDisplayOrder());
        }
        
        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Validate image type
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }
            
            // Delete old file
            if (teamMember.getImageUrl() != null) {
                try {
                    Path oldFilePath = Paths.get(uploadDir, teamMember.getImageUrl().replace("/uploads/team/", ""));
                    Files.deleteIfExists(oldFilePath);
                } catch (IOException e) {
                    System.err.println("Failed to delete old file: " + e.getMessage());
                }
            }
            
            // Save new file
            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            
            Path filePath = Paths.get(uploadDir, uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            teamMember.setImageUrl("/uploads/team/" + uniqueFilename);
            teamMember.setOriginalFileName(originalFilename);
        }
        
        TeamMember updated = teamMemberRepository.save(teamMember);
        return mapToResponse(updated);
    }
    
    public void deleteTeamMember(Long id) throws IOException {
        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + id));
        
        // Delete file
        if (teamMember.getImageUrl() != null) {
            try {
                Path filePath = Paths.get(uploadDir, teamMember.getImageUrl().replace("/uploads/team/", ""));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
            }
        }
        
        teamMemberRepository.delete(teamMember);
    }
    
    private TeamMemberResponse mapToResponse(TeamMember teamMember) {
        return new TeamMemberResponse(
                teamMember.getId(),
                teamMember.getName(),
                teamMember.getPosition(),
                teamMember.getMobileNumber(),
                teamMember.getImageUrl(),
                teamMember.getDisplayOrder(),
                teamMember.getCreatedAt(),
                teamMember.getUpdatedAt()
        );
    }
}

