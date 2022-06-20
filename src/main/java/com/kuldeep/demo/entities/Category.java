package com.kuldeep.demo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name="title",length=100,nullable=false)
	private String categoryTitle;
	
	@Column(name="description")
	private String categoryDescription;
	
	
	//category is here variable name of post
	//fetch lazy parent ask then child wont be searched
	@OneToMany(mappedBy="category", cascade=CascadeType.ALL,fetch =FetchType.LAZY)
	private List<Post> posts =new ArrayList();
	

}

/*
 * category entity
 * category dto
 * category repo interface extends jpa
 * category service interface declare function
 * categoy service implementation class  define function
 * category controler define mapping for th0se fucntion
 * add validation
 * 
 * */
