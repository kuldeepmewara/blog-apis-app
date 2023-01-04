package com.kuldeep.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuldeep.demo.exceptions.ApiException;
import com.kuldeep.demo.exceptions.ResourceNotFoundException;
import com.kuldeep.demo.payloads.JwtAuthRequest;
import com.kuldeep.demo.payloads.JwtAuthResponse;
import com.kuldeep.demo.payloads.UserDto;
import com.kuldeep.demo.security.JwtTokenHelper;
import com.kuldeep.demo.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailService;
	@Autowired
	private AuthenticationManager  authenticationManager;
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
	{
		System.out.println("dss"+request.getUsername()+request.getPassword());
		
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetail=this.userDetailService.loadUserByUsername(request.getUsername());
		
		String token=this.jwtTokenHelper.generateToken(userDetail);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception{
		UsernamePasswordAuthenticationToken  authentication= new UsernamePasswordAuthenticationToken(username,password);
		
		try {
			this.authenticationManager.authenticate(authentication);
		}
		catch(BadCredentialsException e)
		{
			throw new ApiException("Invalid Username or password");
		}
	}
	
	//register user
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto)
	{
		//System.out.println(userDto.getEmail()+userDto.getName()+userDto.getAbout()+userDto.getPassword());
		
		UserDto registeredUser=this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.OK);
	}

}
