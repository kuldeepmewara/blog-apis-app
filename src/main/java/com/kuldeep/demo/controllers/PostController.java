package com.kuldeep.demo.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.kuldeep.demo.config.AppConstants;
import com.kuldeep.demo.entities.Post;
import com.kuldeep.demo.payloads.ApiResponse;
import com.kuldeep.demo.payloads.PostDto;
import com.kuldeep.demo.payloads.PostResponse;
import com.kuldeep.demo.services.FileService;
import com.kuldeep.demo.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
			@RequestParam(value="pageNumber", defaultValue="0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="10",required=false) Integer pageSize
			)
	{
	   PostResponse postResponse=this.postService.getPostByUser(userId,pageNumber,pageSize);
	   
	   return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer catId,
			@RequestParam(value="pageNumber", defaultValue="0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="10",required=false) Integer pageSize
			
			)
	{
	   PostResponse postResponse=this.postService.getPostByCategory(catId,pageNumber,pageSize);
	   
	   return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			)
	{
	   PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
	   
	   return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
	   PostDto postDtos=this.postService.getPostById(postId);
	   
	   return new ResponseEntity<PostDto>(postDtos,HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePostbyId(@PathVariable Integer postId)
	{
	   this.postService.deletePost(postId);
	   
	   return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto post=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> updatePost(@PathVariable String keyword)
	{
		List<PostDto> posts=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	        
	{
	   PostDto postDto=this.postService.getPostById(postId);
	   String fileName=this.fileService.uploadImage(path, image);
	  
	   postDto.setImageName(fileName);
	   PostDto updatedPostDto=this.postService.updatePost(postDto, postId);
	   
	   return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);  
	}
	
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
	{
		InputStream resource=this.fileService.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
