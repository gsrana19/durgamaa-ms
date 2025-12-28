package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.UpdateRequest;
import com.durgamandir.donation.dto.UpdateResponse;
import com.durgamandir.donation.entity.Update;
import com.durgamandir.donation.repository.UpdateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UpdateService {
    
    private final UpdateRepository updateRepository;
    
    public UpdateService(UpdateRepository updateRepository) {
        this.updateRepository = updateRepository;
    }
    
    public List<UpdateResponse> getAllUpdates() {
        return updateRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public UpdateResponse createUpdate(UpdateRequest request) {
        Update update = new Update();
        update.setTitle(request.getTitle());
        update.setMessage(request.getMessage());
        update.setImageUrl(request.getImageUrl());
        
        Update saved = updateRepository.save(update);
        return mapToResponse(saved);
    }
    
    public UpdateResponse updateUpdate(Long id, UpdateRequest request) {
        Update update = updateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Update not found"));
        
        update.setTitle(request.getTitle());
        update.setMessage(request.getMessage());
        if (request.getImageUrl() != null) {
            update.setImageUrl(request.getImageUrl());
        }
        
        Update updated = updateRepository.save(update);
        return mapToResponse(updated);
    }
    
    public void deleteUpdate(Long id) {
        updateRepository.deleteById(id);
    }
    
    private UpdateResponse mapToResponse(Update update) {
        return new UpdateResponse(
                update.getId(),
                update.getTitle(),
                update.getMessage(),
                update.getImageUrl(),
                update.getCreatedAt()
        );
    }
}


