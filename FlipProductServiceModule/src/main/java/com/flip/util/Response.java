package com.flip.util;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {

	private Boolean success = false;
	
	private HttpStatus httpStatus = HttpStatus.OK;
	
	private Object data;
	
	private String errorCode;
	
	private String errorDesc;
	
}
