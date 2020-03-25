package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flip.dto.LoginUserInfoDto;
import com.flip.dto.OtpRequest;
import com.flip.dto.Response;
import com.flip.entity.AuthenticationResponse;
import com.flip.entity.UserInfo;
import com.flip.service.OtpService;
import com.flip.service.UserInfoService;
import com.flip.util.JwtUtil;

@RestController
@RibbonClient(name = "hello-service", configuration = HelloServiceConfiguration.class)
//@RequestMapping(value = "/user-service-config")
public class UserInfoController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private OtpService otpService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping("/addUser")
	public Response addUser(@RequestBody UserInfo userInfo) {
		return Response.builder().data(userInfoService.addUser(userInfo)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@PutMapping("/updateUser/{userId}")
	public Response updateUser(@RequestBody UserInfo userInfo, @PathVariable("userId") String userId) {
		return Response.builder().data(userInfoService.updateUser(userInfo, userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@DeleteMapping("/deleteUser/{iserId}")
	public Response deleteUser(@PathVariable("userId") String userId) {
		return Response.builder().data(userInfoService.deleteUser(userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@GetMapping("/user/{userId}")
	public Response getUserById(@PathVariable("userId") String userId) {
		return Response.builder().data(userInfoService.getUserById(userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@GetMapping("/allUser")
	public Response getAllUser() {
		return Response.builder().data(userInfoService.getAllUsers()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/forgetpassword/{userId}")
	public Response forgetPassword(@PathVariable("userId") String userId) {
		return Response.builder().data(userInfoService.forgetPassword(userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/validateForgetpassword")
	public Response forgetPassword(@RequestBody OtpRequest otpRequest) {
		return Response.builder().data(otpService.validateOtp(otpRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	

	@GetMapping("/helloUser")
	public Response helloUser() {
		return Response.builder().data("hello user from User Service").success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody LoginUserInfoDto loginUserInfoDto)
			throws Exception {

		if (!userInfoService.validateUser(loginUserInfoDto)) {
			throw new RuntimeException("Incorrect username or password");
		}

//		try {
//		
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(loginUserInfoDto.getUserName(), loginUserInfoDto.getPassword())
//			);
//		}
//		catch (BadCredentialsException e) {
//			throw new Exception("Incorrect username or password", e);
//		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUserInfoDto.getUserName());

		final String jwt = jwtUtil.generateToken(userDetails);

		//return ResponseEntity.builder().httpStatus(HttpStatus.OK).data(jwt).build();
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
