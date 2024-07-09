package com.gpi.dronemanagement.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorDetails {
	
	private LocalDateTime timeStamp;
	private String message;
	private String errorcode;
	private String path;

}
