package com.rainey.project.controller;

import java.awt.PageAttributes.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	/*
	@GetMapping("/employees/dept")
	public Map<String, String> findDeptByName(@RequestParam String EmpName) {
	    Map<String, String> result = new HashMap<>();
	    result.put("dept", unifiedService.findDeptByName(EmpName));
	    return result;
	}
	*/
	@GetMapping("/employees/dept")
	public ResponseEntity<Map<String, String>> findDeptByName(@RequestParam String EmpName){
	    Map<String, String> result = new HashMap<>();
	    result.put("dept", unifiedService.findDeptByName(EmpName));
	    return ResponseEntity.ok(result);
	}
	
	
	/*
	@GetMapping("/projectname/all")
	List<ProjectName> findAllProjectName(){
		return unifiedService.findAllProjectName();
	}
	*/
	@GetMapping("/projectname/all")
	public ResponseEntity<List<ProjectName>> findAllProjectName(){
		List<ProjectName> result =  unifiedService.findAllProjectName();
		return ResponseEntity.ok(result);
	}
	
	/*
	@GetMapping("/workitem/all")
	List<WorkItem> findAllWorkItem(){
		return unifiedService.findAllWorkItem();
	}
	*/
	@GetMapping("/workitem/all")
	public ResponseEntity<List<WorkItem>> findAllWorkItem(){
		List<WorkItem> result = unifiedService.findAllWorkItem();
		return ResponseEntity.ok(result);
	}
	/*
	@PostMapping("/save")
	public WorkDiary save(@RequestBody WorkDiary workDiary) {
		unifiedService.save(workDiary);
		return workDiary;
	}
	*/
	@PostMapping("/save")
	public ResponseEntity<WorkDiary> save(@RequestBody WorkDiary workDiary) {
		unifiedService.save(workDiary);
		return ResponseEntity.ok(workDiary);
	}
	/*
	@DeleteMapping("/delete/{id}")
	public String deleteDiaryList(@PathVariable Integer id) {
		unifiedService.deleteDiaryList(id);
		return "第"+id+"已刪除";
	}
	*/
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDiaryList(@PathVariable Integer id) {
	    unifiedService.deleteDiaryList(id);
	    return ResponseEntity.ok("第 " + id + " 已刪除");
	}
	/*
	@GetMapping("/all")
	public List<WorkDiary> findAllWorkDiaries(){
		return unifiedService.findAllWorkDiaries();
	}
	*/
	@GetMapping("/all")
	public ResponseEntity<List<WorkDiary>> findAllWorkDiaries(){
		List<WorkDiary> result = unifiedService.findAllWorkDiaries();
		return ResponseEntity.ok(result);
	}
	
	
	
	/*
	@GetMapping("/employees/all")
	public List<Employee> findAllEmpolyees(){
		return unifiedService.findAllEmpolyees();
	}
	*/
	@GetMapping("/employees/all")
	public ResponseEntity<List<Employee>> findAllEmpolyees(){
		List<Employee> result =  unifiedService.findAllEmpolyees();
		return ResponseEntity.ok(result);
	}
	/*
	 @PatchMapping("/update/{id}")
	 public WorkDiary update(@PathVariable Integer id,@RequestBody Map<String, Object> updateFields) {
	        WorkDiary updatedWorkDiary = unifiedService.update(id, updateFields);
	        return updatedWorkDiary ;
	 }
	 */
	@PatchMapping("/update/{id}")
	public ResponseEntity<WorkDiary> update(@PathVariable Integer id,@RequestBody Map<String, Object> updateFields){
        WorkDiary updatedWorkDiary = unifiedService.update(id, updateFields);
        return ResponseEntity.ok(updatedWorkDiary);
	}
	 
	 
	 /*
	 @PostMapping("/employees/create")
	 public Employee createNewEmpolyee(@RequestBody Employee empolyee) {
		 return unifiedService.createNewEmpolyee(empolyee);
	 }
	 */
	@PostMapping("/employees/create")
	public ResponseEntity<Employee> createNewEmpolyee(@RequestBody Employee employee) {
	    Employee savedEmployee = unifiedService.createNewEmpolyee(employee);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	}
			
	 /*
	 @PostMapping("/projectname/create")
	 public ProjectName createNewProjectName(@RequestBody ProjectName projectName) {
		 return unifiedService.createNewProjectName(projectName);
	 }
	 */
	@PostMapping("/projectname/create")
	public ResponseEntity<ProjectName> createNewProjectName(@RequestBody ProjectName projectName) {
		ProjectName savedProjectname =  unifiedService.createNewProjectName(projectName);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectname);
	}
	 
	 /*
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
	    */
		public String now() {
		    LocalDateTime now = LocalDateTime.now();
		    return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HHmmss"));
		}
	
		@GetMapping("/download")
		public ResponseEntity<Void> downloadExcel(HttpServletResponse response) throws Exception {
	
		    Workbook workbook = generateExcel.workDiaryExcel();
	
		    response.setContentType(
		            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    response.setHeader(
		            "Content-Disposition",
		            "attachment; filename=WorkDiary"+now()+".xlsx");
	
		    workbook.write(response.getOutputStream());
		    workbook.close();
	
		    return ResponseEntity.ok().build();
		}
	
	
	 /*
	 @GetMapping("/sendmail")
	 public String sendMail() throws IOException,MessagingException {
		 sendMail.sendMail();
		 return "發送成功";
	 }
	 */
	 @GetMapping("/sendmail")
	 public ResponseEntity<String> sendMail() throws IOException, MessagingException {
	     sendMail.sendMail();
	     return ResponseEntity.ok("發送成功");
	 }
	 /*
	 @DeleteMapping("/delete")
	 public String deleteAllDiariesList() {
		 unifiedService.deleteAllDiaryList();
		 return "刪除完畢";
	 }
	 */
	 @DeleteMapping("/delete")
	 public ResponseEntity<String> deleteAllDiariesList() {
	     unifiedService.deleteAllDiaryList();
	     return ResponseEntity.ok("刪除完畢");
	 }
	 
	 /*
	 @GetMapping("/employees/allDept")
	 public List<String> findAllDept(){
		 return unifiedService.findAllDept();
	 }
	 */
	 @GetMapping("/employees/allDept")
	 public ResponseEntity<List<String>> findAllDept(){
		  List<String> result = unifiedService.findAllDept();
		  return ResponseEntity.ok(result);
	 }
	  
}
