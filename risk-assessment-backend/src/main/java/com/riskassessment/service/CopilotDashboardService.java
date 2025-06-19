package com.riskassessment.service;

import com.riskassessment.dto.CopilotDashboardDTO;
import com.riskassessment.dto.CopilotDetailsDTO;
import com.riskassessment.dto.CopilotDetailsPageDTO;
import com.riskassessment.entity.CopilotProjectData;
import com.riskassessment.repository.CopilotProjectDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    public List<String> getUniqueLobs() {
        return copilotProjectDataRepository.findDistinctLobNames();
    }
    
    public List<String> getProjectsByLob(String lob) {
        return copilotProjectDataRepository.findDistinctProjectNamesByLob(lob);
    }
    
    public List<String> getMonthsByLobAndProject(String lob, String projectName) {
        return copilotProjectDataRepository.findDistinctReportingMonthsByLobAndProject(lob, projectName);
    }
    
    public CopilotDetailsPageDTO getCopilotDetails(String lob, String projectName, String reportingMonth, Pageable pageable) {
        Page<CopilotProjectData> page = copilotProjectDataRepository.findCopilotDetailsWithFilters(lob, projectName, reportingMonth, pageable);
        
        List<CopilotDetailsDTO> content = page.getContent().stream()
                .map(entity -> new CopilotDetailsDTO(
                    entity.getLobName(),
                    entity.getLobMinusOne(),
                    entity.getProjectName(),
                    entity.getReportingMonth(),
                    entity.getProjectHeadcount(),
                    entity.getEngineeringPopulationCount(),
                    entity.getAvailableLicenses(),
                    entity.getLicenseUsage().doubleValue(),
                    entity.getActualLicenseUsage().doubleValue(),
                    entity.getApplicableEfficiency().doubleValue()
                ))
                .collect(Collectors.toList());
        
        return new CopilotDetailsPageDTO(
            content,
            page.getTotalPages(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize(),
            page.hasNext(),
            page.hasPrevious()
        );
    }
}
