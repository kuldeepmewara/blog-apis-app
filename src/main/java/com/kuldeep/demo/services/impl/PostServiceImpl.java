package com.kuldeep.demo.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kuldeep.demo.entities.Category;
import com.kuldeep.demo.entities.Post;
import com.kuldeep.demo.entities.User;
import com.kuldeep.demo.exceptions.ResourceNotFoundException;
import com.kuldeep.demo.payloads.PostDto;
import com.kuldeep.demo.payloads.PostResponse;
import com.kuldeep.demo.repositories.CategoryRepo;
import com.kuldeep.demo.repositories.PostRepo;
import com.kuldeep.demo.repositories.UserRepo;
import com.kuldeep.demo.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer catId) {
		

		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		
		Category cat=this.categoryRepo.findById(catId).
				orElseThrow(()->new ResourceNotFoundException("category", "category id", catId));
		
		
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(cat);
		post.setUser(user);
		
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		post.setContent(postDto.getContent());
	    post.setTitle(postDto.getTitle());	
		post.setImageName(postDto.getImageName());
		
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
		
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
			 sort=Sort.by(sortBy).ascending();
		else
			 sort=Sort.by(sortBy).descending();
			
		//pagination 
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost =this.postRepo.findAll(p);
		
		List<Post> posts=pagePost.getContent();
		
		//pagination
		
		List<PostDto> postDtos=posts.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
		
	}

	@Override
	public PostResponse getPostByCategory(Integer catId,Integer pageNumber,Integer pageSize) {
		
		Category category=this.categoryRepo.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("category", "category id", catId));
		
		Pageable p =PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost= this.postRepo.findByCategory(category,p);
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> postDtos=posts.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
		
	}

	@Override
	public PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize) {
		User user =this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost =this.postRepo.findByUser(user,p);
		
		List<Post> posts=pagePost.getContent();
		//List<Post> posts=this.postRepo.findByUser(user);
		
		List<PostDto> postDtos=posts.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		//List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		
		List<Post> posts=this.postRepo.searchByTitle("%"+keyword+"%");
		
		
		List<PostDto> postDtos=posts.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
