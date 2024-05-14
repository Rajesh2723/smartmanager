package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.helper.Message;
import com.smart.models.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="agreement",defaultValue="false") 
	boolean agreement,Model model,HttpSession session) {
		 try {
			 if(!agreement) {
					System.out.println("you have not agreed the terms and conditions");
					throw new Exception();
				}
			 	if(result.hasErrors()) {
			 		System.out.println("ERROR"+result.toString());
			 		model.addAttribute("user",user);
			 		return "signup";
			 		
			 	}
				user.setRole("ROLE_USER");
				user.setEnabled(true);
				user.setImageUrl("default.png");
				System.out.println("agreement"+agreement);
				System.out.println("User"+user);
				
				User result1=this.userRepository.save(user);
				model.addAttribute("user",result1);
				model.addAttribute("user",new User());//blank user sent.
				 session.setAttribute("message", new Message("Sucessfully added" , "alert-error"));
				 return "signup";
		 }catch(Exception e){
			 e.printStackTrace();
			 session.setAttribute("message", new Message("something went wrong"+e.getMessage(), "alert-error"));
			 return "signup";
		 }
		 
		
	}
}
