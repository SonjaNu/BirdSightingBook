package com.example.BirdSightingBook.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BirdSightingBook.domain.Bird;
import com.example.BirdSightingBook.domain.BirdRepository;
import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.FindingRepository;
import com.example.BirdSightingBook.domain.Specie;
import com.example.BirdSightingBook.domain.SpecieRepository;



@CrossOrigin
@Controller
@RequestMapping(value = "/species")
public class SpecieController {
	
	@Autowired private SpecieRepository specieRepository;
	@Autowired private BirdRepository birdRepository;
	@Autowired private FindingRepository findingRepository;
	
	@GetMapping("")
	public String listSpecies(Model model, String keyword) {
		if(keyword != null ) {
			model.addAttribute("species", specieRepository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("species", specieRepository.findAll());
		}
		model.addAttribute("specie", new Specie());
		return "species"; 
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@PostMapping({"/save", "/save/{id}"})
	public String saveSpecie(@PathVariable(required=false) Long id, Specie specie) {
		if(id != null) {
			specie.setName(specie.getName());
			specieRepository.save(specie);
			return "redirect:/species";
		}
		else if(!specieRepository.findByName(specie.getName()).isEmpty()) {
			return "redirect:/species";
		}
		specie.setName(specie.getName());
		specieRepository.save(specie);
		return "redirect:/species";
	}
	
	@GetMapping("/specie/{id}")
	public String companyInfo(@PathVariable("id") Long specieId, Model model) {
		model.addAttribute("bird", new Bird());
		Specie specie = specieRepository.findById(specieId).get();
		model.addAttribute("birds", specie.getBirds());
		model.addAttribute("specie", specie);
		return "specieItem";
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@PostMapping({"/specie/{id}/savebird", "/specie/{id}/savebird/{birdid}"})
	public String saveBird(@PathVariable("id") Long specieId, @PathVariable(required=false) Long birdId, Model model, Bird bird) {
		Specie specie = specieRepository.findById(specieId).get();
		if(birdId != null) {
			Bird oldBird = birdRepository.findById(birdId).get();
			oldBird.setName(oldBird.getName());
			birdRepository.save(oldBird);
			return "redirect:/species/specie/{id}";
		}
		
		else if(specieRepository.findById(specieId).get().getBirds().contains(bird)) {
			return "redirect:/species/specie/{id}";
		}
		else {
			bird.setName(bird.getName().toLowerCase());
			bird.setSpecie(specie);
			birdRepository.save(bird);
			return "redirect:/species/specie/{id}";
		}
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("/specie/{id}/delete/{birdid}")
	public String deleteBird(@PathVariable("id") Long specieId, @PathVariable("birdid") Long birdId) {
		List<Finding> findings = birdRepository.findById(birdId).get().getFindings();
		for(Finding finding : findings) {
			finding.setBird(null);
			findingRepository.save(finding);
		}
		birdRepository.delete(birdRepository.findById(birdId).get());
		return "redirect:/species/specie/{id}";
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("/delete/{id}")
	public String deleteCompany(@PathVariable("id") Long specieId) {
		Specie specie = specieRepository.findById(specieId).get();
		List<Finding> findings = specie.getFindings();
		for(Finding finding : findings) {
			finding.setSpecie(null);
			finding.setBird(null);
		}
		List<Bird> birds = specie.getBirds();
		for(Bird bird : birds) {
			birdRepository.delete(bird);
		}
		specieRepository.delete(specie);
		
		return "redirect:/species";
	}
}

