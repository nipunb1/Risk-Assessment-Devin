package com.riskassessment.service;

/**
 * Risk Service - Business logic layer for risk management operations.
 * 
 * This service class provides the core business logic for managing risk assessments,
 * including CRUD operations, data validation, and business rule enforcement.
 * It acts as an intermediary between the controller layer and the data access layer,
 * ensuring proper data transformation and business logic application.
 * 
 * Key responsibilities:
 * - Risk data validation and transformation
 * - Business rule enforcement
 * - DTO to Entity mapping and vice versa
 * - Transaction management coordination
 * 
 * @author Risk Assessment System
 * @version 1.0
 * @since 1.0
 */

import com.riskassessment.dto.RiskDTO;
import com.riskassessment.entity.Risk;
import com.riskassessment.repository.RiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RiskService {
    
    @Autowired
    private RiskRepository riskRepository;
    
    public List<RiskDTO> getAllRisks() {
        return riskRepository.findAllOrderByRiskDateDesc()
                .stream()
                .map(RiskDTO::new)
                .collect(Collectors.toList());
    }
    
    public Optional<RiskDTO> getRiskById(Long id) {
        return riskRepository.findById(id)
                .map(RiskDTO::new);
    }
    
    public RiskDTO createRisk(RiskDTO riskDTO) {
        Risk risk = riskDTO.toEntity();
        Risk savedRisk = riskRepository.save(risk);
        return new RiskDTO(savedRisk);
    }
    
    public Optional<RiskDTO> updateRisk(Long id, RiskDTO riskDTO) {
        return riskRepository.findById(id)
                .map(existingRisk -> {
                    existingRisk.setRiskDate(riskDTO.getRiskDate());
                    existingRisk.setRiskType(riskDTO.getRiskType());
                    existingRisk.setRiskProbability(riskDTO.getRiskProbability());
                    existingRisk.setRiskStatus(riskDTO.getRiskStatus());
                    existingRisk.setRiskRemarks(riskDTO.getRiskRemarks());
                    existingRisk.setRiskImpact(riskDTO.getRiskImpact());
                    Risk updatedRisk = riskRepository.save(existingRisk);
                    return new RiskDTO(updatedRisk);
                });
    }
    
    public boolean deleteRisk(Long id) {
        if (riskRepository.existsById(id)) {
            riskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<RiskDTO> getRisksByStatus(Risk.RiskStatus status) {
        return riskRepository.findByRiskStatus(status)
                .stream()
                .map(RiskDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<RiskDTO> getRisksByType(Risk.RiskType type) {
        return riskRepository.findByRiskType(type)
                .stream()
                .map(RiskDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<RiskDTO> getRisksByProbability(Risk.RiskProbability probability) {
        return riskRepository.findByRiskProbability(probability)
                .stream()
                .map(RiskDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<RiskDTO> getRisksByImpact(Risk.RiskImpact impact) {
        return riskRepository.findByRiskImpact(impact)
                .stream()
                .map(RiskDTO::new)
                .collect(Collectors.toList());
    }
}
