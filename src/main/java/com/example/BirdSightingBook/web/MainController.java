package com.example.BirdSightingBook.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.BirdSightingBook.domain.SigningUp;

@Controller
public class MainController {

	
	@RequestMapping(value = "/login")
	public String login() {
		return "loginform";  //palautetaan loginform-template
	}
	
	@RequestMapping(value = {"/main", "/"}, method = RequestMethod.GET)
	public String main(Model model) {
		return "mainform"; 
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUser(Model model) {
		model.addAttribute("signupform", new SigningUp());
		return "signupform";
	}
}
