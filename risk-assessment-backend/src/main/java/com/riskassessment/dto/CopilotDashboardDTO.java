package com.riskassessment.dto;

import java.math.BigDecimal;

public class CopilotDashboardDTO {
    
    private String lob;
    private BigDecimal licenseUsage;
    private BigDecimal actualLicenseUsage;
    private BigDecimal applicableEfficiency;
    private Long totalProjects;
    
    public CopilotDashboardDTO() {}
    
    public CopilotDashboardDTO(String lob, BigDecimal licenseUsage, BigDecimal actualLicenseUsage, 
                              BigDecimal applicableEfficiency, Long totalProjects) {
        this.lob = lob;
        this.licenseUsage = licenseUsage;
        this.actualLicenseUsage = actualLicenseUsage;
        this.applicableEfficiency = applicableEfficiency;
        this.totalProjects = totalProjects;
    }
    
    public String getLob() {
        return lob;
    }
    
    public void setLob(String lob) {
        this.lob = lob;
    }
    
    public BigDecimal getLicenseUsage() {
        return licenseUsage;
    }
    
    public void setLicenseUsage(BigDecimal licenseUsage) {
        this.licenseUsage = licenseUsage;
    }
    
    public BigDecimal getActualLicenseUsage() {
        return actualLicenseUsage;
    }
    
    public void setActualLicenseUsage(BigDecimal actualLicenseUsage) {
        this.actualLicenseUsage = actualLicenseUsage;
    }
    
    public BigDecimal getApplicableEfficiency() {
        return applicableEfficiency;
    }
    
    public void setApplicableEfficiency(BigDecimal applicableEfficiency) {
        this.applicableEfficiency = applicableEfficiency;
    }
    
    public Long getTotalProjects() {
        return totalProjects;
    }
    
    public void setTotalProjects(Long totalProjects) {
        this.totalProjects = totalProjects;
    }
}
