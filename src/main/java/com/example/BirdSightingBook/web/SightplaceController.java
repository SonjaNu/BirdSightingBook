package com.example.BirdSightingBook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.Sightplace;
import com.example.BirdSightingBook.domain.SightplaceRepository;



@CrossOrigin
@Controller
@RequestMapping(value = "/places")
public class SightplaceController {
	
	@Autowired private SightplaceRepository sightplaceRepository;
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("")
	public String categoryList(Model model) {
		model.addAttribute("sightplaces", sightplaceRepository.findAll());
		model.addAttribute("sightplace", new Sightplace());
		return "sightplaces";
	}
	
	@GetMapping("/addplace")
	public String addPlace(Model model) {
		model.addAttribute("sightplace", new Sightplace());
		return "addplace"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("/edit/{id}")
	public String editPlace(@PathVariable("id") Long sightplaceId, Model model) {
		model.addAttribute("sightplace", sightplaceRepository.findById(sightplaceId));
		return "editplace"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("/delete/{id}")
	public String deleteSightplace(@PathVariable("id") Long sightplaceId) {
		Sightplace sightPlace = sightplaceRepository.findById(sightplaceId).get();
		List<Finding> findings = sightPlace.getFindings();
		for(Finding finding : findings) {
			finding.setSightplace(null);
		}
		sightplaceRepository.delete(sightPlace);
		return "redirect:/places";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveSightplace(@ModelAttribute Sightplace sightplace) {
		if(!sightplaceRepository.findByName(sightplace.modified(sightplace.getName())).isEmpty()) {
			return "redirect:/places";
		}
		sightplace.setName(sightplace.modified(sightplace.getName()));
		sightplaceRepository.save(sightplace);
		return "redirect:/places";
	}
}


