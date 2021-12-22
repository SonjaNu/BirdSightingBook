package com.example.BirdSightingBook.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);
	
	@RestResource public Optional<User> findByEmail(@Param("email") String email);
	
	@Query(value="select * from usertable u where u.username like %:keyword%", nativeQuery=true)
	public List <User> findByKeyword(@Param("keyword") String keyword);
}
