package com.rainey.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainey.project.excel.GenerateExcel;
import com.rainey.project.mail.SendMail;
import com.rainey.project.model.Employee;
import com.rainey.project.model.ProjectName;
import com.rainey.project.model.WorkDiary;
import com.rainey.project.model.WorkItem;
import com.rainey.project.repository.EmployeeRepo;
import com.rainey.project.repository.ProjectNameRepo;
import com.rainey.project.service.UnifiedService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/workdiary")
public class Controller {
	@Autowired
	private UnifiedService unifiedService;
	@Autowired
    private  GenerateExcel generateExcel;
	@Autowired
	private SendMail sendMail;

	
	@GetMapping("/employees/dept")
	public Map<String, String> findDeptByName(@RequestParam String EmpName) {
	    Map<String, String> result = new HashMap<>();
	    result.put("dept", unifiedService.findDeptByName(EmpName));
	    return result;
	}
	@GetMapping("/projectname/all")
	List<ProjectName> findAllProjectName(){
		return unifiedService.findAllProjectName();
	}
	@GetMapping("/workitem/all")
	List<WorkItem> findAllWorkItem(){
		return unifiedService.findAllWorkItem();
	}
	@PostMapping("/save")
	public WorkDiary save(@RequestBody WorkDiary workDiary) {
		unifiedService.save(workDiary);
		return workDiary;
	}
	@DeleteMapping("/delete/{id}")
	public String deleteDiaryList(@PathVariable Integer id) {
		unifiedService.deleteDiaryList(id);
		return "第"+id+"已刪除";
	}
	@GetMapping("/all")
	public List<WorkDiary> findAllWorkDiaries(){
		return unifiedService.findAllWorkDiaries();
	}
	@GetMapping("/employees/all")
	public List<Employee> findAllEmpolyees(){
		return unifiedService.findAllEmpolyees();
	}
	 @PatchMapping("/update/{id}")
	 public WorkDiary update(@PathVariable Integer id,@RequestBody Map<String, Object> updateFields) {
	        WorkDiary updatedWorkDiary = unifiedService.update(id, updateFields);
	        return updatedWorkDiary ;
	 }
	 @PostMapping("/employees/create")
	 public Employee createNewEmpolyee(@RequestBody Employee empolyee) {
		 return unifiedService.createNewEmpolyee(empolyee);
	 }
	 @PostMapping("/projectname/create")
	 public ProjectName createNewProjectName(@RequestBody ProjectName projectName) {
		 return unifiedService.createNewProjectName(projectName);
	 }
	 
	 @GetMapping("/download")
	 public void downloadExcel(HttpServletResponse response) throws Exception {

	        Workbook workbook = generateExcel.workDiaryExcel();

	        response.setContentType(
	                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader(
	                "Content-Disposition",
	                "attachment; filename=WorkDiary.xlsx");

	        workbook.write(response.getOutputStream());
	        workbook.close();
	    }
	 @GetMapping("/sendmail")
	 public String sendMail() throws IOException,MessagingException {
		 sendMail.sendMail();
		 return "發送成功";
	 }
	 @DeleteMapping("/delete")
	 public String deleteAllDiariesList() {
		 unifiedService.deleteAllDiaryList();
		 return "刪除完畢";
	 }

	  
}
