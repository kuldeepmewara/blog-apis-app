package com.kuldeep.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuldeep.demo.entities.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByEmail(String email);

}
