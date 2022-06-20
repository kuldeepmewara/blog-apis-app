package com.kuldeep.demo.entities;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;

	private String content;
	
	private Date date;
	
	@ManyToOne
	private Post post;
	
	@ManyToOne
	private MyUser myuser;
}
