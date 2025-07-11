package com.riskassessment.repository.elasticsearch;

import com.riskassessment.document.RiskDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RiskDocumentRepository extends ElasticsearchRepository<RiskDocument, String> {
    
    List<RiskDocument> findByRiskType(String riskType);
    
    List<RiskDocument> findByRiskStatus(String riskStatus);
    
    List<RiskDocument> findByRiskProbability(String riskProbability);
    
    List<RiskDocument> findBySource(String source);
    
    List<RiskDocument> findByRiskDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<RiskDocument> findByRiskDescContaining(String keyword);
    
    List<RiskDocument> findByRiskTypeAndRiskStatus(String riskType, String riskStatus);
    
    long countByRiskType(String riskType);
    
    long countByRiskStatus(String riskStatus);
    
    long countBySource(String source);
}
