package com.riskassessment.service;

import com.riskassessment.dto.bigdata.MarketDataEvent;
import com.riskassessment.dto.bigdata.RiskIntelligenceEvent;
import com.riskassessment.entity.Risk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MarketRiskAnalyzer {
    
    private static final Logger logger = LoggerFactory.getLogger(MarketRiskAnalyzer.class);
    
    @Value("${market.risk.volatility.threshold:0.05}")
    private BigDecimal volatilityThreshold;
    
    @Value("${market.risk.price.change.threshold:0.10}")
    private BigDecimal priceChangeThreshold;
    
    public RiskIntelligenceEvent analyzeMarketData(MarketDataEvent marketData) {
        logger.debug("Analyzing market data for symbol: {}", marketData.getSymbol());
        
        if (isHighRiskEvent(marketData)) {
            return createRiskIntelligenceEvent(marketData);
        }
        
        return null;
    }
    
    private boolean isHighRiskEvent(MarketDataEvent marketData) {
        boolean highVolatility = marketData.isHighVolatility(volatilityThreshold);
        boolean significantPriceChange = marketData.isSignificantPriceChange(priceChangeThreshold);
        
        logger.debug("Risk analysis for {}: volatility={}, priceChange={}", 
                    marketData.getSymbol(), highVolatility, significantPriceChange);
        
        return highVolatility || significantPriceChange;
    }
    
    private RiskIntelligenceEvent createRiskIntelligenceEvent(MarketDataEvent marketData) {
        String eventId = UUID.randomUUID().toString();
        
        Risk.RiskType riskType = determineRiskType(marketData);
        Risk.RiskProbability probability = calculateRiskProbability(marketData);
        String description = generateRiskDescription(marketData);
        String alertLevel = determineAlertLevel(marketData);
        Double confidenceScore = calculateConfidenceScore(marketData);
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("symbol", marketData.getSymbol());
        metadata.put("price", marketData.getPrice());
        metadata.put("previousPrice", marketData.getPreviousPrice());
        metadata.put("volatility", marketData.getVolatility());
        metadata.put("priceChangePercentage", marketData.getPriceChangePercentage());
        metadata.put("marketType", marketData.getMarketType());
        metadata.put("volume", marketData.getVolume());
        
        return new RiskIntelligenceEvent(
            eventId,
            riskType.name(),
            description,
            probability.name(),
            "MARKET_DATA_ANALYZER",
            "MARKET_RISK_DETECTION",
            LocalDateTime.now(),
            metadata,
            confidenceScore,
            new String[]{marketData.getSymbol()},
            alertLevel
        );
    }
    
    private Risk.RiskType determineRiskType(MarketDataEvent marketData) {
        if (marketData.isSignificantPriceChange(priceChangeThreshold)) {
            return Risk.RiskType.PRICING;
        } else if (marketData.isHighVolatility(volatilityThreshold)) {
            return Risk.RiskType.MARKET_PRACTICE;
        }
        return Risk.RiskType.MARKET_PRACTICE;
    }
    
    private Risk.RiskProbability calculateRiskProbability(MarketDataEvent marketData) {
        BigDecimal priceChange = marketData.getPriceChangePercentage().abs();
        BigDecimal volatility = marketData.getVolatility() != null ? marketData.getVolatility() : BigDecimal.ZERO;
        
        if (priceChange.compareTo(BigDecimal.valueOf(20)) > 0 || 
            volatility.compareTo(BigDecimal.valueOf(0.15)) > 0) {
            return Risk.RiskProbability.HIGH;
        } else if (priceChange.compareTo(BigDecimal.valueOf(10)) > 0 || 
                   volatility.compareTo(BigDecimal.valueOf(0.08)) > 0) {
            return Risk.RiskProbability.MEDIUM;
        }
        return Risk.RiskProbability.LOW;
    }
    
    private String generateRiskDescription(MarketDataEvent marketData) {
        StringBuilder description = new StringBuilder();
        description.append("Market risk detected for ").append(marketData.getSymbol()).append(": ");
        
        if (marketData.isSignificantPriceChange(priceChangeThreshold)) {
            description.append("Significant price change of ")
                      .append(marketData.getPriceChangePercentage().setScale(2, BigDecimal.ROUND_HALF_UP))
                      .append("%. ");
        }
        
        if (marketData.isHighVolatility(volatilityThreshold)) {
            description.append("High volatility detected at ")
                      .append(marketData.getVolatility().multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP))
                      .append("%. ");
        }
        
        description.append("Current price: $").append(marketData.getPrice());
        
        return description.toString();
    }
    
    private String determineAlertLevel(MarketDataEvent marketData) {
        BigDecimal priceChange = marketData.getPriceChangePercentage().abs();
        BigDecimal volatility = marketData.getVolatility() != null ? marketData.getVolatility() : BigDecimal.ZERO;
        
        if (priceChange.compareTo(BigDecimal.valueOf(25)) > 0 || 
            volatility.compareTo(BigDecimal.valueOf(0.20)) > 0) {
            return "CRITICAL";
        } else if (priceChange.compareTo(BigDecimal.valueOf(15)) > 0 || 
                   volatility.compareTo(BigDecimal.valueOf(0.12)) > 0) {
            return "HIGH";
        } else if (priceChange.compareTo(BigDecimal.valueOf(10)) > 0 || 
                   volatility.compareTo(BigDecimal.valueOf(0.08)) > 0) {
            return "MEDIUM";
        }
        return "LOW";
    }
    
    private Double calculateConfidenceScore(MarketDataEvent marketData) {
        double score = 0.5;
        
        BigDecimal priceChange = marketData.getPriceChangePercentage().abs();
        if (priceChange.compareTo(BigDecimal.valueOf(10)) > 0) {
            score += 0.2;
        }
        if (priceChange.compareTo(BigDecimal.valueOf(20)) > 0) {
            score += 0.2;
        }
        
        if (marketData.getVolatility() != null) {
            if (marketData.getVolatility().compareTo(BigDecimal.valueOf(0.08)) > 0) {
                score += 0.15;
            }
            if (marketData.getVolatility().compareTo(BigDecimal.valueOf(0.15)) > 0) {
                score += 0.15;
            }
        }
        
        if (marketData.getVolume() != null && 
            marketData.getVolume().compareTo(BigDecimal.valueOf(1000000)) > 0) {
            score += 0.1;
        }
        
        return Math.min(score, 1.0);
    }
}
