package com.riskassessment.kafka;

import com.riskassessment.dto.bigdata.MarketDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MarketDataProducer {
    
    private static final Logger logger = LoggerFactory.getLogger(MarketDataProducer.class);
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.market-data:market-data-events}")
    private String marketDataTopic;
    
    public void publishMarketData(MarketDataEvent marketDataEvent) {
        try {
            String key = marketDataEvent.getSymbol() + "_" + marketDataEvent.getTimestamp();
            
            kafkaTemplate.send(marketDataTopic, key, marketDataEvent);
            
            logger.debug("Published market data event for symbol: {} to topic: {}", 
                        marketDataEvent.getSymbol(), marketDataTopic);
            
        } catch (Exception e) {
            logger.error("Failed to publish market data event: {}", e.getMessage(), e);
        }
    }
    
    public void publishMarketDataBatch(MarketDataEvent[] marketDataEvents) {
        for (MarketDataEvent event : marketDataEvents) {
            publishMarketData(event);
        }
        
        logger.info("Published batch of {} market data events", marketDataEvents.length);
    }
}
