package com.riskassessment.kafka;

import com.riskassessment.dto.bigdata.MarketDataEvent;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import com.riskassessment.service.MarketRiskAnalyzer;
import com.riskassessment.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MarketDataConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(MarketDataConsumer.class);
    
    @Autowired
    private MarketRiskAnalyzer marketRiskAnalyzer;
    
    @Autowired
    private RiskService riskService;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @KafkaListener(topics = "market-data-events", groupId = "risk-assessment-group")
    public void processMarketData(MarketDataEvent marketDataEvent) {
        logger.info("Processing market data event for symbol: {}", marketDataEvent.getSymbol());
        
        try {
            RiskIntelligenceEvent riskEvent = marketRiskAnalyzer.analyzeMarketData(marketDataEvent);
            
            if (riskEvent != null) {
                logger.info("Risk detected from market data: {}", riskEvent.getEventId());
                
                if (riskEvent.isHighConfidence()) {
                    riskService.createRiskFromIntelligence(riskEvent);
                    logger.info("Created risk from high-confidence market intelligence");
                } else {
                    kafkaTemplate.send("risk-alert-events", riskEvent.getEventId(), riskEvent);
                    logger.info("Published low-confidence risk intelligence event to Kafka");
                }
            } else {
                logger.debug("No risk detected from market data for symbol: {}", marketDataEvent.getSymbol());
            }
            
        } catch (Exception e) {
            logger.error("Failed to process market data event: {}", e.getMessage(), e);
        }
    }
    
    @KafkaListener(topics = "risk-alert-events", groupId = "risk-assessment-group")
    public void processRiskAlerts(RiskIntelligenceEvent riskEvent) {
        logger.info("Processing risk intelligence event: {}", riskEvent.getEventId());
        
        try {
            if (riskEvent.isCriticalAlert() && riskEvent.isHighConfidence()) {
                riskService.createRiskFromIntelligence(riskEvent);
                logger.info("Created risk from critical alert: {}", riskEvent.getEventId());
            } else {
                logger.info("Risk event {} does not meet criteria for automatic risk creation", 
                           riskEvent.getEventId());
            }
            
        } catch (Exception e) {
            logger.error("Failed to process risk alert: {}", e.getMessage(), e);
        }
    }
}
