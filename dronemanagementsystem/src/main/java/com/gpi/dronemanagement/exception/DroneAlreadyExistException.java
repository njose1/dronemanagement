package com.gpi.dronemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DroneAlreadyExistException extends RuntimeException{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4426624592009844408L;

	public DroneAlreadyExistException(String message){
		super(message);
	}
	

}
