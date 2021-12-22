package com.example.BirdSightingBook.web;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BirdSightingBook.domain.Sightplace;
import com.example.BirdSightingBook.domain.SightplaceRepository;




@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class SightplaceControllerRest {

	@Autowired private SightplaceRepository sightPlaceRepository;
	
	@GetMapping("/places")
	public List<Sightplace> getCategoriesRest() {
		return (List<Sightplace>) sightPlaceRepository.findAll();
	}
	
	@GetMapping("/places/{id}")
	public Optional<Sightplace> findPlaceRest(@PathVariable("id") Long sightplaceId) {
		return sightPlaceRepository.findById(sightplaceId);
	}
	
	@RequestMapping(value = "/api/places", method = RequestMethod.POST)
	public @ResponseBody void saveRest(@RequestBody Sightplace sightplace) {
		sightPlaceRepository.save(sightplace);
	}
	
	@PreAuthorize(value="hasAuthority('USER')")
	@GetMapping("/delete/{id}")
	public void deletePlaceRest(@PathVariable("id") Long id) {
		sightPlaceRepository.delete(sightPlaceRepository.findById(id).get());
	}
}


