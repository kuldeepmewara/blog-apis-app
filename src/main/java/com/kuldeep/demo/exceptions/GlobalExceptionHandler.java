package com.kuldeep.demo.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kuldeep.demo.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//this is when resource is not found in the database
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	//this is for handling validation message when input given is not valid
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String>resp=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String FieldName=((FieldError)error).getField();
			String messgae=error.getDefaultMessage();
			resp.put(FieldName, messgae);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String,String>> handleHttpRequestMethodNotSupportedException(MethodArgumentNotValidException ex)
	{
		Map<String,String>resp=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String FieldName=((FieldError)error).getField();
			String messgae=error.getDefaultMessage();
			resp.put(FieldName, messgae);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
}
