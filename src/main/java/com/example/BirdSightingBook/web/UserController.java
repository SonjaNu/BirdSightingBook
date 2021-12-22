package com.example.BirdSightingBook.web;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BirdSightingBook.domain.Book;
import com.example.BirdSightingBook.domain.BookRepository;
import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.FindingRepository;
import com.example.BirdSightingBook.domain.SigningUp;
import com.example.BirdSightingBook.domain.User;
import com.example.BirdSightingBook.domain.UserRepository;





@CrossOrigin
@Controller
@RequestMapping("/users")
public class UserController {
	
	// REPOSITORIES
	@Autowired private UserRepository userRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private FindingRepository findingRepository;
	
	
	@GetMapping("")
	public String listUsers(Model model, String keyword) {
		if(keyword != null) {
			model.addAttribute("users", userRepository.findByKeyword(keyword));
		} else {
			model.addAttribute("users", userRepository.findAll());
		}
		return "userListForm";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/book")
	public String myBook(Model model, Authentication auth) {
		
		User user = userRepository.findByUsername(auth.getName());
		model.addAttribute(user);
		model.addAttribute("book", user.getBook());
		return "book";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/book/{id}")
	public String user(@PathVariable("id") Long userId, Model model, Authentication auth, String keyword) {
	
		if(userId == userRepository.findByUsername(auth.getName()).getId()) {
			User user2 = userRepository.findByUsername(auth.getName());
			model.addAttribute("currUser", null);
			model.addAttribute("user", user2);
			model.addAttribute("book", user2.getBook());
			return "book";
		}
		
		model.addAttribute("currUser", userRepository.findByUsername(auth.getName()));
		User user3 = userRepository.findById(userId).get();
		model.addAttribute("user", user3);
		model.addAttribute("book", user3.getBook());
		return "book";
			
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/adduser")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SigningUp());
		return "userAdding";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", userRepository.findById(userId).get());
		return "userEditing";
	}
	@PreAuthorize(value = "hasAuthority('USER')")
	@PostMapping("/update")
	public String saveOldUser(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		if(bookRepository.findBookByUserId(userId).isPresent()) {
			bookRepository.delete(bookRepository.findBookByUserId(userId).get());
		}
		
		userRepository.delete(userRepository.findById(userId).get());
		return "redirect:/users";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/{userid}/add/{findingid}")
	public String addDiscFromUser(@PathVariable("userid") Long userId, @PathVariable("findingid") Long discId, Authentication auth, Model model) {
		Finding finding = findingRepository.findById(discId).get();
		Long myId = userRepository.findByUsername(auth.getName()).getId();
		Book book = bookRepository.findBookByUserId(myId).get();
		if (!book.getFindings().contains(finding)) {
			finding.addToBook(book);
			findingRepository.save(finding);
			book.addFinding(finding);
			bookRepository.save(book);
		}
		model.addAttribute("userid", userId);
		
		return "redirect:/users/mybirdbook/{userid}";
	}
	
//	@PreAuthorize(value = "hasAnyAuthority('USER')")
//	@GetMapping("/profile")
//	public String userProfile(Authentication auth, Model model) {
//		User user = repository.findByUsername(auth.getName());
//		model.addAttribute("user", user);
//		return "profile";
//	}
	@PreAuthorize(value = "hasAuthority('USER')")
	@PostMapping("/createuser")
	public String save(@Valid @ModelAttribute("signupform") SigningUp signingUp, BindingResult bindingResult, Authentication auth) {
		if (!bindingResult.hasErrors()) {
			if(signingUp.getPassword().equals(signingUp.getPasswordCheck())) {
				String pwd = signingUp.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signingUp.getUsername().toLowerCase());
				newUser.setEmail(signingUp.getEmail());
				newUser.setRole(signingUp.getRole());

				if(userRepository.findByUsername(newUser.getUsername()) == null) {
					
					if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
						newUser.setBook(new Book(newUser));
						userRepository.save(newUser);
					} 
					else {
						bindingResult.rejectValue("email", "err.email", "User with this email already exists!");
						return "userAdding";
					}
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists!");
					return "userAdding";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match!");
				return "userAdding";
			}
		}
		else {
			return "userAdding";
		}
			
		return "redirect:/users";
		
	}
	
}

