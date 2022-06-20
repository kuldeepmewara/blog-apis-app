package com.kuldeep.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuldeep.demo.payloads.ApiResponse;
import com.kuldeep.demo.payloads.CategoryDto;
import com.kuldeep.demo.payloads.UserDto;
import com.kuldeep.demo.services.CategoryService;
import com.kuldeep.demo.services.UserService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController  {
	
	@Autowired
	private CategoryService cateService;
	
	//POST user
	@PostMapping("/")
	public ResponseEntity<CategoryDto> creatCategory(@Valid @RequestBody  CategoryDto categoryDto)
	{
		CategoryDto createdCategoryDto=this.cateService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategoryDto,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
	{
		CategoryDto catDto= this.cateService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(catDto,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable Integer categoryId)
	{
		this.cateService.deleteCategory(categoryId);
		return  new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
	}
	
	
	
	//GET ALL category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
			return ResponseEntity.ok(this.cateService.getAllCategory());
	}
		
	//GET cstegory BY ID
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId)
	{
			return ResponseEntity.ok(this.cateService.getCategory(categoryId));
	}

}
