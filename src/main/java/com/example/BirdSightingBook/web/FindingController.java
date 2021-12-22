package com.example.BirdSightingBook.web;

import java.util.List;


//import org.hibernate.mapping.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BirdSightingBook.domain.BirdRepository;
import com.example.BirdSightingBook.domain.Book;
import com.example.BirdSightingBook.domain.BookRepository;
import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.FindingRepository;
import com.example.BirdSightingBook.domain.SightplaceRepository;
import com.example.BirdSightingBook.domain.SpecieRepository;
import com.example.BirdSightingBook.domain.UserRepository;





@CrossOrigin
@Controller
@RequestMapping("/findings")
public class FindingController {
	
	@Autowired private FindingRepository findingRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private SightplaceRepository sightplaceRepository;
	@Autowired private SpecieRepository specieRepository;
	@Autowired private BirdRepository birdRepository;
	
	@GetMapping("")
	public String storage(Model model, String keyword, Authentication auth) {
		if(keyword != null ) {
			model.addAttribute("findings", findingRepository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("findings", findingRepository.findAll());
		}
		model.addAttribute("book", bookRepository.findBookByUserId(userRepository.findByUsername(auth.getName()).getId()).get().getFindings());
		return "findingsAll";
	}
	
//	@PreAuthorize(value = "hasAuthority('USER')")
	@PostMapping("/save")
	public String saveFinding(@ModelAttribute Finding finding) {
		// saves new disc with its info
		finding.setName(finding.modify(finding.getName()));
		finding.setSightplace(finding.getSightplace());
		finding.setSpecie(finding.getSpecie());
		finding.setBird(finding.getBird());
		findingRepository.save(finding);
		
		return "redirect:/findings";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/add")
	public String addNewDisc(Model model) {
		model.addAttribute("finding", new Finding());
		model.addAttribute("sightplace", sightplaceRepository.findAll());
		model.addAttribute("specie", specieRepository.findAll());
		model.addAttribute("birds", birdRepository.findAll());
		
		return "findingAdding";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping("/addtobook/{id}")
	public String addFindingToBook(@PathVariable("id") Long findingId, Authentication auth) {
		Finding finding = findingRepository.findById(findingId).get();
		Long userId = userRepository.findByUsername(auth.getName()).getId();
		Book book = bookRepository.findBookByUserId(userId).get();
		if (!book.getFindings().contains(finding)) {
			finding.addToBook(book);
			findingRepository.save(finding);
			book.addFinding(finding);
			bookRepository.save(book);
		} 
		
		return "redirect:/findings";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/edit/{id}")
	public String editDisc(@PathVariable("id") Long findingId, Model model) {
		model.addAttribute("finding", findingRepository.findById(findingId));
		model.addAttribute("sightplace", sightplaceRepository.findAll());
		model.addAttribute("specie", specieRepository.findAll());
		model.addAttribute("birds", birdRepository.findAll());
		return "findingEditing";
	}
	
	// deletes disc entity and its representations from other entities
	@PreAuthorize(value = "hasAuthority('USER')")
	@GetMapping("/delete/{id}")
	public String deleteDisc(@PathVariable("id") Long findingId) {
		Finding finding = findingRepository.findById(findingId).get();
		List<Book> books = (List<Book>) bookRepository.findAll();
		for(Book b: books) {
			if (b.getFindings().contains(finding)) {
				b.getFindings().remove(finding);
				bookRepository.save(b);
			}
		}
		findingRepository.delete(finding);
		return "redirect:/findings";
	}
	
}

