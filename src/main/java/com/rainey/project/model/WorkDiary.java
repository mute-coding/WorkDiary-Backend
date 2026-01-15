package com.rainey.project.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


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

	
	public WorkDiary() {}
	public WorkDiary(Integer id, String year, String week, String month,String date, String initDept, String initBy,
			String workitem, String itemDescribe, String devMinute, String supportMinute, String systemMinute,LocalDateTime createTime) {
		super();
		this.id = id;
		this.year = year;
		this.week = week;
		this.month = month;
		this.date = date;
		this.initDept = initDept;
		this.initBy = initBy;
		this.workitem = workitem;
		this.itemDescribe = itemDescribe;
		this.devMinute = devMinute;
		this.supportMinute = supportMinute;
		this.systemMinute = systemMinute;
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getInitDept() {
		return initDept;
	}
	public void setInitDept(String initDept) {
		this.initDept = initDept;
	}
	public String getInitBy() {
		return initBy;
	}
	public void setInitBy(String initBy) {
		this.initBy = initBy;
	}
	public String getWorkitem() {
		return workitem;
	}
	public void setWorkitem(String workitem) {
		this.workitem = workitem;
	}
	public String getItemDescribe() {
		return itemDescribe;
	}
	public void setItemDescribe(String itemDescribe) {
		this.itemDescribe = itemDescribe;
	}
	public String getDevMinute() {
		return devMinute;
	}
	public void setDevMinute(String devMinute) {
		this.devMinute = devMinute;
	}
	public String getSupportMinute() {
		return supportMinute;
	}
	public void setSupportMinute(String supportMinute) {
		this.supportMinute = supportMinute;
	}
	public String getSystemMinute() {
		return systemMinute;
	}
	public void setSystemMinute(String systemMinute) {
		this.systemMinute = systemMinute;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "WorkDiary [id=" + id + ", year=" + year + ", week=" + week + ", month=" + month +", date=" + date + ", initDept="
				+ initDept + ", initBy=" + initBy + ", workitem=" + workitem + ", itemDescribe=" + itemDescribe
				+ ", devMinute=" + devMinute + ", supportMinute=" + supportMinute + ", systemMinute=" + systemMinute + ", createTime=" + createTime
				+ " ]";
	}
	
}
