package com.accenture.lkm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.lkm.entity.EmployeeEntity;
import com.accenture.lkm.entity.SkillSetEntity;
@RepositoryDefinition(idClass = Integer.class, domainClass = SkillSetEntity.class)
@Transactional(value = "txManager")
// name of TransactionManger can be any thing
public interface SkillSetDAO{
	@Query(name="skillLevel")
	public SkillSetEntity getSkillSet(String skill, String level);
	
	@Query(name="skillsQuery")
	public List<String> getSkills();
}
