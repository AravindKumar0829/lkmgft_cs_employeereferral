package com.accenture.lkm.service;

import java.util.Date;
import java.util.List;

import com.accenture.lkm.business.bean.EmployeeBean;
import com.accenture.lkm.exceptions.SkillLevelMismatchException;

public interface EmpService {	
	void addEmp(EmployeeBean bean) throws Exception;
	List<String> getSkills();
	List<EmployeeBean> getEmployeeDetails(Date fromDate, Date toDate);
}