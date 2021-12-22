package com.example.BirdSightingBook.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface BookRepository extends CrudRepository<Book, Long>{
	
	@RestResource public Optional <Book> findById(@Param("id") Long id);
	
	@RestResource public Optional <Book> findBookByUserId(@Param("id") Long userId);
	
	@RestResource public Optional <Book> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Book b "
			+ "where b.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Book> findByKeyword(@Param("keyword") String keyword);
	
}
