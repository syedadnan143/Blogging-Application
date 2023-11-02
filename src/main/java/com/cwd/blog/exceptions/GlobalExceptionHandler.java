package com.cwd.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cwd.blog.payloads.ApiResponse;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResoueceNotFountException.class)
	public ResponseEntity<ApiResponse>resouecsNotFountException(ResoueceNotFountException ex){
	  String message = ex.getMessage();
	  ApiResponse apiResponse = new ApiResponse(message, false);
	  return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
    
    public ResponseEntity<Map<String, String>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    	Map<String, String> resp = new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error) ->{
    	   String fieldName = ((FieldError) error).getField();
    	   String message = error.getDefaultMessage();
    	   resp.put(fieldName, message);
    	});
    	return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ApiException.class)
   	public ResponseEntity<ApiResponse>handleApiException(ApiException ex){
   	  String message = ex.getMessage();
   	  ApiResponse apiResponse = new ApiResponse(message, true);
   	  return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
   	}
       
}
