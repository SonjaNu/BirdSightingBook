package com.example.BirdSightingBook.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface SightplaceRepository extends CrudRepository<Sightplace, Long> {
	
	@RestResource public Optional<Sightplace> findByName(@Param("name") String name);
	
	@RestResource public Optional<Sightplace> findById(@Param("id") Long id);
	
	@Query(value="select * "
			+ "from Sightplace c"
			+ "where c.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Sightplace> findByKeyword(@Param("keyword") String keyword);
}
