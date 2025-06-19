package com.riskassessment.controller;

import com.riskassessment.dto.CopilotDashboardDTO;
import com.riskassessment.dto.CopilotDetailsDTO;
import com.riskassessment.dto.CopilotDetailsPageDTO;
import com.riskassessment.service.CopilotDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/copilot-dashboard")
@CrossOrigin(origins = "*")
public class CopilotDashboardController {
    
    @Autowired
    private CopilotDashboardService copilotDashboardService;
    
    @GetMapping("/data")
    public ResponseEntity<List<CopilotDashboardDTO>> getCopilotDashboardData(
            @RequestParam String reportingMonth) {
        try {
            List<CopilotDashboardDTO> data = copilotDashboardService.getCopilotDashboardData(reportingMonth);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/months")
    public ResponseEntity<List<String>> getAvailableMonths() {
        try {
            List<String> months = copilotDashboardService.getAvailableMonths();
            return ResponseEntity.ok(months);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/lobs")
    public ResponseEntity<List<String>> getUniqueLobs() {
        try {
            List<String> lobs = copilotDashboardService.getUniqueLobs();
            return ResponseEntity.ok(lobs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/projects")
    public ResponseEntity<List<String>> getProjectsByLob(@RequestParam String lob) {
        try {
            List<String> projects = copilotDashboardService.getProjectsByLob(lob);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/months-by-filters")
    public ResponseEntity<List<String>> getMonthsByLobAndProject(
            @RequestParam String lob,
            @RequestParam(required = false) String projectName) {
        try {
            List<String> months = copilotDashboardService.getMonthsByLobAndProject(lob, projectName);
            return ResponseEntity.ok(months);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/details")
    public ResponseEntity<CopilotDetailsPageDTO> getCopilotDetails(
            @RequestParam(required = false) String lob,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) String reportingMonth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            CopilotDetailsPageDTO details = copilotDashboardService.getCopilotDetails(lob, projectName, reportingMonth, pageable);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
