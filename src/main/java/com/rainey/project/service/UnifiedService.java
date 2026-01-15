package com.rainey.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rainey.project.model.Employee;
import com.rainey.project.model.ProjectName;
import com.rainey.project.model.WorkDiary;

import com.rainey.project.model.WorkItem;
import com.rainey.project.repository.EmployeeRepo;
import com.rainey.project.repository.ProjectNameRepo;
import com.rainey.project.repository.WorkDiaryRepo;
import com.rainey.project.repository.WorkItemRepo;

import jakarta.transaction.Transactional;
//import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class UnifiedService implements IUnifiedService {
	@Autowired
	private EmployeeRepo empolyeeRepo;
	@Autowired
	private ProjectNameRepo projectNameRepo;
	@Autowired
	private WorkDiaryRepo workDiaryRepo;
	@Autowired
	private WorkItemRepo workItemRepo;
	@Autowired
	private ObjectMapper objectMapper;
	

	@Override
	public String findDeptByName(String empName) {
		// TODO Auto-generated method stub
		return empolyeeRepo.findDeptByName(empName);
	}
	@Override
	public List<ProjectName> findAllProjectName() {
		// TODO Auto-generated method stub
		return projectNameRepo.findAll();
	}
	@Override
	public WorkDiary save(WorkDiary workDiary) {
		
		  workDiary.setCreateTime(LocalDateTime.now());
		    WorkDiary saved = workDiaryRepo.save(workDiary);
		    // 5. 再存一次更新 record_id
		    return workDiaryRepo.save(saved);
	}
	@Override
	public void deleteDiaryList(Integer id) {
		// TODO Auto-generated method stub
		workDiaryRepo.deleteById(id);
	}
	@Override
	public List<WorkItem> findAllWorkItem() {
		// TODO Auto-generated method stub
		return workItemRepo.findAll();
	}
	
	@Override
	public List<WorkDiary> findAllWorkDiaries() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

	    return workDiaryRepo.findAll()
	            .stream()
	            .sorted(Comparator.comparing(wd ->
	                LocalDate.parse(wd.getDate(), formatter)
	            ))
	            .collect(Collectors.toList());
	}
	
	@Override
	public List<Employee> findAllEmpolyees() {
		// TODO Auto-generated method stub
		return empolyeeRepo.findAll();
	}
	@Override
	public WorkDiary update(Integer id, Map<String, Object> updateFields) {
		// TODO Auto-generated method stub
        Optional<WorkDiary> existingWorkDiaryOpt = workDiaryRepo.findById(id);

        if (!existingWorkDiaryOpt.isPresent()) {
            return null;
        }

        WorkDiary existingWorkDiary = existingWorkDiaryOpt.get();

    
        WorkDiary updatedWorkDiary = objectMapper.updateValue(existingWorkDiary, updateFields);
        return workDiaryRepo.save(updatedWorkDiary);
    }
	@Override
	public Employee createNewEmpolyee(Employee empolyee) {
		// TODO Auto-generated method stub
		return empolyeeRepo.save(empolyee);
	}
	@Override
	public ProjectName createNewProjectName(ProjectName projectName) {
		// TODO Auto-generated method stub
		return projectNameRepo.save(projectName);
	}
	@Override
	public void deleteAllDiaryList() {
		// TODO Auto-generated method stub
		workDiaryRepo.deleteAll();
	}
}
