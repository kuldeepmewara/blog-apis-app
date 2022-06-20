package com.kuldeep.demo.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
	
	private Integer Id;
	
	private String content;
	
	private Date date;

}
