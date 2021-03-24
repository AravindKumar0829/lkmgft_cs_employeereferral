package com.accenture.lkm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.lkm.business.bean.EmployeeBean;
import com.accenture.lkm.entity.EmployeeEntity;
import com.accenture.lkm.entity.SkillSetEntity;
import com.accenture.lkm.exceptions.SkillLevelMismatchException;

@Repository
@Transactional(value = "txManager")
public class EmpDaoWrapper {

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private SkillSetDAO skillSetDAO;

	
	public void addEmp(EmployeeBean bean) throws Exception {
		double bonus = checkLevel(bean);
		//TO-DO - Include statements to save the data into database

	}

	public double checkLevel(EmployeeBean bean) {
		SkillSetEntity skillSet = skillSetDAO.getSkillSet(bean.getCandSkill(), bean.getCandLevel());
		if (skillSet != null)
			return skillSet.getBonus();
		return 0;
	}

	public List<String> getSkills() {
		return skillSetDAO.getSkills();
	}

	public List<EmployeeBean> getEmployeeDetails(Date fromDate, Date toDate) {
		List<EmployeeEntity> employeeDetails = employeeDAO.getEmployeeDetails(fromDate, toDate);
		List<EmployeeBean> beans = new ArrayList<>();
		employeeDetails.forEach(employeeEntity -> {
			EmployeeBean toBean = convertEntityToBean(employeeEntity);
			beans.add(toBean);
		});
		return beans;
	}

	// Utility Methods.......
	public static EmployeeBean convertEntityToBean(EmployeeEntity entity) {
		EmployeeBean employeeBean = new EmployeeBean();
		BeanUtils.copyProperties(entity, employeeBean);
		return employeeBean;
	}

	public static EmployeeEntity convertBeanToEntity(EmployeeBean bean) {
		EmployeeEntity entity = new EmployeeEntity();
		BeanUtils.copyProperties(bean, entity);
		return entity;
	}

}
