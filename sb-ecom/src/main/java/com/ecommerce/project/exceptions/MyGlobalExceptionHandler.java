package com.ecommerce.project.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.project.payload.APIResponse;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String, String> response = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach(err->{
			response.put(((FieldError)err).getField(), err.getDefaultMessage());
		});
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
		return new ResponseEntity<>(new APIResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> myAPIException(APIException e){
		return new ResponseEntity<>(new APIResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
	}
}
