package com.kuldeep.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuldeep.demo.entities.Category;
import com.kuldeep.demo.entities.Post;
import com.kuldeep.demo.entities.Users;

public interface PostRepo extends JpaRepository<Post, Integer> {
	

	Page<Post> findByUser(Users user, Pageable p);

	Page<Post> findByCategory(Category category,Pageable p);
	
	//this method is fine but giving some error due to hibernate version issue in 5,6,6,5.6.7
	List<Post> findByTitleContaining(String keyword);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String string);

}
