package com.kuldeep.demo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kuldeep.demo.config.AppConstants;
import com.kuldeep.demo.entities.Role;
import com.kuldeep.demo.entities.MyUser;
import com.kuldeep.demo.exceptions.ResourceNotFoundException;
import com.kuldeep.demo.payloads.UserDto;
import com.kuldeep.demo.repositories.RoleRepo;
import com.kuldeep.demo.repositories.UserRepo;
import com.kuldeep.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	

	//CREATE USER
	@Override
	public UserDto createUser(UserDto userDto) {
		
		MyUser user=this.dtoToUser(userDto);
		MyUser savedUser=this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	//UPDATE USER
	@Override
	public UserDto updateuser(UserDto userDto, Integer userId) {
		
		MyUser user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		this.userRepo.save(user);
		return this.userToDto(user);
	}

	//GET USER BY ID
	@Override
	public UserDto getuserById(Integer userId) {
		
		MyUser user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		
		return this.userToDto(user);
		
	}

	//GET ALL USERS
	@Override
	public List<UserDto> getAllUsers() {
		List<MyUser> users=this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream()
				.map(user->this.userToDto(user))
				.collect(Collectors.toList());
		return userDtos;
	}

	//DELETE USER BY ID
	@Override
	public void deleteUser(Integer userId) {
		
		MyUser user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
        this.userRepo.delete(user);
	}
	
	public  MyUser dtoToUser(UserDto userDto)
	{
		MyUser user=this.modelMapper.map(userDto, MyUser.class);
		
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	public UserDto userToDto(MyUser user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		MyUser user=this.modelMapper.map(userDto, MyUser.class);
		//encode password
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		MyUser newUser=this.userRepo.save(user);
		
		return this.modelMapper.map(newUser,UserDto.class);
	}

}
