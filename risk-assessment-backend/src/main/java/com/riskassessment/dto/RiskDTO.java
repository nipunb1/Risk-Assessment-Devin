package com.riskassessment.dto;

import com.riskassessment.entity.Risk;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RiskDTO {
    
    private Long riskId;
    
    @NotNull
    private LocalDate riskDate;
    
    @NotNull
    private Risk.RiskType riskType;
    
    @NotNull
    private Risk.RiskProbability riskProbability;
    
    
    @NotNull
    private Risk.RiskStatus riskStatus;
    
    private String riskRemarks;
    
    @NotNull
    private Risk.RiskImpact riskImpact;
    
    public RiskDTO() {}
    
    public RiskDTO(Risk risk) {
        this.riskId = risk.getRiskId();
        this.riskDate = risk.getRiskDate();
        this.riskType = risk.getRiskType();
        this.riskProbability = risk.getRiskProbability();
        this.riskStatus = risk.getRiskStatus();
        this.riskRemarks = risk.getRiskRemarks();
        this.riskImpact = risk.getRiskImpact();
    }
    
    public Risk toEntity() {
        Risk risk = new Risk();
        risk.setRiskId(this.riskId);
        risk.setRiskDate(this.riskDate);
        risk.setRiskType(this.riskType);
        risk.setRiskProbability(this.riskProbability);
        risk.setRiskStatus(this.riskStatus);
        risk.setRiskRemarks(this.riskRemarks);
        risk.setRiskImpact(this.riskImpact);
        return risk;
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
    
    public Risk.RiskType getRiskType() {
        return riskType;
    }
    
    public void setRiskType(Risk.RiskType riskType) {
        this.riskType = riskType;
    }
    
    public Risk.RiskProbability getRiskProbability() {
        return riskProbability;
    }
    
    public void setRiskProbability(Risk.RiskProbability riskProbability) {
        this.riskProbability = riskProbability;
    }
    
    
    public Risk.RiskStatus getRiskStatus() {
        return riskStatus;
    }
    
    public void setRiskStatus(Risk.RiskStatus riskStatus) {
        this.riskStatus = riskStatus;
    }
    
    public String getRiskRemarks() {
        return riskRemarks;
    }
    
    public void setRiskRemarks(String riskRemarks) {
        this.riskRemarks = riskRemarks;
    }
    
    public Risk.RiskImpact getRiskImpact() {
        return riskImpact;
    }
    
    public void setRiskImpact(Risk.RiskImpact riskImpact) {
        this.riskImpact = riskImpact;
    }
}
