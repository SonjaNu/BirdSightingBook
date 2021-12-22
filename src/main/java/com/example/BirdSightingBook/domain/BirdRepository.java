package com.example.BirdSightingBook.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface BirdRepository extends CrudRepository<Bird, Long> {
	
	@RestResource public Optional<Bird> findById(@Param("id") Long id);

	@RestResource public Optional<Bird> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Bird p "
			+ "where p.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Bird> findByKeyword(@Param("keyword") String keyword);
}
