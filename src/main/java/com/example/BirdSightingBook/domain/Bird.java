package com.example.BirdSightingBook.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Bird {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	private String name;
	
	@ManyToOne
	@JsonIgnore
	private Specie specie;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bird")
	@JsonIgnoreProperties({"bird", "findings"})
	private List<Finding> findings;

	public Bird() {
		super();
		this.name = null;
		this.specie = null;
	}

	public Bird(String name, Specie specie) {
		super();
		this.name = name;
		this.specie = specie;
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

	public Specie getSpecie() {
		return specie;
	}

	public void setSpecie(Specie specie) {
		this.specie = specie;
	}

	public List<Finding> getFindings() {
		return findings;
	}

	public void setFindings(List<Finding> findings) {
		this.findings = findings;
	}

	@Override
	public String toString() {
		return "Bird [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}
