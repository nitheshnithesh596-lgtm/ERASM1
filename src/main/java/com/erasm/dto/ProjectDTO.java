package com.erasm.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProjectDTO {

    private Long projectId;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String projectName;

    @NotBlank(message = "Client name is required")
    @Size(min = 2, max = 100, message = "Client name must be between 2 and 100 characters")
    private String clientName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date cannot be in the past")
    private LocalDate endDate;

    @NotBlank(message = "Technology stack is required")
    @Size(min = 2, max = 200, message = "Technology stack must be between 2 and 200 characters")
    private String technologyStack;

    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be greater than zero")
    private Double budget;

    @NotBlank(message = "Project status is required")
    private String status;

    public ProjectDTO() {
    }

    public ProjectDTO(Long projectId,
                      String projectName,
                      String clientName,
                      LocalDate startDate,
                      LocalDate endDate,
                      String technologyStack,
                      Double budget,
                      String status) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.technologyStack = technologyStack;
        this.budget = budget;
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTechnologyStack() {
        return technologyStack;
    }

    public void setTechnologyStack(String technologyStack) {
        this.technologyStack = technologyStack;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}