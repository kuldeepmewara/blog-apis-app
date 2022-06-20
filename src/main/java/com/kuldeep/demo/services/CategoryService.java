package com.kuldeep.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kuldeep.demo.payloads.CategoryDto;

@Service
public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryid);
	
	//delete
	public void  deleteCategory(Integer categoryDto);
	
	//get
	CategoryDto getCategory(Integer categoryId);
	
	//get all
	List<CategoryDto> getAllCategory();

}
