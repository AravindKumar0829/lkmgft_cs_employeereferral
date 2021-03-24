package com.accenture.lkm.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.accenture.lkm.service.EmployeeServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/cst-root-ctx.xml")
public class EmployeeServiceTest {

	@Autowired
	EmployeeServiceImpl service;
	@Test
	public void testService() {
		assertTrue(service != null);
	}
	
	@Test
	public void testGetSkills() {
		assertTrue(service.getSkills() != null);
	}
	
	@Test
	public void testGetSkillsListContent() {
		assertTrue(service.getSkills().contains("J2EE"));
	}

}
