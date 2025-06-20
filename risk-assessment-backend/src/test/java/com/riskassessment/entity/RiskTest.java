package com.riskassessment.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class RiskTest {
    
    @Test
    public void testRiskCreation() {
        Risk risk = new Risk();
        risk.setRiskDate(LocalDate.now());
        risk.setRiskType(Risk.RiskType.MARKET_PRACTICE);
        risk.setRiskProbability(Risk.RiskProbability.HIGH);
        risk.setRiskDesc("Test risk description");
        risk.setRiskStatus(Risk.RiskStatus.OPEN);
        risk.setRiskRemarks("Test remarks");
        risk.setRiskImpact(Risk.RiskImpact.HIGH);
        
        assertEquals(LocalDate.now(), risk.getRiskDate());
        assertEquals(Risk.RiskType.MARKET_PRACTICE, risk.getRiskType());
        assertEquals(Risk.RiskProbability.HIGH, risk.getRiskProbability());
        assertEquals("Test risk description", risk.getRiskDesc());
        assertEquals(Risk.RiskStatus.OPEN, risk.getRiskStatus());
        assertEquals("Test remarks", risk.getRiskRemarks());
        assertEquals(Risk.RiskImpact.HIGH, risk.getRiskImpact());
    }
    
    @Test
    public void testRiskConstructorWithParameters() {
        LocalDate testDate = LocalDate.of(2023, 6, 15);
        Risk risk = new Risk(testDate, Risk.RiskType.REGULATORY, Risk.RiskProbability.MEDIUM,
                           "Regulatory compliance risk", Risk.RiskStatus.IN_PROGRESS, "Under review", Risk.RiskImpact.MEDIUM);
        
        assertEquals(testDate, risk.getRiskDate());
        assertEquals(Risk.RiskType.REGULATORY, risk.getRiskType());
        assertEquals(Risk.RiskProbability.MEDIUM, risk.getRiskProbability());
        assertEquals("Regulatory compliance risk", risk.getRiskDesc());
        assertEquals(Risk.RiskStatus.IN_PROGRESS, risk.getRiskStatus());
        assertEquals("Under review", risk.getRiskRemarks());
        assertEquals(Risk.RiskImpact.MEDIUM, risk.getRiskImpact());
    }
    
    @Test
    public void testRiskTypeEnum() {
        assertEquals("Market Practice", Risk.RiskType.MARKET_PRACTICE.getDisplayName());
        assertEquals("Conflict of Interest", Risk.RiskType.CONFLICT_OF_INTEREST.getDisplayName());
        assertEquals("Pricing", Risk.RiskType.PRICING.getDisplayName());
        assertEquals("Regulatory", Risk.RiskType.REGULATORY.getDisplayName());
        assertEquals("Governance", Risk.RiskType.GOVERNANCE.getDisplayName());
    }
    
    @Test
    public void testRiskProbabilityEnum() {
        assertEquals("Low", Risk.RiskProbability.LOW.getDisplayName());
        assertEquals("Medium", Risk.RiskProbability.MEDIUM.getDisplayName());
        assertEquals("High", Risk.RiskProbability.HIGH.getDisplayName());
    }
    
    @Test
    public void testRiskStatusEnum() {
        assertEquals("Open", Risk.RiskStatus.OPEN.getDisplayName());
        assertEquals("In Progress", Risk.RiskStatus.IN_PROGRESS.getDisplayName());
        assertEquals("Closed", Risk.RiskStatus.CLOSED.getDisplayName());
    }
    
    @Test
    public void testRiskImpactEnum() {
        assertEquals("Low", Risk.RiskImpact.LOW.getDisplayName());
        assertEquals("Medium", Risk.RiskImpact.MEDIUM.getDisplayName());
        assertEquals("High", Risk.RiskImpact.HIGH.getDisplayName());
    }
}
