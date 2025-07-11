package com.riskassessment.service;

import com.riskassessment.document.RiskDocument;
import com.riskassessment.dto.RiskDTO;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import com.riskassessment.entity.Risk;
import com.riskassessment.repository.RiskRepository;
import com.riskassessment.repository.elasticsearch.RiskDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RiskService {
    
    private static final Logger logger = LoggerFactory.getLogger(RiskService.class);
    
    @Autowired
    private RiskRepository riskRepository;
    
    @Autowired
    private RiskDocumentRepository riskDocumentRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
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
        RiskDTO savedRiskDTO = new RiskDTO(savedRisk);
        
        publishRiskEvent(savedRiskDTO);
        indexRiskForSearch(savedRiskDTO, "MANUAL");
        
        return savedRiskDTO;
    }
    
    public Optional<RiskDTO> updateRisk(Long id, RiskDTO riskDTO) {
        return riskRepository.findById(id)
                .map(existingRisk -> {
                    existingRisk.setRiskDate(riskDTO.getRiskDate());
                    existingRisk.setRiskType(riskDTO.getRiskType());
                    existingRisk.setRiskProbability(riskDTO.getRiskProbability());
                    existingRisk.setRiskDesc(riskDTO.getRiskDesc());
                    existingRisk.setRiskStatus(riskDTO.getRiskStatus());
                    existingRisk.setRiskRemarks(riskDTO.getRiskRemarks());
                    Risk updatedRisk = riskRepository.save(existingRisk);
                    RiskDTO updatedRiskDTO = new RiskDTO(updatedRisk);
                    
                    publishRiskEvent(updatedRiskDTO);
                    indexRiskForSearch(updatedRiskDTO, "MANUAL");
                    
                    return updatedRiskDTO;
                });
    }
    
    public boolean deleteRisk(Long id) {
        if (riskRepository.existsById(id)) {
            riskRepository.deleteById(id);
            
            try {
                riskDocumentRepository.deleteById("risk_" + id);
            } catch (Exception e) {
                logger.warn("Failed to delete risk from Elasticsearch index: {}", e.getMessage());
            }
            
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
    
    public RiskDTO createRiskFromIntelligence(RiskIntelligenceEvent event) {
        logger.info("Creating risk from intelligence event: {}", event.getEventId());
        
        RiskDTO riskDTO = new RiskDTO();
        riskDTO.setRiskDate(LocalDate.now());
        riskDTO.setRiskType(event.getRiskTypeEnum());
        riskDTO.setRiskProbability(event.getRiskProbabilityEnum());
        riskDTO.setRiskDesc(event.getRiskDescription());
        riskDTO.setRiskStatus(Risk.RiskStatus.OPEN);
        
        String remarks = String.format("Auto-generated from %s | Confidence: %.2f | Alert Level: %s", 
                                     event.getSource(), 
                                     event.getConfidenceScore(), 
                                     event.getAlertLevel());
        riskDTO.setRiskRemarks(remarks);
        
        Risk risk = riskDTO.toEntity();
        Risk savedRisk = riskRepository.save(risk);
        RiskDTO savedRiskDTO = new RiskDTO(savedRisk);
        
        publishRiskEvent(savedRiskDTO);
        indexRiskForSearch(savedRiskDTO, event.getSource());
        
        logger.info("Successfully created risk {} from intelligence event", savedRisk.getRiskId());
        return savedRiskDTO;
    }
    
    public List<RiskDocument> searchRisks(String keyword) {
        try {
            return riskDocumentRepository.findByRiskDescContaining(keyword);
        } catch (Exception e) {
            logger.warn("Failed to search risks in Elasticsearch: {}", e.getMessage());
            return List.of();
        }
    }
    
    public List<RiskDocument> getRisksBySource(String source) {
        try {
            return riskDocumentRepository.findBySource(source);
        } catch (Exception e) {
            logger.warn("Failed to get risks by source from Elasticsearch: {}", e.getMessage());
            return List.of();
        }
    }
    
    private void publishRiskEvent(RiskDTO riskDTO) {
        try {
            kafkaTemplate.send("risk-alert-events", "risk_" + riskDTO.getRiskId(), riskDTO);
            logger.debug("Published risk event to Kafka for risk ID: {}", riskDTO.getRiskId());
        } catch (Exception e) {
            logger.warn("Failed to publish risk event to Kafka: {}", e.getMessage());
        }
    }
    
    private void indexRiskForSearch(RiskDTO riskDTO, String source) {
        try {
            RiskDocument riskDocument = new RiskDocument(riskDTO, source);
            riskDocumentRepository.save(riskDocument);
            logger.debug("Indexed risk in Elasticsearch for risk ID: {}", riskDTO.getRiskId());
        } catch (Exception e) {
            logger.warn("Failed to index risk in Elasticsearch: {}", e.getMessage());
        }
    }
}
