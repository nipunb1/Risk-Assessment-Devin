package com.riskassessment.controller;

/**
 * Risk Controller - REST API endpoints for risk management operations.
 * 
 * This controller provides RESTful web services for managing risk assessments.
 * It handles HTTP requests, performs input validation, delegates business logic
 * to the service layer, and returns appropriate HTTP responses.
 * 
 * Supported operations:
 * - GET /api/risks - Retrieve all risks
 * - GET /api/risks/{id} - Retrieve specific risk by ID
 * - POST /api/risks - Create new risk assessment
 * - PUT /api/risks/{id} - Update existing risk
 * - DELETE /api/risks/{id} - Delete risk assessment
 * - GET /api/risks/status/{status} - Filter risks by status
 * - GET /api/risks/enums - Get enumeration values
 * 
 * All endpoints return JSON responses and follow REST conventions for
 * HTTP status codes and error handling.
 * 
 * @author Risk Assessment System
 * @version 1.0
 * @since 1.0
 */

import com.riskassessment.dto.RiskDTO;
import com.riskassessment.entity.Risk;
import com.riskassessment.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/risks")
@CrossOrigin(origins = {"http://localhost:4200", "https://risk-assessment-app-tunnel-i30jgj31.devinapps.com"})
public class RiskController {
    
    @Autowired
    private RiskService riskService;
    
    @GetMapping
    public ResponseEntity<List<RiskDTO>> getAllRisks() {
        List<RiskDTO> risks = riskService.getAllRisks();
        return ResponseEntity.ok(risks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RiskDTO> getRiskById(@PathVariable Long id) {
        Optional<RiskDTO> risk = riskService.getRiskById(id);
        return risk.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<RiskDTO> createRisk(@Valid @RequestBody RiskDTO riskDTO) {
        RiskDTO createdRisk = riskService.createRisk(riskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRisk);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RiskDTO> updateRisk(@PathVariable Long id, @Valid @RequestBody RiskDTO riskDTO) {
        Optional<RiskDTO> updatedRisk = riskService.updateRisk(id, riskDTO);
        return updatedRisk.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id) {
        boolean deleted = riskService.deleteRisk(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RiskDTO>> getRisksByStatus(@PathVariable Risk.RiskStatus status) {
        List<RiskDTO> risks = riskService.getRisksByStatus(status);
        return ResponseEntity.ok(risks);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<RiskDTO>> getRisksByType(@PathVariable Risk.RiskType type) {
        List<RiskDTO> risks = riskService.getRisksByType(type);
        return ResponseEntity.ok(risks);
    }
    
    @GetMapping("/probability/{probability}")
    public ResponseEntity<List<RiskDTO>> getRisksByProbability(@PathVariable Risk.RiskProbability probability) {
        List<RiskDTO> risks = riskService.getRisksByProbability(probability);
        return ResponseEntity.ok(risks);
    }
    
    @GetMapping("/impact/{impact}")
    public ResponseEntity<List<RiskDTO>> getRisksByImpact(@PathVariable Risk.RiskImpact impact) {
        List<RiskDTO> risks = riskService.getRisksByImpact(impact);
        return ResponseEntity.ok(risks);
    }
    
    @GetMapping("/enums")
    public ResponseEntity<EnumValues> getEnumValues() {
        EnumValues enumValues = new EnumValues();
        return ResponseEntity.ok(enumValues);
    }
    
    public static class EnumValues {
        public Risk.RiskType[] riskTypes = Risk.RiskType.values();
        public Risk.RiskProbability[] riskProbabilities = Risk.RiskProbability.values();
        public Risk.RiskStatus[] riskStatuses = Risk.RiskStatus.values();
        public Risk.RiskImpact[] riskImpacts = Risk.RiskImpact.values();
    }
}
