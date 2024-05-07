package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.models.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
		
	@RequestMapping("/home")
	public String test(Model model) {
		
		model.addAttribute("title","Home - Smart Contact Manager");
		
		return "home";     
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title","about - Smart Contact Manager");
		
		return "about";     
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title","Register - Smart Contact Manager");
		model.addAttribute("user",new User());		//sending a blank values initially
		return "signup";     
	}
	//handler for user registration
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model model) {
		if(!agreement) {
			System.out.println("you have not agreed the terms and conditions");
		}
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		System.out.println("agreement"+agreement);
		System.out.println("User"+user);
		
		User result=this.userRepository.save(user);
		model.addAttribute("user",result);
		return "signup";
	}
}
