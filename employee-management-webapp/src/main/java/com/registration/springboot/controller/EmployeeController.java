package com.registration.springboot.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
//import org.springframework.hateoas.server.LinkBuilder;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.registration.springboot.model.Employee;
import com.registration.springboot.service.EmployeeService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.*;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping("/")
	public String viewHomePage(Model model) {
		System.out.println("home page");
		return findPaginated(1,"firstName","asc",model);
	}
	
	@SuppressWarnings("null")
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		//ServerHttpResponse serverHttpResponse = null;
		//serverHttpResponse.getHeaders().add(HttpHeaders.LINK, sortDir);
		int pageSize=5;
		Page<Employee> page=employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees=page.getContent();
		
		
		final StringBuilder header=new StringBuilder();
		if(page !=null) {
			if(!page.isFirst()) {
				String firstpage=ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", 0)
						.replaceQueryParam("size", page.getSize()).build().encode().toUriString();
				header.append(firstpage + "rel=first");
			}
				
		}
		//PagedResourcesAssembler<Employee> assembler;
		
		//page.getPageable().getPageNumber()
		//Link link=linkTo(methodOn(EmployeeController.class).findPaginated(pageNo, sortField, sortDir, model));
	  //PagedResources<ProductEntity> pr= assembler.toResource(products,linkTo(ProductRESTController.class).slash("/products").withSelfRel());
	  //HttpHeaders responseHeaders = new HttpHeaders();
	  //responseHeaders.add("Link",createLinkHeader(pr));
	  //return new ResponseEntity<>(assembler.toResource(products,linkTo(ProductRESTController.class).slash("/products").withSelfRel()),responseHeaders,HttpStatus.OK);
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value="id") long id,Model model) {
		Employee employee= employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value="id") long id) {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		System.out.println("save employee");
		employeeService.saveEmployee(employee);
		System.out.println("save success");
		return "redirect:/";
	}
	
}
