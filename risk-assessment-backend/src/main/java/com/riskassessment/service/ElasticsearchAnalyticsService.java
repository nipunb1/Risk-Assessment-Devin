package com.riskassessment.service;

import com.riskassessment.document.RiskDocument;
import com.riskassessment.repository.elasticsearch.RiskDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchAnalyticsService {
    
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchAnalyticsService.class);
    
    @Autowired
    private RiskDocumentRepository riskDocumentRepository;
    
    public Map<String, Object> getRiskAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        try {
            analytics.put("totalRisks", riskDocumentRepository.count());
            analytics.put("risksByType", getRiskCountsByType());
            analytics.put("risksByStatus", getRiskCountsByStatus());
            analytics.put("risksBySource", getRiskCountsBySource());
            analytics.put("recentRisks", getRecentRisks(7));
            
            logger.debug("Generated risk analytics with {} total risks", analytics.get("totalRisks"));
        } catch (Exception e) {
            logger.error("Failed to generate risk analytics: {}", e.getMessage());
            analytics.put("error", "Failed to retrieve analytics data");
        }
        
        return analytics;
    }
    
    public List<RiskDocument> searchSimilarRisks(String description, String riskType) {
        try {
            List<RiskDocument> similarRisks = riskDocumentRepository.findByRiskDescContaining(description);
            
            if (riskType != null && !riskType.isEmpty()) {
                similarRisks = similarRisks.stream()
                    .filter(risk -> riskType.equals(risk.getRiskType()))
                    .toList();
            }
            
            logger.debug("Found {} similar risks for description containing: {}", similarRisks.size(), description);
            return similarRisks;
        } catch (Exception e) {
            logger.error("Failed to search similar risks: {}", e.getMessage());
            return List.of();
        }
    }
    
    public List<RiskDocument> getRiskTrends(LocalDate startDate, LocalDate endDate) {
        try {
            List<RiskDocument> risks = riskDocumentRepository.findByRiskDateBetween(startDate, endDate);
            logger.debug("Retrieved {} risks for trend analysis between {} and {}", 
                        risks.size(), startDate, endDate);
            return risks;
        } catch (Exception e) {
            logger.error("Failed to get risk trends: {}", e.getMessage());
            return List.of();
        }
    }
    
    private Map<String, Long> getRiskCountsByType() {
        Map<String, Long> counts = new HashMap<>();
        try {
            counts.put("MARKET_PRACTICE", riskDocumentRepository.countByRiskType("MARKET_PRACTICE"));
            counts.put("PRICING", riskDocumentRepository.countByRiskType("PRICING"));
            counts.put("REGULATORY", riskDocumentRepository.countByRiskType("REGULATORY"));
            counts.put("GOVERNANCE", riskDocumentRepository.countByRiskType("GOVERNANCE"));
            counts.put("CONFLICT_OF_INTEREST", riskDocumentRepository.countByRiskType("CONFLICT_OF_INTEREST"));
        } catch (Exception e) {
            logger.warn("Failed to get risk counts by type: {}", e.getMessage());
        }
        return counts;
    }
    
    private Map<String, Long> getRiskCountsByStatus() {
        Map<String, Long> counts = new HashMap<>();
        try {
            counts.put("OPEN", riskDocumentRepository.countByRiskStatus("OPEN"));
            counts.put("IN_PROGRESS", riskDocumentRepository.countByRiskStatus("IN_PROGRESS"));
            counts.put("CLOSED", riskDocumentRepository.countByRiskStatus("CLOSED"));
        } catch (Exception e) {
            logger.warn("Failed to get risk counts by status: {}", e.getMessage());
        }
        return counts;
    }
    
    private Map<String, Long> getRiskCountsBySource() {
        Map<String, Long> counts = new HashMap<>();
        try {
            counts.put("MANUAL", riskDocumentRepository.countBySource("MANUAL"));
            counts.put("MARKET_DATA_ANALYZER", riskDocumentRepository.countBySource("MARKET_DATA_ANALYZER"));
            counts.put("AUTOMATED", riskDocumentRepository.countBySource("AUTOMATED"));
        } catch (Exception e) {
            logger.warn("Failed to get risk counts by source: {}", e.getMessage());
        }
        return counts;
    }
    
    private List<RiskDocument> getRecentRisks(int days) {
        try {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(days);
            return riskDocumentRepository.findByRiskDateBetween(startDate, endDate);
        } catch (Exception e) {
            logger.warn("Failed to get recent risks: {}", e.getMessage());
            return List.of();
        }
    }
}
