package com.riskassessment.service;

import com.riskassessment.dto.CopilotDashboardDTO;
import com.riskassessment.repository.CopilotProjectDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopilotDashboardService {
    
    @Autowired
    private CopilotProjectDataRepository copilotProjectDataRepository;
    
    public List<CopilotDashboardDTO> getCopilotDashboardData(String reportingMonth) {
        List<Object[]> results = copilotProjectDataRepository.getCopilotDashboardDataByMonth(reportingMonth);
        
        return results.stream()
                .map(result -> new CopilotDashboardDTO(
                    (String) result[0],           // lob_name
                    BigDecimal.valueOf(((Number) result[1]).doubleValue()), // avg license_usage
                    BigDecimal.valueOf(((Number) result[2]).doubleValue()), // avg actual_license_usage
                    BigDecimal.valueOf(((Number) result[3]).doubleValue()), // avg applicable_efficiency
                    ((Number) result[4]).longValue() // total projects count
                ))
                .collect(Collectors.toList());
    }
    
    public List<String> getAvailableMonths() {
        return copilotProjectDataRepository.findDistinctReportingMonths();
    }
}
