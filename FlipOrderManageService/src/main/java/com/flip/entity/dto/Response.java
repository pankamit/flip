package com.flip.entity.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {

	Response(){
		
	}

	public Response(Boolean success, HttpStatus httpStatus, Object data, String errorCode, String errorDesc) {
		super();
		this.success = success;
		this.httpStatus = httpStatus;
		this.data = data;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}



	private Boolean success = false;
	
	private HttpStatus httpStatus = HttpStatus.OK;
	
	private Object data;
	
	private String errorCode;
	
	private String errorDesc;
	
}
