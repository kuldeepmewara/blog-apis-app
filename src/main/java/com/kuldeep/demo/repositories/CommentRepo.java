package com.kuldeep.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuldeep.demo.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
