package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepo;

@Controller
public class HomeController {

	@Autowired
	EmployeeRepo db;
	@RequestMapping("/")    //for url instead of servlet we use method and requestmapping is dealing with get and post method
	String myfun (Model m)
	{
		Employee e=new Employee();
		m.addAttribute("emp",e);
		return "reg";
	}
	
	@RequestMapping("/register")
	String myfun1(@ModelAttribute ("emp") Employee e)
	{
		System.out.println(e.toString());
		db.save(e);
		return "redirect:/datashow"; 
	}
	
	@RequestMapping("/datashow")
	String myshow(Model e)
	{
		
		List<Employee> l=db.findAll();
		e.addAttribute("employees",l);
		return "show";
	}
	
	
	@RequestMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") int id, Model m) {
	    Employee em = db.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    db.delete(em);
	    return "redirect:/datashow";
	}
	
	@RequestMapping("/edit/{id}")
	public String editEmployee(@PathVariable("id") int id, Model m) {
	    Employee em = db.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	   m.addAttribute("empdata", em);
	   
	    return "edit";
	}
	
	@RequestMapping("/update/{id}")
	public String updateEmployee(@ModelAttribute ("empdata") Employee e) {
	  
	   db.save(e);
	    return "redirect:/datashow";
	}
	
	
}
