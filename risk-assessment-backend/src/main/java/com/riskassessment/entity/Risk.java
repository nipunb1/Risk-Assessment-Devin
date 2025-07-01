package com.riskassessment.entity;

/**
 * Risk Entity - JPA entity representing a risk assessment record in the database.
 * 
 * This entity encapsulates all information related to a risk including its identification,
 * categorization, assessment details, and current status. The entity uses JPA annotations
 * for database mapping and includes validation constraints to ensure data integrity.
 * 
 * The Risk entity supports the following risk management workflow:
 * 1. Risk identification with date, type, and description
 * 2. Risk assessment with probability and impact evaluation
 * 3. Risk tracking with status updates and remarks
 * 
 * @author Risk Assessment System
 * @version 1.0
 * @since 1.0
 */

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "risk")
public class Risk {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "risk_id")
    private Long riskId;
    
    @NotNull
    @Column(name = "risk_date")
    private LocalDate riskDate;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_type")
    private RiskType riskType;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_probability")
    private RiskProbability riskProbability;
    
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status")
    private RiskStatus riskStatus;
    
    @Column(name = "risk_remarks", columnDefinition = "TEXT")
    private String riskRemarks;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_impact")
    private RiskImpact riskImpact;
    
    public enum RiskType {
        MARKET_PRACTICE("Market Practice"),
        CONFLICT_OF_INTEREST("Conflict of Interest"),
        PRICING("Pricing"),
        REGULATORY("Regulatory"),
        GOVERNANCE("Governance");
        
        private final String displayName;
        
        RiskType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RiskProbability {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High");
        
        private final String displayName;
        
        RiskProbability(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RiskStatus {
        OPEN("Open"),
        IN_PROGRESS("In Progress"),
        CLOSED("Closed");
        
        private final String displayName;
        
        RiskStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RiskImpact {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High");
        
        private final String displayName;
        
        RiskImpact(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public Risk() {}
    
    public Risk(LocalDate riskDate, RiskType riskType, RiskProbability riskProbability, 
                RiskStatus riskStatus, String riskRemarks, RiskImpact riskImpact) {
        this.riskDate = riskDate;
        this.riskType = riskType;
        this.riskProbability = riskProbability;
        this.riskStatus = riskStatus;
        this.riskRemarks = riskRemarks;
        this.riskImpact = riskImpact;
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
    
    public RiskType getRiskType() {
        return riskType;
    }
    
    public void setRiskType(RiskType riskType) {
        this.riskType = riskType;
    }
    
    public RiskProbability getRiskProbability() {
        return riskProbability;
    }
    
    public void setRiskProbability(RiskProbability riskProbability) {
        this.riskProbability = riskProbability;
    }
    
    
    public RiskStatus getRiskStatus() {
        return riskStatus;
    }
    
    public void setRiskStatus(RiskStatus riskStatus) {
        this.riskStatus = riskStatus;
    }
    
    public String getRiskRemarks() {
        return riskRemarks;
    }
    
    public void setRiskRemarks(String riskRemarks) {
        this.riskRemarks = riskRemarks;
    }
    
    public RiskImpact getRiskImpact() {
        return riskImpact;
    }
    
    public void setRiskImpact(RiskImpact riskImpact) {
        this.riskImpact = riskImpact;
    }
}
