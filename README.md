# 工作日誌應用程式-後端程式碼
### 專案所使用的後端技術
- spring boot
- hibernate
### 資料庫
- SQL SERVER
## 專案內容
### 資料表
- WD_DiaryRepository(存放工作日誌內容)
``` SQL
id int primary key
year nvarcher(60)
week nvarcher(40)
month nvarcher(15)
date nvarcher(80)
init_dept nvarcher(40)
init_by nvarcher(20)
item_describe nvarcher(180)
dev_minute nvarcher(60)
support_minute nvarcher(60)
system_minute nvarcher(60)
create_time datetime
```
- WD_Employee(存放員工資料，從EXCEL匯入)
``` SQL
emp_name nvarcher(50)
emp_id nvarcher(50) primary key
emp_dept nvarcher(50)
```
- WD_ProjectName(存放專案名稱)
``` SQL
id int primary key
project_name nvarcher(50)
```
- WD_WorkItem(存放工作類別)
``` SQL
id int primary key
workitem nvarcher(20)
```
### spring boot程式碼

#### Model層
- WorkDiary.java
``` java
@Entity
@Table(name = "WD_DiaryRepository")

public class WorkDiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	//年份
	@Column(name = "year")
	private String year;
	//第幾周
	@Column(name = "week")
	private String week;
	//月份
	@Column(name = "month")
	private String month;
	@Column(name = "date")
	private String date;
	//發起單位
	@Column(name = "init_dept")
	private String initDept;
	//發起人員
	@Column(name = "init_by")
	private String initBy;
	//項目名稱
	@Column(name = "workitem")
	private String workitem;
	//工作事項(需求)描述
	@Column(name="item_describe")
	private String itemDescribe;
	//開發類工時(分)
	@Column(name = "dev_minute")
	private String devMinute;
	//服務類工時(分)
	@Column(name = "support_minute")
	private String supportMinute;
	//系統類工時(分)
	@Column(name = "system_minute")
	private String systemMinute;
	@Column(name = "create_time")
	private LocalDateTime createTime;

  constructor、 getter、setter......
}
```
- Employee.java
``` java
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
  constructor、 getter、setter......
}
```
- ProjectName.java
``` java
@Entity
@Table(name = "WD_ProjectName", schema = "dbo")
public class ProjectName {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="project_name")
	private String projectName;
constructor、 getter、setter......
}
```
- WorkItem.java
``` java
@Entity
@Table(name="WD_WorkItem")
public class WorkItem {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "workItem")
	private String workItem;
constructor、 getter、setter......
}
```
#### Repository層
- WorkDiaryRepo.java
``` java
@Repository
public interface WorkDiaryRepo extends JpaRepository<WorkDiary, Integer> {

}
```
- EmployeeRepo.java
``` java
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
  //用員工名稱找對應的員工部門
	@Query("SELECT e.empDept From Employee e Where e.empName = :empName")
	String findDeptByName(@Param("empName") String empName);
	//select 所有部門
	@Query("SELECT DISTINCT e.empDept From Employee e")
	List<String> findAllDept();
	
}
```
- ProjectNameRepo.java
``` java
@Repository
public interface ProjectNameRepo extends JpaRepository<ProjectName, Integer> {

}
```
- WorkItemRepo.java
``` java
@Repository
public interface WorkItemRepo extends JpaRepository<ProjectName, Integer> {

}
```
#### Service層
- IUnifiedService.java
``` java
public interface IUnifiedService {
  //找尋員工部門，參數為員工名稱
	String findDeptByName(String empName);
  //列出所有專案名稱
	List<ProjectName> findAllProjectName();
  //列出所有工作分類
	List<WorkItem> findAllWorkItem();
  //列出所有工作項目
	List<WorkDiary> findAllWorkDiaries();
  //列出所有員工名稱
	List<Employee> findAllEmpolyees();
  //列出所有部門
	List<String> findAllDept();
  //新增工作項目
	WorkDiary save(WorkDiary workDiary);
  //更新工作項目
	WorkDiary update(Integer id,Map<String, Object>updateFields);
  //刪除工作項目，參數為工作項目id
	void deleteDiaryList(Integer id);
  //刪除所有工作項目
	void deleteAllDiaryList();
  //創建新的員工資料
	Employee createNewEmpolyee(Employee empolyee);
  //創建新的專案名稱
	ProjectName createNewProjectName(ProjectName projectName);
	
}
```
- UnifiedService.java
-- 依賴注入(DI)
```.java
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
```
-- findDeptByName
``` java
@Override
	public String findDeptByName(String empName) {
		// TODO Auto-generated method stub
		return empolyeeRepo.findDeptByName(empName);
	}
```
-- findAllProjectName
``` java
@Override
	public List<ProjectName> findAllProjectName() {
		// TODO Auto-generated method stub
		return projectNameRepo.findAll();
	}
```
-- save
``` java
@Override
	public WorkDiary save(WorkDiary workDiary) {
		
		  workDiary.setCreateTime(LocalDateTime.now());
		  WorkDiary saved = workDiaryRepo.save(workDiary);
		  return workDiaryRepo.save(saved);
	}
```
-- deleteDiaryList
``` java
	@Override
	public void deleteDiaryList(Integer id) {
		// TODO Auto-generated method stub
		workDiaryRepo.deleteById(id);
	}
```
-- findAllWorkItem
``` java
	@Override
	public List<WorkItem> findAllWorkItem() {
		// TODO Auto-generated method stub
		return workItemRepo.findAll();
	}
```
-- findAllWorkDiaries
``` java
	@Override
	public List<WorkDiary> findAllWorkDiaries() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
      //使用stream，利用WorkDiary的date做排序
	    return workDiaryRepo.findAll()
	            .stream()
	            .sorted(Comparator.comparing(wd ->
	                LocalDate.parse(wd.getDate(), formatter)
	            ))
	            .collect(Collectors.toList());
	}
```
-- findAllEmpolyees
``` java
	@Override
	public List<Employee> findAllEmpolyees() {
		// TODO Auto-generated method stub
		return empolyeeRepo.findAll();
	}
```
-- update
``` java
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
```
-- findAllEmpolyees
``` java
	@Override
	public Employee createNewEmpolyee(Employee empolyee) {
		// TODO Auto-generated method stub
		return empolyeeRepo.save(empolyee);
	}
```
-- createNewProjectName
``` java
	@Override
	public ProjectName createNewProjectName(ProjectName projectName) {
		// TODO Auto-generated method stub
		return projectNameRepo.save(projectName);
	}
```
-- deleteAllDiaryList
``` java
	@Override
	public void deleteAllDiaryList() {
		// TODO Auto-generated method stub
		workDiaryRepo.deleteAll();
	}
```
-- findAllDept
``` java
	@Override
	public List<String> findAllDept() {
		// TODO Auto-generated method stub
		return empolyeeRepo.findAllDept();
	}
```
#### 生成EXCEL功能
``` java
@Component
public class GenerateExcel {
	
    @Autowired
	private UnifiedService unifiedService;

    public Workbook workDiaryExcel()  {
    	List<WorkDiary> workDiaries = unifiedService.findAllWorkDiaries(); 
    	Workbook workbook = new XSSFWorkbook();
    	
    	Map<String, CellStyle> styles = createStyle(workbook);
    	
    	Sheet sheet = workbook.createSheet("工作日誌");
    	setColumnWidths(sheet);
    	int setRowNum = 0;
    	
    	for(WorkDiary item : workDiaries) {
    		
    		//算出基偶數列
            boolean isEvenRow = (setRowNum + 1) % 2 != 0;
            
           
            Row row = sheet.createRow(setRowNum++);
            
            Cell yearCell = row.createCell(0);
            //yearCell.setCellValue(Integer.parseInt(item.getYear()));
            yearCell.setCellFormula("YEAR(D"+setRowNum+")");
            if(isEvenRow) {
                yearCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	yearCell.setCellStyle(styles.get("blueBGStyle"));
            }
            
            
        	Cell weekCell = row.createCell(1);
        	//weekCell.setCellValue(Integer.parseInt(item.getWeek()));
        	weekCell.setCellFormula("WEEKNUM(D"+setRowNum+")");
            if(isEvenRow) {
            	weekCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	weekCell.setCellStyle(styles.get("blueBGStyle"));
            }


        	Cell monthCell = row.createCell(2);
        	//monthCell.setCellValue(Integer.parseInt(item.getMonth()));
        	monthCell.setCellFormula("MONTH(D"+setRowNum+")");
        	if(isEvenRow) {
            	monthCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	monthCell.setCellStyle(styles.get("blueBGStyle"));
            }


        	Cell dateCell = row.createCell(3);
        	dateCell.setCellValue(String.valueOf(item.getDate()));
        	
            if(isEvenRow) {
            	dateCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	dateCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	

        	Cell initDeptCell = row.createCell(4);
        	initDeptCell.setCellValue(String.valueOf(item.getInitDept()));
        	initDeptCell.setCellFormula("IF(ISNA(F6VLOOKUP(F"+setRowNum+",部門員工!$A:$E,3,0)),\"\",VLOOKUP(F"+setRowNum+",部門員工!$A:$E,3,0))");
        	 if(isEvenRow) {
        		 initDeptCell.setCellStyle(styles.get("whiteBGStyle"));
             }else {
            	 initDeptCell.setCellStyle(styles.get("blueBGStyle"));
             }
        	
        	
        	Cell initByCell = row.createCell(5);
        	initByCell.setCellValue(String.valueOf(item.getInitBy()));
        	
         	 if(isEvenRow) {
         		initByCell.setCellStyle(styles.get("whiteBGStyle"));
             }else {
            	initByCell.setCellStyle(styles.get("blueBGStyle"));
             }
        	
        	
        	Cell workitemCell = row.createCell(6);
        	workitemCell.setCellValue(String.valueOf(item.getWorkitem()));
        	
        	
        	if(isEvenRow) {
        		workitemCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	workitemCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
        	
        	
        	Cell itemDescribeCell = row.createCell(7);
        	itemDescribeCell.setCellValue(String.valueOf(item.getItemDescribe()));
        	
        	
        	if(isEvenRow) {
        		itemDescribeCell.setCellStyle(styles.get("itemDescribeWhiteBGStyle"));
            }else {
            	itemDescribeCell.setCellStyle(styles.get("itemDescribeBlueBGStyle"));
            }
        	
        	
        	
        	Cell devMinuteCell = row.createCell(8);
        	devMinuteCell.setCellValue(String.valueOf(item.getDevMinute()));
        	
          	if(isEvenRow) {
          		devMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	devMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
        	
        	
        	Cell supportMinuteCell = row.createCell(9);
        	supportMinuteCell.setCellValue(String.valueOf(item.getSupportMinute()));
        	
          	if(isEvenRow) {
          		supportMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	supportMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }

        	Cell systemMinuteCell = row.createCell(10);
        	systemMinuteCell.setCellValue(String.valueOf(item.getSystemMinute()));
        	
        	
          	if(isEvenRow) {
          		systemMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	systemMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
    	}

    	return workbook;
    }
    createStyle、setColumnWidths、workbookToByte......
}
```
#### 寄送Excel至Gmail功能
``` java
@Component
public class SendMail {
	@Autowired
	private GenerateExcel generateExcel;
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String now() {
	    LocalDateTime now = LocalDateTime.now();
	    return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HHmmss"));
	}
    public void sendMail() throws IOException, MessagingException {
    	
    	byte[] excelResource = generateExcel.workbookToByte();
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	MimeMessageHelper mimeMessageHelper = 
    			new MimeMessageHelper(mimeMessage,true,"UTF-8");
    	
    	mimeMessageHelper.setFrom(電子郵件帳號);
    	mimeMessageHelper.setTo(電子郵件帳號);
    	mimeMessageHelper.setSubject("工作日誌");
    	mimeMessageHelper.setText("本周工作日誌", true);

    	mimeMessageHelper.addAttachment(
                "工作日誌"+now()+".xlsx",
                new ByteArrayResource(excelResource)
        );

    	javaMailSender.send(mimeMessage);
    }
}
```
#### 處理Cors跨域安全
``` java
@Configuration
public class Webconfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500",
                		 "https://mute-coding.github.io", // GitHub Page
                         "https://phylis-nonpresentational-gussie.ngrok-free.dev ") // ngrok)
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH")
                .allowedHeaders("*");
    }
}
```
#### Controller層
- 依賴注入(DI)
``` java
@RestController
@RequestMapping("/workdiary")
public class Controller {
	@Autowired
	private UnifiedService unifiedService;
	@Autowired
    private  GenerateExcel generateExcel;
	@Autowired
	private SendMail sendMail;
```
- findDeptByName
``` java
	@GetMapping("/employees/dept")
	public ResponseEntity<Map<String, String>> findDeptByName(@RequestParam String EmpName){
	    Map<String, String> result = new HashMap<>();
	    result.put("dept", unifiedService.findDeptByName(EmpName));
	    return ResponseEntity.ok(result);
	}
```
- findDeptByName
``` java
	@GetMapping("/employees/dept")
	public ResponseEntity<Map<String, String>> findDeptByName(@RequestParam String EmpName){
	    Map<String, String> result = new HashMap<>();
	    result.put("dept", unifiedService.findDeptByName(EmpName));
	    return ResponseEntity.ok(result);
	}
```
- findAllProjectName
``` java
	@GetMapping("/projectname/all")
	public ResponseEntity<List<ProjectName>> findAllProjectName(){
		List<ProjectName> result =  unifiedService.findAllProjectName();
		return ResponseEntity.ok(result);
	}
```
- findAllWorkItem
``` java
	@GetMapping("/workitem/all")
	public ResponseEntity<List<WorkItem>> findAllWorkItem(){
		List<WorkItem> result = unifiedService.findAllWorkItem();
		return ResponseEntity.ok(result);
	}
```
- save
``` java
	@PostMapping("/save")
	public ResponseEntity<WorkDiary> save(@RequestBody WorkDiary workDiary) {
		unifiedService.save(workDiary);
		return ResponseEntity.ok(workDiary);
	}
```
- deleteDiaryList
``` java
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDiaryList(@PathVariable Integer id) {
	    unifiedService.deleteDiaryList(id);
	    return ResponseEntity.ok("第 " + id + " 已刪除");
	}
```
- findAllWorkDiaries
``` java
	@GetMapping("/all")
	public ResponseEntity<List<WorkDiary>> findAllWorkDiaries(){
		List<WorkDiary> result = unifiedService.findAllWorkDiaries();
		return ResponseEntity.ok(result);
	}
```
- findAllEmpolyees
``` java
	@GetMapping("/employees/all")
	public ResponseEntity<List<Employee>> findAllEmpolyees(){
		List<Employee> result =  unifiedService.findAllEmpolyees();
		return ResponseEntity.ok(result);
	}
```
- update
``` java
	@PatchMapping("/update/{id}")
	public ResponseEntity<WorkDiary> update(@PathVariable Integer id,@RequestBody Map<String, Object> updateFields){
        WorkDiary updatedWorkDiary = unifiedService.update(id, updateFields);
        return ResponseEntity.ok(updatedWorkDiary);
	}
```
- createNewEmpolyee
``` java
	@PostMapping("/employees/create")
	public ResponseEntity<Employee> createNewEmpolyee(@RequestBody Employee employee) {
	    Employee savedEmployee = unifiedService.createNewEmpolyee(employee);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	}
```
- createNewProjectName
``` java
	@PostMapping("/projectname/create")
	public ResponseEntity<ProjectName> createNewProjectName(@RequestBody ProjectName projectName) {
		ProjectName savedProjectname =  unifiedService.createNewProjectName(projectName);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectname);
	}
	 
```
- downloadExcel
``` java
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
	 
```
- sendMail
``` java
	 @GetMapping("/sendmail")
	 public ResponseEntity<String> sendMail() throws IOException, MessagingException {
	     sendMail.sendMail();
	     return ResponseEntity.ok("發送成功");
	 }
```
- deleteAllDiariesList
``` java
	 @DeleteMapping("/delete")
	 public ResponseEntity<String> deleteAllDiariesList() {
	     unifiedService.deleteAllDiaryList();
	     return ResponseEntity.ok("刪除完畢");
	 }
```
- findAllDept
``` java
	 @GetMapping("/employees/allDept")
	 public ResponseEntity<List<String>> findAllDept(){
		  List<String> result = unifiedService.findAllDept();
		  return ResponseEntity.ok(result);
	 }
```








