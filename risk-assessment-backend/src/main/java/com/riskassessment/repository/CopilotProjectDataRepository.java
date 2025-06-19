package com.riskassessment.repository;

import com.riskassessment.entity.CopilotProjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    @Query("SELECT DISTINCT c.lobName FROM CopilotProjectData c ORDER BY c.lobName")
    List<String> findDistinctLobNames();
    
    @Query("SELECT DISTINCT c.projectName FROM CopilotProjectData c WHERE c.lobName = :lobName ORDER BY c.projectName")
    List<String> findDistinctProjectNamesByLob(@Param("lobName") String lobName);
    
    @Query("SELECT DISTINCT c.reportingMonth FROM CopilotProjectData c WHERE c.lobName = :lobName " +
           "AND (:projectName IS NULL OR c.projectName = :projectName) ORDER BY c.reportingMonth DESC")
    List<String> findDistinctReportingMonthsByLobAndProject(@Param("lobName") String lobName, 
                                                           @Param("projectName") String projectName);
    
    @Query("SELECT c FROM CopilotProjectData c WHERE " +
           "(:lob IS NULL OR c.lobName = :lob) AND " +
           "(:projectName IS NULL OR c.projectName = :projectName) AND " +
           "(:reportingMonth IS NULL OR c.reportingMonth = :reportingMonth) " +
           "ORDER BY c.lobName, c.projectName, c.reportingMonth")
    Page<CopilotProjectData> findCopilotDetailsWithFilters(@Param("lob") String lob,
                                                          @Param("projectName") String projectName,
                                                          @Param("reportingMonth") String reportingMonth,
                                                          Pageable pageable);
}
