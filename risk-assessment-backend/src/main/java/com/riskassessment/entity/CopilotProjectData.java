package com.riskassessment.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "copilot_project_data")
public class CopilotProjectData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long entryId;
    
    @NotBlank
    @Column(name = "project_name")
    private String projectName;
    
    @NotBlank
    @Column(name = "lob_name")
    private String lobName;
    
    @NotBlank
    @Column(name = "lob_minus_one")
    private String lobMinusOne;
    
    @Column(name = "release_name")
    private String releaseName;
    
    @NotNull
    @Column(name = "release_or_month_effort", precision = 10, scale = 2)
    private BigDecimal releaseOrMonthEffort;
    
    @NotBlank
    @Column(name = "reporting_month", length = 3)
    private String reportingMonth;
    
    @NotNull
    @Column(name = "available_licenses")
    private Integer availableLicenses;
    
    @NotNull
    @Column(name = "actively_used_licenses")
    private Integer activelyUsedLicenses;
    
    @NotNull
    @Column(name = "project_headcount")
    private Integer projectHeadcount;
    
    @NotNull
    @Column(name = "engineering_population_count")
    private Integer engineeringPopulationCount;
    
    @NotNull
    @Column(name = "license_usage", precision = 5, scale = 2)
    private BigDecimal licenseUsage;
    
    @NotNull
    @Column(name = "acutual_license_usage", precision = 5, scale = 2)
    private BigDecimal actualLicenseUsage;
    
    @NotNull
    @Column(name = "applicable_efficiency", precision = 5, scale = 2)
    private BigDecimal applicableEfficiency;
    
    @NotNull
    @Column(name = "total_efforts_saved", precision = 10, scale = 2)
    private BigDecimal totalEffortsSaved;
    
    @NotNull
    @Column(name = "total_estimated_efforts", precision = 10, scale = 2)
    private BigDecimal totalEstimatedEfforts;
    
    @NotBlank
    @Column(name = "record_status")
    private String recordStatus;
    
    public CopilotProjectData() {}
    
    public Long getEntryId() {
        return entryId;
    }
    
    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getLobName() {
        return lobName;
    }
    
    public void setLobName(String lobName) {
        this.lobName = lobName;
    }
    
    public String getLobMinusOne() {
        return lobMinusOne;
    }
    
    public void setLobMinusOne(String lobMinusOne) {
        this.lobMinusOne = lobMinusOne;
    }
    
    public String getReleaseName() {
        return releaseName;
    }
    
    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }
    
    public BigDecimal getReleaseOrMonthEffort() {
        return releaseOrMonthEffort;
    }
    
    public void setReleaseOrMonthEffort(BigDecimal releaseOrMonthEffort) {
        this.releaseOrMonthEffort = releaseOrMonthEffort;
    }
    
    public String getReportingMonth() {
        return reportingMonth;
    }
    
    public void setReportingMonth(String reportingMonth) {
        this.reportingMonth = reportingMonth;
    }
    
    public Integer getAvailableLicenses() {
        return availableLicenses;
    }
    
    public void setAvailableLicenses(Integer availableLicenses) {
        this.availableLicenses = availableLicenses;
    }
    
    public Integer getActivelyUsedLicenses() {
        return activelyUsedLicenses;
    }
    
    public void setActivelyUsedLicenses(Integer activelyUsedLicenses) {
        this.activelyUsedLicenses = activelyUsedLicenses;
    }
    
    public Integer getProjectHeadcount() {
        return projectHeadcount;
    }
    
    public void setProjectHeadcount(Integer projectHeadcount) {
        this.projectHeadcount = projectHeadcount;
    }
    
    public Integer getEngineeringPopulationCount() {
        return engineeringPopulationCount;
    }
    
    public void setEngineeringPopulationCount(Integer engineeringPopulationCount) {
        this.engineeringPopulationCount = engineeringPopulationCount;
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
    
    public BigDecimal getTotalEffortsSaved() {
        return totalEffortsSaved;
    }
    
    public void setTotalEffortsSaved(BigDecimal totalEffortsSaved) {
        this.totalEffortsSaved = totalEffortsSaved;
    }
    
    public BigDecimal getTotalEstimatedEfforts() {
        return totalEstimatedEfforts;
    }
    
    public void setTotalEstimatedEfforts(BigDecimal totalEstimatedEfforts) {
        this.totalEstimatedEfforts = totalEstimatedEfforts;
    }
    
    public String getRecordStatus() {
        return recordStatus;
    }
    
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
