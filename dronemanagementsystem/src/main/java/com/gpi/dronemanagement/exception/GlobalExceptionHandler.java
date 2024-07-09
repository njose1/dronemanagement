package com.gpi.dronemanagement.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DroneAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> handleDroneAlreadyExistException(DroneAlreadyExistException ex,
			WebRequest req) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), "DRONE_EXISTS_ALREADY",
				req.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(DroneNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleDroneNotFoundException(DroneNotFoundException ex, WebRequest req) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), "DRONE_NOT_FOUND",
				req.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(InvalidDronePositionException.class)
	public ResponseEntity<ErrorDetails> handleInvalidDronePositionException(InvalidDronePositionException ex,
			WebRequest req) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), "INVALID_DRONE_POSITION",
				req.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
