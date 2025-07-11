package com.riskassessment.dto.bigdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.riskassessment.entity.Risk;

import java.time.LocalDateTime;
import java.util.Map;

public class RiskIntelligenceEvent {
    
    private String eventId;
    private String riskType;
    private String riskDescription;
    private String riskProbability;
    private String source;
    private String eventType;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detectedAt;
    
    private Map<String, Object> metadata;
    private Double confidenceScore;
    private String[] relatedSymbols;
    private String alertLevel;

    public RiskIntelligenceEvent() {}

    public RiskIntelligenceEvent(String eventId, String riskType, String riskDescription, 
                               String riskProbability, String source, String eventType, 
                               LocalDateTime detectedAt, Map<String, Object> metadata, 
                               Double confidenceScore, String[] relatedSymbols, String alertLevel) {
        this.eventId = eventId;
        this.riskType = riskType;
        this.riskDescription = riskDescription;
        this.riskProbability = riskProbability;
        this.source = source;
        this.eventType = eventType;
        this.detectedAt = detectedAt;
        this.metadata = metadata;
        this.confidenceScore = confidenceScore;
        this.relatedSymbols = relatedSymbols;
        this.alertLevel = alertLevel;
    }

    public Risk.RiskType getRiskTypeEnum() {
        try {
            return Risk.RiskType.valueOf(riskType);
        } catch (IllegalArgumentException e) {
            return Risk.RiskType.MARKET_PRACTICE;
        }
    }

    public Risk.RiskProbability getRiskProbabilityEnum() {
        try {
            return Risk.RiskProbability.valueOf(riskProbability);
        } catch (IllegalArgumentException e) {
            return Risk.RiskProbability.MEDIUM;
        }
    }

    public boolean isHighConfidence() {
        return confidenceScore != null && confidenceScore >= 0.8;
    }

    public boolean isCriticalAlert() {
        return "CRITICAL".equalsIgnoreCase(alertLevel);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public String getRiskProbability() {
        return riskProbability;
    }

    public void setRiskProbability(String riskProbability) {
        this.riskProbability = riskProbability;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String[] getRelatedSymbols() {
        return relatedSymbols;
    }

    public void setRelatedSymbols(String[] relatedSymbols) {
        this.relatedSymbols = relatedSymbols;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }
}
