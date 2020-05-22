package de.telekom.sea.mystuff.backend.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundAdvice {
	
	
	// Was muss ich bei     "  HttpStatus "   machen ??????????????????????????????????????????????????????? 
	
	/*
	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String resourceNotFoundHandler(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
	
	*/

}

