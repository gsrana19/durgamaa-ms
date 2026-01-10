package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.SankalpamRequest;
import com.durgamandir.donation.dto.SankalpamResponse;
import com.durgamandir.donation.entity.Sankalpam;
import com.durgamandir.donation.repository.SankalpamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SankalpamService {
    
    private final SankalpamRepository sankalpamRepository;
    
    public SankalpamService(SankalpamRepository sankalpamRepository) {
        this.sankalpamRepository = sankalpamRepository;
    }
    
    public SankalpamResponse createSankalpam(SankalpamRequest request) {
        Sankalpam sankalpam = new Sankalpam();
        sankalpam.setFullName(request.getFullName());
        sankalpam.setGotra(request.getGotra());
        sankalpam.setCity(request.getCity());
        sankalpam.setPrayer(request.getPrayer());
        
        Sankalpam saved = sankalpamRepository.save(sankalpam);
        return mapToResponse(saved);
    }
    
    private SankalpamResponse mapToResponse(Sankalpam sankalpam) {
        return new SankalpamResponse(
                sankalpam.getId(),
                sankalpam.getFullName(),
                sankalpam.getGotra(),
                sankalpam.getCity(),
                sankalpam.getPrayer(),
                sankalpam.getCreatedAt()
        );
    }
}




