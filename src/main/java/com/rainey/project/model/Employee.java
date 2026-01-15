package com.rainey.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="WD_Employee")
public class Employee {
	@Column(name = "emp_name")
	private String empName;
	@Id
	@Column(name = "emp_id")
	private String empId;
	@Column(name = "emp_dept")
	private String empDept;
	
	public Employee() {}
	public Employee(String empName,
					String empId,
					String empDept) {
		this.empName = empName;
		this.empId = empId;
		this.empDept = empDept;
	}
	public String getEmpId() { return empId; }
	public void setEmpId(String empId) { this.empId = empId; }

	public String getEmpName() { return empName; }
	public void setEmpName(String empName) { this.empName = empName; }

	public String getEmpDept() { return empDept; }
	public void setEmpDept(String empDept) { this.empDept = empDept; }
	@Override
	public String toString() {
		return "Empolyee [emp_name=" + empName + ", emp_id=" + empId + ", emp_dept=" + empDept + "]";
	}
	
	
}
