package com.accenture.lkm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.lkm.entity.EmployeeEntity;

@RepositoryDefinition(idClass = Integer.class, domainClass = EmployeeEntity.class)
@Transactional(value = "txManager")
// name of TransactionManger can be any thing
public interface EmployeeDAO {
	
	EmployeeEntity save(EmployeeEntity entity);
	
	@Query(name="employeeReferralsInDateRange")
	List<EmployeeEntity> getEmployeeDetails(Date fromDate, Date toDate);
}
