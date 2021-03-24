package com.accenture.lkm.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.lkm.business.bean.DateRangeBean;
import com.accenture.lkm.business.bean.EmployeeBean;
import com.accenture.lkm.exceptions.SkillLevelMismatchException;
import com.accenture.lkm.service.EmpService;

@Controller
public class EmpController {

	@Autowired
	private EmpService empService;
	

	@RequestMapping("empform.html")
	public ModelAndView addEmpForm() {
		return new ModelAndView("AddEmpForm", "empBean", new EmployeeBean());
	}
	
	@RequestMapping("addEmployee")
	public ModelAndView processEmpForm(@ModelAttribute("empBean") @Valid EmployeeBean bean, BindingResult result) throws Exception {
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()) {
			mv.setViewName("AddEmpForm");
		} else {
			empService.addEmp(bean);
			mv.setViewName("success");
			
			mv.addObject("empName", bean.getEmpName());
			mv.addObject("candName", bean.getCandName());
		}
		return mv;
	}
	
	@RequestMapping("dateForm")
	public ModelAndView viewEmpForm() {
		return new ModelAndView("EmployeeReport", "dateRange", new DateRangeBean());
	}
	
	@RequestMapping("dateRangeReport")
	public ModelAndView procssViewForm(@ModelAttribute("dateRange")DateRangeBean dateRange) {
		
		List<EmployeeBean> list = empService.getEmployeeDetails(dateRange.getFromDate(), dateRange.getToDate());
		System.out.println(list.size()+"***********************");
		ModelAndView mv = new ModelAndView();
		if(list.size() > 0) {
			mv.setViewName("CandidateDetails");
			mv.addObject("empReferralList", list);
		} else {
			mv.setViewName("EmployeeReport");
			mv.addObject("errMsg", "no records found....");
		}
		
		return mv;
		
	}
	
	@ModelAttribute("skillslist")
	public List<String> getSkills() {
		return empService.getSkills();
	}
	
	//Error Handler:
	/*@ExceptionHandler(value=SkillLevelMismatchException.class)
	public ModelAndView handleSkillLevelMismatchException(SkillLevelMismatchException exception){	
		ModelAndView  modelAndView = new ModelAndView();
		modelAndView.setViewName("GeneralizedExceptionHandlerPage");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}*/
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView handleAllExceptions(Exception exception){	
		ModelAndView  modelAndView = new ModelAndView();
		modelAndView.setViewName("GeneralizedExceptionHandlerPage");
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}
	
}
