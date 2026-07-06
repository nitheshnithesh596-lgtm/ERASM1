package com.erasm.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="resource_requests")
public class ResourceRequest {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long requestId;

    private String requiredSkill;

    private Integer requiredCount;

    private String status;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    public ResourceRequest() {
    }

	public ResourceRequest(Long requestId, String requiredSkill, Integer requiredCount, String status,
			Project project) {
		super();
		this.requestId = requestId;
		this.requiredSkill = requiredSkill;
		this.requiredCount = requiredCount;
		this.status = status;
		this.project = project;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
