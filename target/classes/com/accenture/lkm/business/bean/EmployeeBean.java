package com.accenture.lkm.business.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class EmployeeBean {
	private int empId;
	@NotEmpty
	@Size(min=3, max=7, message="Employee name should be between 3 and 7")
	private String empName;
	@NotNull(message="empDor should not be empty")
	@DateTimeFormat(pattern = "dd-MMM-yyyy")
	private Date empDor;
	@NotEmpty
	private String candName;
	@NotEmpty
	private String candSkill;
	@NotEmpty
	private String candLevel;
	private Double referralBonus;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Date getEmpDor() {
		return empDor;
	}
	public void setEmpDor(Date empDor) {
		this.empDor = empDor;
	}
	public String getCandName() {
		return candName;
	}
	public void setCandName(String candName) {
		this.candName = candName;
	}
	public String getCandSkill() {
		return candSkill;
	}
	public void setCandSkill(String candSkill) {
		this.candSkill = candSkill;
	}
	public String getCandLevel() {
		return candLevel;
	}
	public void setCandLevel(String candLevel) {
		this.candLevel = candLevel;
	}
	public Double getReferralBonus() {
		return referralBonus;
	}
	public void setReferralBonus(Double referralBonus) {
		this.referralBonus = referralBonus;
	}
}
