package com.riskassessment.controller;

import com.riskassessment.document.RiskDocument;
import com.riskassessment.service.ElasticsearchAnalyticsService;
import com.riskassessment.service.SparkAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    @Autowired
    private ElasticsearchAnalyticsService elasticsearchAnalyticsService;
    
    @Autowired
    private SparkAnalyticsService sparkAnalyticsService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardAnalytics() {
        Map<String, Object> analytics = elasticsearchAnalyticsService.getRiskAnalytics();
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/spark")
    public ResponseEntity<Map<String, Object>> getSparkAnalytics() {
        Map<String, Object> analytics = sparkAnalyticsService.performRiskAnalytics();
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/predictions")
    public ResponseEntity<Map<String, Object>> getRiskPredictions() {
        Map<String, Object> predictions = sparkAnalyticsService.predictRiskTrends();
        return ResponseEntity.ok(predictions);
    }
    
    @GetMapping("/similar")
    public ResponseEntity<List<RiskDocument>> getSimilarRisks(
            @RequestParam String description,
            @RequestParam(required = false) String riskType) {
        List<RiskDocument> similarRisks = elasticsearchAnalyticsService.searchSimilarRisks(description, riskType);
        return ResponseEntity.ok(similarRisks);
    }
    
    @GetMapping("/trends")
    public ResponseEntity<List<RiskDocument>> getRiskTrends(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<RiskDocument> trends = elasticsearchAnalyticsService.getRiskTrends(start, end);
        return ResponseEntity.ok(trends);
    }
}
