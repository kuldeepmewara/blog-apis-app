package com.kuldeep.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuldeep.demo.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
