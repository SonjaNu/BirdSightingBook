package com.example.BirdSightingBook.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Sightplace {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sightplace" )
	@JsonIgnoreProperties({"bird", "sightplace", "specie", "books"})
	private List<Finding> findings;

	public Sightplace() {
		super();
		this.name = null;
	}

	public Sightplace(String name) {
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
	
	public String getModifiedName() {
		return modified(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Finding> getFindings() {
		return findings;
	}

	public void setFindings(List<Finding> findings) {
		this.findings = findings;
	}
	
	public String modified(String word) {
		String words[] = word.split("\\s");
		String outcome = "";
		for (String w: words) {
			String first = w.substring(0, 1);
			String rest = w.substring(1);
			outcome += first.toUpperCase() + rest.toLowerCase() + " ";
		}
		
		return outcome.trim();
	}

	@Override
	public String toString() {
		return "Sightplace [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}
