package com.rainey.project.service;

import java.util.List;
import java.util.Map;

import com.rainey.project.model.Employee;
import com.rainey.project.model.ProjectName;
import com.rainey.project.model.WorkDiary;
import com.rainey.project.model.WorkItem;

public interface IUnifiedService {
	String findDeptByName(String empName);
	List<ProjectName> findAllProjectName();
	List<WorkItem> findAllWorkItem();
	List<WorkDiary> findAllWorkDiaries();
	List<Employee> findAllEmpolyees();
	WorkDiary save(WorkDiary workDiary);
	WorkDiary update(Integer id,Map<String, Object>updateFields);
	void deleteDiaryList(Integer id);
	void deleteAllDiaryList();
	Employee createNewEmpolyee(Employee empolyee);
	ProjectName createNewProjectName(ProjectName projectName);
	
	
}
