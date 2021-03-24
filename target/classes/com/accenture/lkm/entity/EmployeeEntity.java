package com.accenture.lkm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int empId;
	private String empName;
	private Date empDor;
	private String candName;
	private String candSkill;
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
