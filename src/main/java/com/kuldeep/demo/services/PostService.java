package com.kuldeep.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kuldeep.demo.entities.Category;
import com.kuldeep.demo.entities.Post;
import com.kuldeep.demo.entities.Users;
import com.kuldeep.demo.payloads.PostDto;
import com.kuldeep.demo.payloads.PostResponse;

@Service
public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer catId);
	
	//getPost by category
	PostResponse getPostByCategory(Integer catId,Integer pageNumber,Integer pageSize);
		
	//getPost by user
    PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize);
	
	//get all
	PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);
		
	//get single
	PostDto getPostById(Integer postId);
		
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//search post
	List<PostDto> searchPost(String keyword);
	
	
	

}
