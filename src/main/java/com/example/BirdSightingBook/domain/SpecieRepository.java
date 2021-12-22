package com.example.BirdSightingBook.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface SpecieRepository extends CrudRepository<Specie, Long> {
	
	@RestResource public Optional<Specie> findById(@Param("id") Long id);
	
	@RestResource public Optional<Specie> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Specie c "
			+ "where c.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Specie> findByKeyword(@Param("keyword") String keyword);
}
