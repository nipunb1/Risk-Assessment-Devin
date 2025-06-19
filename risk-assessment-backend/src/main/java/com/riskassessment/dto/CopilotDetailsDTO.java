package com.riskassessment.dto;

public class CopilotDetailsDTO {
    private String lob;
    private String lobMinusOne;
    private String projectName;
    private String reportingMonth;
    private Integer projectHeadcount;
    private Double engineeringPopulationPercent;
    private Double licenseUsage;
    private Double actualLicenseUsage;
    private Double applicableEfficiency;

    public CopilotDetailsDTO() {}

    public CopilotDetailsDTO(String lob, String lobMinusOne, String projectName, String reportingMonth, 
                           Integer projectHeadcount, Integer engineeringPopulationCount, Integer availableLicenses,
                           Double licenseUsage, Double actualLicenseUsage, Double applicableEfficiency) {
        this.lob = lob;
        this.lobMinusOne = lobMinusOne;
        this.projectName = projectName;
        this.reportingMonth = reportingMonth;
        this.projectHeadcount = projectHeadcount;
        this.engineeringPopulationPercent = availableLicenses > 0 ? 
            (engineeringPopulationCount.doubleValue() / availableLicenses.doubleValue()) * 100 : 0.0;
        this.licenseUsage = licenseUsage;
        this.actualLicenseUsage = actualLicenseUsage;
        this.applicableEfficiency = applicableEfficiency;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getLobMinusOne() {
        return lobMinusOne;
    }

    public void setLobMinusOne(String lobMinusOne) {
        this.lobMinusOne = lobMinusOne;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReportingMonth() {
        return reportingMonth;
    }

    public void setReportingMonth(String reportingMonth) {
        this.reportingMonth = reportingMonth;
    }

    public Integer getProjectHeadcount() {
        return projectHeadcount;
    }

    public void setProjectHeadcount(Integer projectHeadcount) {
        this.projectHeadcount = projectHeadcount;
    }

    public Double getEngineeringPopulationPercent() {
        return engineeringPopulationPercent;
    }

    public void setEngineeringPopulationPercent(Double engineeringPopulationPercent) {
        this.engineeringPopulationPercent = engineeringPopulationPercent;
    }

    public Double getLicenseUsage() {
        return licenseUsage;
    }

    public void setLicenseUsage(Double licenseUsage) {
        this.licenseUsage = licenseUsage;
    }

    public Double getActualLicenseUsage() {
        return actualLicenseUsage;
    }

    public void setActualLicenseUsage(Double actualLicenseUsage) {
        this.actualLicenseUsage = actualLicenseUsage;
    }

    public Double getApplicableEfficiency() {
        return applicableEfficiency;
    }

    public void setApplicableEfficiency(Double applicableEfficiency) {
        this.applicableEfficiency = applicableEfficiency;
    }
}
