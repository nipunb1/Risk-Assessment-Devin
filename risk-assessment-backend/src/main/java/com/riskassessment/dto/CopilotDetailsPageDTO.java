package com.riskassessment.dto;

import java.util.List;

public class CopilotDetailsPageDTO {
    private List<CopilotDetailsDTO> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;

    public CopilotDetailsPageDTO() {}

    public CopilotDetailsPageDTO(List<CopilotDetailsDTO> content, int totalPages, long totalElements, 
                               int currentPage, int pageSize, boolean hasNext, boolean hasPrevious) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public List<CopilotDetailsDTO> getContent() {
        return content;
    }

    public void setContent(List<CopilotDetailsDTO> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
