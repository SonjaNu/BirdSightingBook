package com.example.BirdSightingBook.domain;

import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface FindingRepository extends CrudRepository<Finding, Long> {

	@RestResource public Optional<Finding> findById(@Param("id") Long id);
	
	@RestResource public Optional<Finding> findByName(@Param("name") String name);
	
	@RestResource public Iterable<Finding> findByBird(@Param("bird") String bird);
	
	@RestResource public Iterable<Finding> findBySpecie(@Param("specie") String specie);
	
	@RestResource public Iterable<Finding> findBySightplace(@Param("sightplace") String sightplace);
	

	
	@Query(value="select * "
			+ "from Finding d "
			+ "where d.name like %:keyword% ", 
			nativeQuery = true)
	public Iterable<Finding> findByKeyword(@Param("keyword") String keyword);
}
