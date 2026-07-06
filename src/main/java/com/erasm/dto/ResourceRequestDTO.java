package com.erasm.dto;

public class ResourceRequestDTO {

    private Long requestId;
    private String requiredSkill;
    private Integer requiredCount;
    private String status;
    private Long projectId;

    public ResourceRequestDTO() {
    }

    public ResourceRequestDTO(Long requestId, String requiredSkill,
            Integer requiredCount, String status, Long projectId) {

        this.requestId = requestId;
        this.requiredSkill = requiredSkill;
        this.requiredCount = requiredCount;
        this.status = status;
        this.projectId = projectId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(String requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public Integer getRequiredCount() {
        return requiredCount;
    }

    public void setRequiredCount(Integer requiredCount) {
        this.requiredCount = requiredCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}