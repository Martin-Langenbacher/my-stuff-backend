package de.telekom.sea.mystuff.backend.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	// nummer muss da sein und wurde automatisch erstellt !
	private static final long serialVersionUID = 7692858862244699678L;

	//private static final long serialVersionUID = 5909234906542641361L;

	ResourceNotFoundException(Long id) {
		super("Could not find resource " + id);
	}

}
