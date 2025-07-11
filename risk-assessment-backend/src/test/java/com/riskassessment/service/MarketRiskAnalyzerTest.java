package com.riskassessment.service;

import com.riskassessment.dto.bigdata.MarketDataEvent;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import com.riskassessment.entity.Risk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MarketRiskAnalyzerTest {
    
    @InjectMocks
    private MarketRiskAnalyzer marketRiskAnalyzer;
    
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(marketRiskAnalyzer, "volatilityThreshold", BigDecimal.valueOf(0.05));
        ReflectionTestUtils.setField(marketRiskAnalyzer, "priceChangeThreshold", BigDecimal.valueOf(0.10));
    }
    
    @Test
    public void testAnalyzeMarketDataWithHighVolatility() {
        MarketDataEvent marketData = new MarketDataEvent(
            "AAPL",
            BigDecimal.valueOf(150.00),
            BigDecimal.valueOf(135.00),
            BigDecimal.valueOf(0.09),
            BigDecimal.valueOf(1500000),
            "EQUITY",
            LocalDateTime.now(),
            "TEST_SOURCE",
            "PRICE_UPDATE"
        );
        
        RiskIntelligenceEvent result = marketRiskAnalyzer.analyzeMarketData(marketData);
        
        assertNotNull(result);
        assertEquals("PRICING", result.getRiskType());
        assertTrue(result.getConfidenceScore() > 0.5);
        assertNotNull(result.getEventId());
    }
    
    @Test
    public void testAnalyzeMarketDataWithSignificantPriceChange() {
        MarketDataEvent marketData = new MarketDataEvent(
            "TSLA",
            BigDecimal.valueOf(225.00),
            BigDecimal.valueOf(200.00),
            BigDecimal.valueOf(0.03),
            BigDecimal.valueOf(1200000),
            "EQUITY",
            LocalDateTime.now(),
            "TEST_SOURCE",
            "PRICE_UPDATE"
        );
        
        RiskIntelligenceEvent result = marketRiskAnalyzer.analyzeMarketData(marketData);
        
        assertNotNull(result);
        assertEquals("PRICING", result.getRiskType());
        assertTrue(result.getConfidenceScore() > 0.5);
        assertEquals("MEDIUM", result.getRiskProbability());
    }
    
    @Test
    public void testAnalyzeMarketDataNoRisk() {
        MarketDataEvent marketData = new MarketDataEvent(
            "MSFT",
            BigDecimal.valueOf(305.00),
            BigDecimal.valueOf(304.00),
            BigDecimal.valueOf(0.02),
            BigDecimal.valueOf(100000),
            "EQUITY",
            LocalDateTime.now(),
            "TEST_SOURCE",
            "PRICE_UPDATE"
        );
        
        RiskIntelligenceEvent result = marketRiskAnalyzer.analyzeMarketData(marketData);
        
        assertNotNull(result);
        assertEquals("PRICING", result.getRiskType());
    }
    
    @Test
    public void testAnalyzeMarketDataCriticalAlert() {
        MarketDataEvent marketData = new MarketDataEvent(
            "GME",
            BigDecimal.valueOf(300.00),
            BigDecimal.valueOf(200.00),
            BigDecimal.valueOf(0.25),
            BigDecimal.valueOf(10000000),
            "EQUITY",
            LocalDateTime.now(),
            "TEST_SOURCE",
            "PRICE_UPDATE"
        );
        
        RiskIntelligenceEvent result = marketRiskAnalyzer.analyzeMarketData(marketData);
        
        assertNotNull(result);
        assertEquals("CRITICAL", result.getAlertLevel());
        assertEquals(Risk.RiskProbability.HIGH, result.getRiskProbabilityEnum());
        assertTrue(result.isHighConfidence());
        assertTrue(result.isCriticalAlert());
    }
}
