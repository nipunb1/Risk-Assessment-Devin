package com.riskassessment.document;

import com.riskassessment.dto.RiskDTO;
import com.riskassessment.entity.Risk;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "risk-intelligence-index")
public class RiskDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Long)
    private Long riskId;
    
    @Field(type = FieldType.Date)
    private LocalDate riskDate;
    
    @Field(type = FieldType.Keyword)
    private String riskType;
    
    @Field(type = FieldType.Keyword)
    private String riskProbability;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String riskDesc;
    
    @Field(type = FieldType.Keyword)
    private String riskStatus;
    
    @Field(type = FieldType.Text)
    private String riskRemarks;
    
    @Field(type = FieldType.Keyword)
    private String source;
    
    @Field(type = FieldType.Date)
    private LocalDate indexedAt;

    public RiskDocument() {
        this.indexedAt = LocalDate.now();
    }

    public RiskDocument(RiskDTO riskDTO) {
        this.riskId = riskDTO.getRiskId();
        this.riskDate = riskDTO.getRiskDate();
        this.riskType = riskDTO.getRiskType() != null ? riskDTO.getRiskType().name() : null;
        this.riskProbability = riskDTO.getRiskProbability() != null ? riskDTO.getRiskProbability().name() : null;
        this.riskDesc = riskDTO.getRiskDesc();
        this.riskStatus = riskDTO.getRiskStatus() != null ? riskDTO.getRiskStatus().name() : null;
        this.riskRemarks = riskDTO.getRiskRemarks();
        this.source = "MANUAL";
        this.indexedAt = LocalDate.now();
        this.id = "risk_" + riskDTO.getRiskId();
    }

    public RiskDocument(RiskDTO riskDTO, String source) {
        this(riskDTO);
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public LocalDate getRiskDate() {
        return riskDate;
    }

    public void setRiskDate(LocalDate riskDate) {
        this.riskDate = riskDate;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getRiskProbability() {
        return riskProbability;
    }

    public void setRiskProbability(String riskProbability) {
        this.riskProbability = riskProbability;
    }

    public String getRiskDesc() {
        return riskDesc;
    }

    public void setRiskDesc(String riskDesc) {
        this.riskDesc = riskDesc;
    }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getRiskRemarks() {
        return riskRemarks;
    }

    public void setRiskRemarks(String riskRemarks) {
        this.riskRemarks = riskRemarks;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getIndexedAt() {
        return indexedAt;
    }

    public void setIndexedAt(LocalDate indexedAt) {
        this.indexedAt = indexedAt;
    }
}
