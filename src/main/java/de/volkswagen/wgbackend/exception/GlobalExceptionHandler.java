package de.volkswagen.wgbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
		return ResponseEntity.status(404).body(e.getMessage());
	}

	@ExceptionHandler(NoWgAssignedException.class)
	public ResponseEntity<String> handleNoWgAssignedException(NoWgAssignedException e) {
		return ResponseEntity.status(404).body("No Wg assigned");
	}
}
