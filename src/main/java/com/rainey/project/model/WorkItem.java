package com.rainey.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="WD_WorkItem")
public class WorkItem {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "workItem")
	private String workItem;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorkItem() {
		return workItem;
	}
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}

	@Override
	public String toString() {
		return "WorkItem [id=" + id + ", workItem=" + workItem + "]";
	}
}
