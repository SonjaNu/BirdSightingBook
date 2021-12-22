package com.example.BirdSightingBook.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@OneToOne(mappedBy = "book")
	@JsonIgnoreProperties("book")
	private User user;
	
	@ManyToMany(targetEntity = Finding.class, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("books")
	private List<Finding> findings;

	public Book() {
		super();
		this.name = null;
	}

	public Book(User user) {
		super();
		this.name = user.getUsername() + "'s book";
		this.user = user;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Finding> getFindings() {
		return findings;
	}

	public void setFindings(List<Finding> findings) {
		this.findings = findings;
	}
	public void addFinding(Finding finding) {
		this.findings.add(finding);
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
	
	

}
