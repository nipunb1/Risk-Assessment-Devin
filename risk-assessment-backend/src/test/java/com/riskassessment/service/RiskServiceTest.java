package com.riskassessment.service;

import com.riskassessment.dto.RiskDTO;
import com.riskassessment.entity.Risk;
import com.riskassessment.repository.RiskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {
    
    @Mock
    private RiskRepository riskRepository;
    
    @InjectMocks
    private RiskService riskService;
    
    private Risk testRisk;
    private RiskDTO testRiskDTO;
    
    @BeforeEach
    public void setUp() {
        testRisk = new Risk();
        testRisk.setRiskId(1L);
        testRisk.setRiskDate(LocalDate.now());
        testRisk.setRiskType(Risk.RiskType.MARKET_PRACTICE);
        testRisk.setRiskProbability(Risk.RiskProbability.HIGH);
        testRisk.setRiskDesc("Test risk");
        testRisk.setRiskStatus(Risk.RiskStatus.OPEN);
        testRisk.setRiskRemarks("Test remarks");
        
        testRiskDTO = new RiskDTO(testRisk);
    }
    
    @Test
    public void testGetAllRisks() {
        when(riskRepository.findAllOrderByRiskDateDesc()).thenReturn(Arrays.asList(testRisk));
        
        List<RiskDTO> result = riskService.getAllRisks();
        
        assertEquals(1, result.size());
        assertEquals(testRisk.getRiskId(), result.get(0).getRiskId());
        verify(riskRepository).findAllOrderByRiskDateDesc();
    }
    
    @Test
    public void testGetRiskById() {
        when(riskRepository.findById(1L)).thenReturn(Optional.of(testRisk));
        
        Optional<RiskDTO> result = riskService.getRiskById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(testRisk.getRiskId(), result.get().getRiskId());
        verify(riskRepository).findById(1L);
    }
    
    @Test
    public void testGetRiskByIdNotFound() {
        when(riskRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<RiskDTO> result = riskService.getRiskById(1L);
        
        assertFalse(result.isPresent());
        verify(riskRepository).findById(1L);
    }
    
    @Test
    public void testCreateRisk() {
        when(riskRepository.save(any(Risk.class))).thenReturn(testRisk);
        
        RiskDTO result = riskService.createRisk(testRiskDTO);
        
        assertEquals(testRisk.getRiskId(), result.getRiskId());
        verify(riskRepository).save(any(Risk.class));
    }
    
    @Test
    public void testUpdateRisk() {
        when(riskRepository.findById(1L)).thenReturn(Optional.of(testRisk));
        when(riskRepository.save(any(Risk.class))).thenReturn(testRisk);
        
        Optional<RiskDTO> result = riskService.updateRisk(1L, testRiskDTO);
        
        assertTrue(result.isPresent());
        assertEquals(testRisk.getRiskId(), result.get().getRiskId());
        verify(riskRepository).findById(1L);
        verify(riskRepository).save(any(Risk.class));
    }
    
    @Test
    public void testUpdateRiskNotFound() {
        when(riskRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<RiskDTO> result = riskService.updateRisk(1L, testRiskDTO);
        
        assertFalse(result.isPresent());
        verify(riskRepository).findById(1L);
        verify(riskRepository, never()).save(any(Risk.class));
    }
    
    @Test
    public void testDeleteRisk() {
        when(riskRepository.existsById(1L)).thenReturn(true);
        
        boolean result = riskService.deleteRisk(1L);
        
        assertTrue(result);
        verify(riskRepository).existsById(1L);
        verify(riskRepository).deleteById(1L);
    }
    
    @Test
    public void testDeleteRiskNotFound() {
        when(riskRepository.existsById(1L)).thenReturn(false);
        
        boolean result = riskService.deleteRisk(1L);
        
        assertFalse(result);
        verify(riskRepository).existsById(1L);
        verify(riskRepository, never()).deleteById(anyLong());
    }
    
    @Test
    public void testGetRisksByStatus() {
        when(riskRepository.findByRiskStatus(Risk.RiskStatus.OPEN)).thenReturn(Arrays.asList(testRisk));
        
        List<RiskDTO> result = riskService.getRisksByStatus(Risk.RiskStatus.OPEN);
        
        assertEquals(1, result.size());
        assertEquals(testRisk.getRiskId(), result.get(0).getRiskId());
        verify(riskRepository).findByRiskStatus(Risk.RiskStatus.OPEN);
    }
    
    @Test
    public void testGetRisksByType() {
        when(riskRepository.findByRiskType(Risk.RiskType.MARKET_PRACTICE)).thenReturn(Arrays.asList(testRisk));
        
        List<RiskDTO> result = riskService.getRisksByType(Risk.RiskType.MARKET_PRACTICE);
        
        assertEquals(1, result.size());
        assertEquals(testRisk.getRiskId(), result.get(0).getRiskId());
        verify(riskRepository).findByRiskType(Risk.RiskType.MARKET_PRACTICE);
    }
    
    @Test
    public void testGetRisksByProbability() {
        when(riskRepository.findByRiskProbability(Risk.RiskProbability.HIGH)).thenReturn(Arrays.asList(testRisk));
        
        List<RiskDTO> result = riskService.getRisksByProbability(Risk.RiskProbability.HIGH);
        
        assertEquals(1, result.size());
        assertEquals(testRisk.getRiskId(), result.get(0).getRiskId());
        verify(riskRepository).findByRiskProbability(Risk.RiskProbability.HIGH);
    }
    
    @Test
    public void testCreateRiskFromIntelligence() {
        com.riskassessment.dto.bigdata.RiskIntelligenceEvent event = 
            new com.riskassessment.dto.bigdata.RiskIntelligenceEvent();
        event.setEventId("test-event-123");
        event.setRiskType("MARKET_PRACTICE");
        event.setRiskProbability("HIGH");
        event.setRiskDescription("Test market risk from intelligence");
        event.setSource("MARKET_DATA_ANALYZER");
        event.setConfidenceScore(0.85);
        event.setAlertLevel("HIGH");
        
        Risk savedRisk = new Risk();
        savedRisk.setRiskId(1L);
        savedRisk.setRiskDate(LocalDate.now());
        savedRisk.setRiskType(Risk.RiskType.MARKET_PRACTICE);
        savedRisk.setRiskProbability(Risk.RiskProbability.HIGH);
        savedRisk.setRiskDesc("Test market risk from intelligence");
        savedRisk.setRiskStatus(Risk.RiskStatus.OPEN);
        savedRisk.setRiskRemarks("Auto-generated from MARKET_DATA_ANALYZER | Confidence: 0.85 | Alert Level: HIGH");
        
        when(riskRepository.save(any(Risk.class))).thenReturn(savedRisk);
        
        RiskDTO result = riskService.createRiskFromIntelligence(event);
        
        assertNotNull(result);
        assertEquals(savedRisk.getRiskId(), result.getRiskId());
        assertTrue(result.getRiskRemarks().contains("MARKET_DATA_ANALYZER"));
        assertTrue(result.getRiskRemarks().contains("0.85"));
        assertTrue(result.getRiskRemarks().contains("HIGH"));
        
        verify(riskRepository).save(any(Risk.class));
    }
    
    @Test
    public void testSearchRisks() {
        List<com.riskassessment.document.RiskDocument> mockResults = Arrays.asList();
        
        List<com.riskassessment.document.RiskDocument> result = riskService.searchRisks("test keyword");
        
        assertNotNull(result);
    }
    
    @Test
    public void testGetRisksBySource() {
        List<com.riskassessment.document.RiskDocument> mockResults = Arrays.asList();
        
        List<com.riskassessment.document.RiskDocument> result = riskService.getRisksBySource("MANUAL");
        
        assertNotNull(result);
    }
}
