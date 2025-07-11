package com.riskassessment.kafka;

import com.riskassessment.dto.bigdata.MarketDataEvent;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import com.riskassessment.service.MarketRiskAnalyzer;
import com.riskassessment.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarketDataConsumerTest {
    
    @Mock
    private MarketRiskAnalyzer marketRiskAnalyzer;
    
    @Mock
    private RiskService riskService;
    
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @InjectMocks
    private MarketDataConsumer marketDataConsumer;
    
    private MarketDataEvent testMarketData;
    private RiskIntelligenceEvent testRiskEvent;
    
    @BeforeEach
    public void setUp() {
        testMarketData = new MarketDataEvent(
            "AAPL",
            BigDecimal.valueOf(150.00),
            BigDecimal.valueOf(145.00),
            BigDecimal.valueOf(0.08),
            BigDecimal.valueOf(1000000),
            "EQUITY",
            LocalDateTime.now(),
            "TEST_SOURCE",
            "PRICE_UPDATE"
        );
        
        testRiskEvent = new RiskIntelligenceEvent();
        testRiskEvent.setEventId("test-event-123");
        testRiskEvent.setRiskType("MARKET_PRACTICE");
        testRiskEvent.setConfidenceScore(0.85);
        testRiskEvent.setAlertLevel("HIGH");
    }
    
    @Test
    public void testProcessMarketDataWithHighConfidenceRisk() {
        when(marketRiskAnalyzer.analyzeMarketData(testMarketData)).thenReturn(testRiskEvent);
        
        marketDataConsumer.processMarketData(testMarketData);
        
        verify(marketRiskAnalyzer).analyzeMarketData(testMarketData);
        verify(riskService).createRiskFromIntelligence(testRiskEvent);
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }
    
    @Test
    public void testProcessMarketDataWithLowConfidenceRisk() {
        testRiskEvent.setConfidenceScore(0.6);
        when(marketRiskAnalyzer.analyzeMarketData(testMarketData)).thenReturn(testRiskEvent);
        
        marketDataConsumer.processMarketData(testMarketData);
        
        verify(marketRiskAnalyzer).analyzeMarketData(testMarketData);
        verify(riskService, never()).createRiskFromIntelligence(any());
        verify(kafkaTemplate).send(eq("risk-alert-events"), eq("test-event-123"), eq(testRiskEvent));
    }
    
    @Test
    public void testProcessMarketDataNoRisk() {
        when(marketRiskAnalyzer.analyzeMarketData(testMarketData)).thenReturn(null);
        
        marketDataConsumer.processMarketData(testMarketData);
        
        verify(marketRiskAnalyzer).analyzeMarketData(testMarketData);
        verify(riskService, never()).createRiskFromIntelligence(any());
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }
    
    @Test
    public void testProcessRiskAlertsCriticalAndHighConfidence() {
        testRiskEvent.setAlertLevel("CRITICAL");
        testRiskEvent.setConfidenceScore(0.9);
        
        marketDataConsumer.processRiskAlerts(testRiskEvent);
        
        verify(riskService).createRiskFromIntelligence(testRiskEvent);
    }
    
    @Test
    public void testProcessRiskAlertsNotCritical() {
        testRiskEvent.setAlertLevel("MEDIUM");
        testRiskEvent.setConfidenceScore(0.9);
        
        marketDataConsumer.processRiskAlerts(testRiskEvent);
        
        verify(riskService, never()).createRiskFromIntelligence(any());
    }
}
