package com.riskassessment.repository;

import com.riskassessment.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {
    
    List<Risk> findByRiskStatus(Risk.RiskStatus riskStatus);
    
    List<Risk> findByRiskType(Risk.RiskType riskType);
    
    List<Risk> findByRiskProbability(Risk.RiskProbability riskProbability);
    
    List<Risk> findByRiskImpact(Risk.RiskImpact riskImpact);
    
    @Query("SELECT r FROM Risk r ORDER BY r.riskDate DESC")
    List<Risk> findAllOrderByRiskDateDesc();
}
