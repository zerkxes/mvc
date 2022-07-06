package com.aniket.spring.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aniket.spring.web.model.Todo;
import com.aniket.spring.web.service.TodoRepository;
import com.aniket.spring.web.service.TodoService;
//import org.springframework.web.bind.annotation.ResponseBody;
@Controller//marks the class to be picked up by spring
@SessionAttributes("name")
public class TodoController {
	@Autowired
	TodoService service;
	@Autowired
	TodoRepository repo;
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,false));
	}
	
	@RequestMapping(value="listTodos", method=RequestMethod.GET )//maps function to a url
	//@ResponseBody//converts function output to xml or json so that it can be displayed
	public String showTodos(ModelMap m)
	{
		m.put("todos", service.retrieveTodos("in28minutes"));
		String name=(String)m.get("name");
		m.put("name", name);
		return "listTodos";
	}
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showWelcomePage(ModelMap m)
	{
		m.put("name", "zerkxes");
		return "welcome";
	}
	@RequestMapping(value="add-todo", method=RequestMethod.GET )//maps function to a url
	//@ResponseBody//converts function output to xml or json so that it can be displayed
	public String addTodo(ModelMap m,@ModelAttribute("todo") @Valid Todo todo)
	{
		String name=(String)m.get("name");
		m.put("name", name);
		return "add-todo";
	}
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String todo(ModelMap m,@ModelAttribute("todo")@Valid Todo todo, BindingResult result)
	{
		m.addAttribute("todo", new Todo(0, "in28minutes",
				"Default Desc", new Date(), false));
		if(result.hasErrors()){
			return "add-todo";
		}
		todo.setUser("in28minutes");
		repo.save(todo);
		service.addTodo("in28minutes", todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:listTodos";
	}
	@RequestMapping(value="delete-todo", method=RequestMethod.GET )//maps function to a url
	//@ResponseBody//converts function output to xml or json so that it can be displayed
	public String deleteTodo(@RequestParam int id)
	{
		service.deleteTodo(id);
		return "redirect:listTodos";
	}
	@RequestMapping(value="update-todo", method=RequestMethod.GET )//maps function to a url
	//@ResponseBody//converts function output to xml or json so that it can be displayed
	public String showUpdateTodo(@RequestParam int id, ModelMap m,@ModelAttribute("todo") @Valid Todo todo)
	{
		Todo todo1=service.retrieveTodo(id);
		m.put("todo", todo1);
		return "add-todo";
	}
	@RequestMapping(value="update-todo", method=RequestMethod.POST )//maps function to a url
	//@ResponseBody//converts function output to xml or json so that it can be displayed
	public String UpdateTodo(@ModelAttribute("todo") @Valid Todo todo, BindingResult result, ModelMap m)
	{
		todo.setUser("in28minutes");
		if(result.hasErrors()){
			return "add-todo";
		}
		service.updateTodo(todo);
		m.put("todo",todo);
		return "redirect:listTodos";
	}
	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) 
			new SecurityContextLogoutHandler().logout(request,response,auth);
		return "redirect:/";
	}
}
