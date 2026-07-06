package com.erasm.dto;

import java.time.LocalDate;

public class AllocationDTO {

    private Long allocationId;

    private Integer allocationPercentage;

    private LocalDate allocationDate;

    private LocalDate releaseDate;

    private Long employeeId;

    private Long projectId;

   
    private Integer billableHours;

    private Integer totalHours;

    public AllocationDTO() {
    }

    public AllocationDTO(
            Long allocationId,
            Integer allocationPercentage,
            LocalDate allocationDate,
            LocalDate releaseDate,
            Long employeeId,
            Long projectId,
            Integer billableHours,
            Integer totalHours) {

        this.allocationId = allocationId;
        this.allocationPercentage = allocationPercentage;
        this.allocationDate = allocationDate;
        this.releaseDate = releaseDate;
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.billableHours = billableHours;
        this.totalHours = totalHours;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Integer getAllocationPercentage() {
        return allocationPercentage;
    }

    public void setAllocationPercentage(Integer allocationPercentage) {
        this.allocationPercentage = allocationPercentage;
    }

    public LocalDate getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(LocalDate allocationDate) {
        this.allocationDate = allocationDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getBillableHours() {
        return billableHours;
    }

    public void setBillableHours(Integer billableHours) {
        this.billableHours = billableHours;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }
}