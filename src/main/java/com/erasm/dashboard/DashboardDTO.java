package com.erasm.dashboard;

public class DashboardDTO {

   
    private long totalEmployees;

   
    private long totalProjects;
    private long activeProjects;
    private long completedProjects;

   private long totalSkills;

   
    private long totalAllocations;

   
    private long totalCertifications;

  
    private long pendingResourceRequests;

    private int totalBillableHours;
    private int totalBenchHours;
    private int totalHours;

    private double billablePercentage;
    private double benchPercentage;

    public DashboardDTO() {
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public long getActiveProjects() {
        return activeProjects;
    }

    public void setActiveProjects(long activeProjects) {
        this.activeProjects = activeProjects;
    }

    public long getCompletedProjects() {
        return completedProjects;
    }

    public void setCompletedProjects(long completedProjects) {
        this.completedProjects = completedProjects;
    }

    public long getTotalSkills() {
        return totalSkills;
    }

    public void setTotalSkills(long totalSkills) {
        this.totalSkills = totalSkills;
    }

    public long getTotalAllocations() {
        return totalAllocations;
    }

    public void setTotalAllocations(long totalAllocations) {
        this.totalAllocations = totalAllocations;
    }

    public long getTotalCertifications() {
        return totalCertifications;
    }

    public void setTotalCertifications(long totalCertifications) {
        this.totalCertifications = totalCertifications;
    }

    public long getPendingResourceRequests() {
        return pendingResourceRequests;
    }

    public void setPendingResourceRequests(long pendingResourceRequests) {
        this.pendingResourceRequests = pendingResourceRequests;
    }

    public int getTotalBillableHours() {
        return totalBillableHours;
    }

    public void setTotalBillableHours(int totalBillableHours) {
        this.totalBillableHours = totalBillableHours;
    }

    public int getTotalBenchHours() {
        return totalBenchHours;
    }

    public void setTotalBenchHours(int totalBenchHours) {
        this.totalBenchHours = totalBenchHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public double getBillablePercentage() {
        return billablePercentage;
    }

    public void setBillablePercentage(double billablePercentage) {
        this.billablePercentage = billablePercentage;
    }

    public double getBenchPercentage() {
        return benchPercentage;
    }

    public void setBenchPercentage(double benchPercentage) {
        this.benchPercentage = benchPercentage;
    }
}