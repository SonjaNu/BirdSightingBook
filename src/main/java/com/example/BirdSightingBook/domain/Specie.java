package com.example.BirdSightingBook.domain;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Specie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specie" )
	//@JsonIgnoreProperties({"company", "discs"})
	private List<Bird> birds;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specie" )
	//@JsonIgnoreProperties({"company", "discs"})
	private List<Finding> findings;

	public Specie() {
		super();
		this.name = null;
	}

	public Specie(String name) {
		super();
		this.name = name;
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

	public List<Bird> getBirds() {
		return birds;
	}

	public void setBirds(List<Bird> birds) {
		this.birds = birds;
	}

	public List<Finding> getFindings() {
		return findings;
	}

	public void setFindings(List<Finding> findings) {
		this.findings = findings;
	}

	@Override
	public String toString() {
		return "Specie [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
	

}
