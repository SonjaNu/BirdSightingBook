package com.example.BirdSightingBook.domain;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Finding {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	private String name;
	private String modifiedName;
	private String day;
	private String month;
	private String year;
	private String count;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties("findings")
	@JoinColumn(name = "birdid", referencedColumnName = "id")
	private Bird bird;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties("findings")
	@JoinColumn(name = "sightplaceid", referencedColumnName = "id")
	private Sightplace sightplace;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties({"findings", "birds"})
	@JoinColumn(name = "specieid", referencedColumnName = "id")
	private Specie specie;
	
	@ManyToMany(targetEntity = Book.class, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Book> books;
	
	

	public Finding() {
		super();
		this.name = null;
		this.modifiedName = null;
		this.day = null;
		this.month = null;
		this.year = null;
		this.count = null;
		this.bird = null;
		this.sightplace = null;
		this.specie = null;
		
	}



	public Finding(Long id, String name, String modifiedName, String day, String month, String year, String count,
			Bird bird, Sightplace sightplace, Specie specie) {
		super();
		this.id = id;
		this.name = name;
		this.modifiedName = modify(name);
		this.day = day;
		this.month = month;
		this.year = year;
		this.count = count;
		this.bird = bird;
		this.sightplace = sightplace;
		this.specie = specie;
		this.books = null;
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
		this.modifiedName = modify(name);
	}



	public String getModifiedName() {
		return modifiedName;
	}



	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}



	public String getDay() {
		return day;
	}



	public void setDay(String day) {
		this.day = day;
	}



	public String getMonth() {
		return month;
	}



	public void setMonth(String month) {
		this.month = month;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getCount() {
		return count;
	}



	public void setCount(String count) {
		this.count = count;
	}



	public Bird getBird() {
		return bird;
	}



	public void setBird(Bird bird) {
		this.bird = bird;
	}



	public Sightplace getSightplace() {
		return sightplace;
	}



	public void setSightplace(Sightplace sightplace) {
		this.sightplace = sightplace;
	}



	public Specie getSpecie() {
		return specie;
	}



	public void setSpecie(Specie specie) {
		this.specie = specie;
	}



	public List<Book> getBooks() {
		return books;
	}



	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addToBook(Book book) {
		this.books.add(book);
	}
	
	public void deleteFromBook(Book book) {
		this.books.remove(book);
	}

	public String modify(String word) {
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
		return "Finding [id=" + id + ", day=" + day + ", month=" + month + ", year=" + year + ", count=" + count
				+ ", name=" + modify(name) + ", bird=" + bird + ", sightplace=" + sightplace + ", specie=" + specie
				+ "]";
	}
	
	
	
	

}
