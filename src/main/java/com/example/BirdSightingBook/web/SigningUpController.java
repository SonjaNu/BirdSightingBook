package com.example.BirdSightingBook.web;



import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.BirdSightingBook.domain.Book;
import com.example.BirdSightingBook.domain.SigningUp;
import com.example.BirdSightingBook.domain.User;
import com.example.BirdSightingBook.domain.UserRepository;





@Controller
public class SigningUpController {
	
	// REPOSITORIES
	@Autowired private UserRepository repository;
	
	/**
     * Create new user
     * Check if user already exists & form validation
     * 
     * @param signupForm
     * @param bindingResult
     * @return
     */
	
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String savenew(@Valid @ModelAttribute("signupform") SigningUp signingUp, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if(signingUp.getPassword().equals(signingUp.getPasswordCheck())) {
				String pwd = signingUp.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signingUp.getUsername());
				newUser.setEmail(signingUp.getEmail());
				newUser.setRole("USER");

				if(repository.findByUsername(newUser.getUsername()) == null) {
					
					if(repository.findByEmail(newUser.getEmail()).isEmpty()) {
						newUser.setBook(new Book(newUser));
						repository.save(newUser);
					} 
					else {
						bindingResult.rejectValue("email", "err.email", "User with this email already exists!");
						return "signupform";
					}
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists!");
					return "signupform";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match!");
				return "signupform";
			}
		}
		else {
			return "signupform";
		}
			
		return "redirect:/login";
		
	}
}
