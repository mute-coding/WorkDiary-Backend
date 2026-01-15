package com.rainey.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WD_ProjectName", schema = "dbo")
public class ProjectName {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="project_name")
	private String projectName;
	
	public ProjectName() {}
	public ProjectName(Integer id,
					String projectName) {
		this.id = id;
		this.projectName = projectName;
		
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	@Override
	public String toString() {
		return "ProjectName [id=" + id + ", projectName=" + projectName + "]";
	}
	
	
	
}
