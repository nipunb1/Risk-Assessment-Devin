package com.riskassessment.kafka;

import com.riskassessment.dto.RiskDTO;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RiskEventProducer {
    
    private static final Logger logger = LoggerFactory.getLogger(RiskEventProducer.class);
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.risk-alerts:risk-alert-events}")
    private String riskAlertTopic;
    
    public void publishRiskCreated(RiskDTO riskDTO) {
        try {
            String key = "risk_created_" + riskDTO.getRiskId();
            
            kafkaTemplate.send(riskAlertTopic, key, riskDTO);
            
            logger.debug("Published risk created event for risk ID: {} to topic: {}", 
                        riskDTO.getRiskId(), riskAlertTopic);
            
        } catch (Exception e) {
            logger.error("Failed to publish risk created event: {}", e.getMessage(), e);
        }
    }
    
    public void publishRiskUpdated(RiskDTO riskDTO) {
        try {
            String key = "risk_updated_" + riskDTO.getRiskId();
            
            kafkaTemplate.send(riskAlertTopic, key, riskDTO);
            
            logger.debug("Published risk updated event for risk ID: {} to topic: {}", 
                        riskDTO.getRiskId(), riskAlertTopic);
            
        } catch (Exception e) {
            logger.error("Failed to publish risk updated event: {}", e.getMessage(), e);
        }
    }
    
    public void publishRiskIntelligence(RiskIntelligenceEvent riskEvent) {
        try {
            String key = "risk_intelligence_" + riskEvent.getEventId();
            
            kafkaTemplate.send(riskAlertTopic, key, riskEvent);
            
            logger.debug("Published risk intelligence event: {} to topic: {}", 
                        riskEvent.getEventId(), riskAlertTopic);
            
        } catch (Exception e) {
            logger.error("Failed to publish risk intelligence event: {}", e.getMessage(), e);
        }
    }
}
