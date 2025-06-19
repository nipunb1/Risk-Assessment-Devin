package com.riskassessment.repository;

import com.riskassessment.entity.CopilotProductivityMeasures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopilotProductivityMeasuresRepository extends JpaRepository<CopilotProductivityMeasures, Long> {
    
    List<CopilotProductivityMeasures> findByReportingMonth(String reportingMonth);
    
    List<CopilotProductivityMeasures> findByLobName(String lobName);
    
    List<CopilotProductivityMeasures> findByProjectName(String projectName);
    
    @Query("SELECT c FROM CopilotProductivityMeasures c ORDER BY c.date DESC")
    List<CopilotProductivityMeasures> findAllOrderByDateDesc();
}
