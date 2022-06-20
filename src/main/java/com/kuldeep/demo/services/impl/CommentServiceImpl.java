package com.kuldeep.demo.services.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuldeep.demo.entities.Comment;
import com.kuldeep.demo.entities.Post;
import com.kuldeep.demo.exceptions.ResourceNotFoundException;
import com.kuldeep.demo.payloads.CommentDto;
import com.kuldeep.demo.repositories.CommentRepo;
import com.kuldeep.demo.repositories.PostRepo;
import com.kuldeep.demo.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(post.getUser());
		comment.setDate(new Date());
		
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment id", commentId));
		this.commentRepo.delete(com);
		
	}

}
