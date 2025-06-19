package com.riskassessment.repository;

import com.riskassessment.entity.CopilotProjectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CopilotProjectDataRepository extends JpaRepository<CopilotProjectData, Long> {
    
    List<CopilotProjectData> findByReportingMonth(String reportingMonth);
    
    List<CopilotProjectData> findByLobName(String lobName);
    
    @Query("SELECT c.lobName, " +
           "AVG(c.licenseUsage) as avgLicenseUsage, " +
           "AVG(c.actualLicenseUsage) as avgActualLicenseUsage, " +
           "AVG(c.applicableEfficiency) as avgApplicableEfficiency, " +
           "COUNT(DISTINCT c.projectName) as totalProjects " +
           "FROM CopilotProjectData c " +
           "WHERE c.reportingMonth = :reportingMonth " +
           "GROUP BY c.lobName")
    List<Object[]> getCopilotDashboardDataByMonth(@Param("reportingMonth") String reportingMonth);
    
    @Query("SELECT DISTINCT c.reportingMonth FROM CopilotProjectData c ORDER BY c.reportingMonth DESC")
    List<String> findDistinctReportingMonths();
}
