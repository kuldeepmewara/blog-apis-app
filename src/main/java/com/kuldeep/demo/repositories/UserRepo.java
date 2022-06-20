package com.kuldeep.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuldeep.demo.entities.MyUser;

public interface UserRepo extends JpaRepository<MyUser, Integer> {
	
	Optional<MyUser> findByEmail(String email);

}
