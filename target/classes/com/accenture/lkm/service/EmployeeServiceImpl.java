package com.accenture.lkm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.lkm.business.bean.EmployeeBean;
import com.accenture.lkm.dao.EmpDaoWrapper;
import com.accenture.lkm.exceptions.SkillLevelMismatchException;

@Service
public class EmployeeServiceImpl implements EmpService{
	
	@Autowired
	EmpDaoWrapper edao;

	@Override
	public void addEmp(EmployeeBean bean) throws Exception {
		edao.addEmp(bean);
		
	}

	@Override
	public List<String> getSkills() {
		return edao.getSkills();
	}

	@Override
	public List<EmployeeBean> getEmployeeDetails(Date fromDate, Date toDate) {
		return edao.getEmployeeDetails(fromDate, toDate);
	}
	
}
