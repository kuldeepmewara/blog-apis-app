package com.kuldeep.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kuldeep.demo.payloads.UserDto;

@Service
public interface UserService {
	
	UserDto registerUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateuser(UserDto user ,Integer userId);
	
	UserDto getuserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	

}
