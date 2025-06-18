package com.riskassessment.dto;

import com.riskassessment.entity.Risk;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class RiskDTOTest {
    
    @Test
    public void testRiskDTOFromEntity() {
        Risk risk = new Risk();
        risk.setRiskId(1L);
        risk.setRiskDate(LocalDate.now());
        risk.setRiskType(Risk.RiskType.PRICING);
        risk.setRiskProbability(Risk.RiskProbability.LOW);
        risk.setRiskDesc("Pricing risk test");
        risk.setRiskStatus(Risk.RiskStatus.CLOSED);
        risk.setRiskRemarks("Resolved");
        
        RiskDTO dto = new RiskDTO(risk);
        
        assertEquals(1L, dto.getRiskId());
        assertEquals(LocalDate.now(), dto.getRiskDate());
        assertEquals(Risk.RiskType.PRICING, dto.getRiskType());
        assertEquals(Risk.RiskProbability.LOW, dto.getRiskProbability());
        assertEquals("Pricing risk test", dto.getRiskDesc());
        assertEquals(Risk.RiskStatus.CLOSED, dto.getRiskStatus());
        assertEquals("Resolved", dto.getRiskRemarks());
    }
    
    @Test
    public void testRiskDTOToEntity() {
        RiskDTO dto = new RiskDTO();
        dto.setRiskId(2L);
        dto.setRiskDate(LocalDate.of(2023, 5, 10));
        dto.setRiskType(Risk.RiskType.GOVERNANCE);
        dto.setRiskProbability(Risk.RiskProbability.HIGH);
        dto.setRiskDesc("Governance issue");
        dto.setRiskStatus(Risk.RiskStatus.OPEN);
        dto.setRiskRemarks("Needs attention");
        
        Risk entity = dto.toEntity();
        
        assertEquals(2L, entity.getRiskId());
        assertEquals(LocalDate.of(2023, 5, 10), entity.getRiskDate());
        assertEquals(Risk.RiskType.GOVERNANCE, entity.getRiskType());
        assertEquals(Risk.RiskProbability.HIGH, entity.getRiskProbability());
        assertEquals("Governance issue", entity.getRiskDesc());
        assertEquals(Risk.RiskStatus.OPEN, entity.getRiskStatus());
        assertEquals("Needs attention", entity.getRiskRemarks());
    }
    
    @Test
    public void testEmptyRiskDTO() {
        RiskDTO dto = new RiskDTO();
        assertNull(dto.getRiskId());
        assertNull(dto.getRiskDate());
        assertNull(dto.getRiskType());
        assertNull(dto.getRiskProbability());
        assertNull(dto.getRiskDesc());
        assertNull(dto.getRiskStatus());
        assertNull(dto.getRiskRemarks());
    }
}
