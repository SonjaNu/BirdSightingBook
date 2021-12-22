
package com.example.BirdSightingBook.web;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BirdSightingBook.domain.Book;
import com.example.BirdSightingBook.domain.BookRepository;
import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.FindingRepository;
import com.example.BirdSightingBook.domain.User;
import com.example.BirdSightingBook.domain.UserRepository;



@CrossOrigin
@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired private BookRepository bookRepository;
	@Autowired private FindingRepository findingRepository;
	@Autowired private UserRepository userRepository;
	
	
	/*@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/{id}")
	public String listMyDiscs(@PathVariable("id") Long id, Model model, Authentication auth, String keyword) {
		User user = userService.getById(id);
		model.addAttribute("me", userService.getByUsername(auth.getName()));
		model.addAttribute("user", user);
		model.addAttribute("bag", user.getBag());
		return "bag";
	}*/
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/remove/{id}")
	public String removeDiscFromBag(@PathVariable("id") Long discId, Authentication auth) {
		Finding finding = findingRepository.findById(discId).get();
		User user = userRepository.findByUsername(auth.getName());
		Book book = user.getBook();
		book.getFindings().remove(finding);
		bookRepository.save(book);
		finding.deleteFromBook(book);
		findingRepository.save(finding);
		return "redirect:/users/book";
	}
}


