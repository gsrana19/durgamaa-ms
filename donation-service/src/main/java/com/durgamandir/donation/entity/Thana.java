package com.durgamandir.donation.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "thanas")
public class Thana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
    
    @OneToMany(mappedBy = "thana", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Village> villages;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public District getDistrict() {
        return district;
    }
    
    public void setDistrict(District district) {
        this.district = district;
    }
    
    public List<Village> getVillages() {
        return villages;
    }
    
    public void setVillages(List<Village> villages) {
        this.villages = villages;
    }
}

