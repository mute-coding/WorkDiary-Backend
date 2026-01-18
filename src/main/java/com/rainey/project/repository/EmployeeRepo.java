package com.rainey.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rainey.project.model.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
	@Query("SELECT e.empDept From Employee e Where e.empName = :empName")
	String findDeptByName(@Param("empName") String empName);
	
	@Query("SELECT DISTINCT e.empDept From Employee e")
	List<String> findAllDept();
	
	
}
