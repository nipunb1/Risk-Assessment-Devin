package com.riskassessment.service;

import com.riskassessment.document.RiskDocument;
import com.riskassessment.repository.elasticsearch.RiskDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ElasticsearchAnalyticsServiceTest {
    
    @Mock
    private RiskDocumentRepository riskDocumentRepository;
    
    @InjectMocks
    private ElasticsearchAnalyticsService elasticsearchAnalyticsService;
    
    private RiskDocument testRiskDocument;
    
    @BeforeEach
    public void setUp() {
        testRiskDocument = new RiskDocument();
        testRiskDocument.setId("risk_1");
        testRiskDocument.setRiskId(1L);
        testRiskDocument.setRiskType("MARKET_PRACTICE");
        testRiskDocument.setRiskStatus("OPEN");
        testRiskDocument.setRiskDesc("Test risk description");
        testRiskDocument.setSource("MANUAL");
        testRiskDocument.setRiskDate(LocalDate.now());
    }
    
    @Test
    public void testGetRiskAnalytics() {
        when(riskDocumentRepository.count()).thenReturn(10L);
        when(riskDocumentRepository.countByRiskType("MARKET_PRACTICE")).thenReturn(5L);
        when(riskDocumentRepository.countByRiskStatus("OPEN")).thenReturn(7L);
        when(riskDocumentRepository.countBySource("MANUAL")).thenReturn(8L);
        when(riskDocumentRepository.findByRiskDateBetween(any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(Arrays.asList(testRiskDocument));
        
        Map<String, Object> analytics = elasticsearchAnalyticsService.getRiskAnalytics();
        
        assertNotNull(analytics);
        assertEquals(10L, analytics.get("totalRisks"));
        assertNotNull(analytics.get("risksByType"));
        assertNotNull(analytics.get("risksByStatus"));
        assertNotNull(analytics.get("risksBySource"));
        assertNotNull(analytics.get("recentRisks"));
        
        verify(riskDocumentRepository).count();
        verify(riskDocumentRepository).countByRiskType("MARKET_PRACTICE");
        verify(riskDocumentRepository).countByRiskStatus("OPEN");
        verify(riskDocumentRepository).countBySource("MANUAL");
    }
    
    @Test
    public void testSearchSimilarRisks() {
        List<RiskDocument> mockResults = Arrays.asList(testRiskDocument);
        when(riskDocumentRepository.findByRiskDescContaining("test")).thenReturn(mockResults);
        
        List<RiskDocument> results = elasticsearchAnalyticsService.searchSimilarRisks("test", "MARKET_PRACTICE");
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("MARKET_PRACTICE", results.get(0).getRiskType());
        
        verify(riskDocumentRepository).findByRiskDescContaining("test");
    }
    
    @Test
    public void testGetRiskTrends() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        List<RiskDocument> mockResults = Arrays.asList(testRiskDocument);
        
        when(riskDocumentRepository.findByRiskDateBetween(startDate, endDate)).thenReturn(mockResults);
        
        List<RiskDocument> results = elasticsearchAnalyticsService.getRiskTrends(startDate, endDate);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        verify(riskDocumentRepository).findByRiskDateBetween(startDate, endDate);
    }
    
    @Test
    public void testGetRiskAnalyticsWithException() {
        when(riskDocumentRepository.count()).thenThrow(new RuntimeException("Elasticsearch connection failed"));
        
        Map<String, Object> analytics = elasticsearchAnalyticsService.getRiskAnalytics();
        
        assertNotNull(analytics);
        assertTrue(analytics.containsKey("error"));
        assertEquals("Failed to retrieve analytics data", analytics.get("error"));
    }
}
