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

import com.example.BirdSightingBook.domain.Finding;
import com.example.BirdSightingBook.domain.FindingRepository;



@CrossOrigin
@RestController
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class FindingControllerRest {
	
	@Autowired private FindingRepository repository;
	
	/******************RESTFUL SERVICES **************************/
	
	@GetMapping("/findings")
	public List<Finding> findingListRest() {
		return (List<Finding>) repository.findAll();
	}
	
	@GetMapping("/findings/{id}")
	public Optional<Finding> findFindingRest(@PathVariable("id") Long findingId) {
		return repository.findById(findingId);
	}
	
	@GetMapping("/findings/{name}")
	public Optional<Finding> findDiscRest(@PathVariable("name") String findingName) {
		return repository.findByName(findingName);
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value = "/api/findings", method = RequestMethod.POST)
	public @ResponseBody void saveRest(@RequestBody Finding finding) {
		repository.save(finding);
	}
}
