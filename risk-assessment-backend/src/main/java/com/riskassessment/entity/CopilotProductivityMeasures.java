package com.riskassessment.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "copilot_productivity_measures")
public class CopilotProductivityMeasures {
    
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
    
    @Column(name = "track_module_name")
    private String trackModuleName;
    
    @NotBlank
    @Column(name = "work_type")
    private String workType;
    
    @NotBlank
    @Column(name = "tech_stack")
    private String techStack;
    
    @NotBlank
    @Column(name = "assignee")
    private String assignee;
    
    @Column(name = "release_poc")
    private String releasePoc;
    
    @NotNull
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "jira_no")
    private String jiraNo;
    
    @NotNull
    @Column(name = "estimated_effort_without_copilot", precision = 10, scale = 2)
    private BigDecimal estimatedEffortWithoutCopilot;
    
    @NotNull
    @Column(name = "actual_effort_with_copilot", precision = 10, scale = 2)
    private BigDecimal actualEffortWithCopilot;
    
    @NotNull
    @Column(name = "difference_in_planned_actual", precision = 10, scale = 2)
    private BigDecimal differenceInPlannedActual;
    
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @NotBlank
    @Column(name = "reporting_year", length = 4)
    private String reportingYear;
    
    @NotBlank
    @Column(name = "reporting_month", length = 3)
    private String reportingMonth;
    
    @NotBlank
    @Column(name = "record_status")
    private String recordStatus;
    
    public CopilotProductivityMeasures() {}
    
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
    
    public String getTrackModuleName() {
        return trackModuleName;
    }
    
    public void setTrackModuleName(String trackModuleName) {
        this.trackModuleName = trackModuleName;
    }
    
    public String getWorkType() {
        return workType;
    }
    
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
    public String getTechStack() {
        return techStack;
    }
    
    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }
    
    public String getAssignee() {
        return assignee;
    }
    
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    
    public String getReleasePoc() {
        return releasePoc;
    }
    
    public void setReleasePoc(String releasePoc) {
        this.releasePoc = releasePoc;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getJiraNo() {
        return jiraNo;
    }
    
    public void setJiraNo(String jiraNo) {
        this.jiraNo = jiraNo;
    }
    
    public BigDecimal getEstimatedEffortWithoutCopilot() {
        return estimatedEffortWithoutCopilot;
    }
    
    public void setEstimatedEffortWithoutCopilot(BigDecimal estimatedEffortWithoutCopilot) {
        this.estimatedEffortWithoutCopilot = estimatedEffortWithoutCopilot;
    }
    
    public BigDecimal getActualEffortWithCopilot() {
        return actualEffortWithCopilot;
    }
    
    public void setActualEffortWithCopilot(BigDecimal actualEffortWithCopilot) {
        this.actualEffortWithCopilot = actualEffortWithCopilot;
    }
    
    public BigDecimal getDifferenceInPlannedActual() {
        return differenceInPlannedActual;
    }
    
    public void setDifferenceInPlannedActual(BigDecimal differenceInPlannedActual) {
        this.differenceInPlannedActual = differenceInPlannedActual;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getReportingYear() {
        return reportingYear;
    }
    
    public void setReportingYear(String reportingYear) {
        this.reportingYear = reportingYear;
    }
    
    public String getReportingMonth() {
        return reportingMonth;
    }
    
    public void setReportingMonth(String reportingMonth) {
        this.reportingMonth = reportingMonth;
    }
    
    public String getRecordStatus() {
        return recordStatus;
    }
    
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
