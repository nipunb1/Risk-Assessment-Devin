package com.riskassessment.controller;

import com.riskassessment.dto.CopilotDashboardDTO;
import com.riskassessment.service.CopilotDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
