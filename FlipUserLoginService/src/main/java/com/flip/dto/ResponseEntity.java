package com.flip.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseEntity {

	private Boolean success = false;
	
	private String data;
	
	private HttpStatus httpStatus;
	
	private String errorMessage;
	
	private String errorCode;
	
}
