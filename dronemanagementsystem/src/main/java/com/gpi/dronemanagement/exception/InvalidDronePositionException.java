package com.gpi.dronemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDronePositionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3028810785254020513L;

	public InvalidDronePositionException(String message){
		super(message);
	}
}
