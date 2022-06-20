package com.kuldeep.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuldeep.demo.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
