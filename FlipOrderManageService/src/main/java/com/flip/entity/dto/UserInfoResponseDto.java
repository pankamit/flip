package com.flip.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoResponseDto {

	private String id;
	
	private String firstName;
	
	private String lastName;

	private String mobile;

	private String email;

	private String password;
	
	private String fullName;

//	@Override
//	public String toString() {
//		return "UserInfoResponseDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile="
//				+ mobile + ", email=" + email + ", password=" + password + "]";
//	}
	
	
}
