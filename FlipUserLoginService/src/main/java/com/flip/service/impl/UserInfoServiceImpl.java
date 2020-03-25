package com.flip.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.flip.config.producer.CartGenerationEventProducer;
import com.flip.config.producer.CartRemoveEventProducer;
import com.flip.config.producer.ForgetPasswordEventProducer;
import com.flip.dto.LoginUserInfoDto;
import com.flip.entity.UserDetailsInfo;
import com.flip.entity.UserInfo;
import com.flip.enumeration.Roles;
import com.flip.event.CartGenerationEvent;
import com.flip.event.ForgetPasswordEvent;
import com.flip.generator.IdGenerator;
import com.flip.repository.UserRepository;
import com.flip.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService, UserDetailsService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ForgetPasswordEvent forgetPasswordEvent;

	@Autowired
	private ForgetPasswordEventProducer forgetPasswordProducer;
	
	@Autowired
	private CartGenerationEventProducer cartGenerationEventProducer;
	
	@Autowired
	private CartRemoveEventProducer cartRemoveEventProducer;
	
	@Autowired
	private UserRepository userRpository;

	public UserInfo addUser(UserInfo userInfo) {

		bCryptPasswordEncoder = new BCryptPasswordEncoder();

		String pass = bCryptPasswordEncoder.encode(userInfo.getPassword());

		userInfo.setPassword(pass);
		IdGenerator.setRole(userInfo.getRole());
		UserInfo userInfoSaved = userRpository.save(userInfo);
		
		if(userInfoSaved.getRole().equals(Roles.USER)) {
			CartGenerationEvent cartGenerationEvent = new CartGenerationEvent();
			cartGenerationEvent.setUserId(userInfoSaved.getId());
			cartGenerationEventProducer.sendMessage(cartGenerationEvent);
		}

		return userInfoSaved;
	}

	@Override
	public Boolean validateUser(LoginUserInfoDto loginUser) {

		Optional<UserInfo> userInfoEntity = userRpository.findByMobileOrEmail(loginUser.getUserName(),
				loginUser.getUserName());

		userInfoEntity.orElseThrow(() -> new RuntimeException("user is not present"));

		UserInfo userInfo = userInfoEntity.get();

		if ((userInfo.getMobile().equals(loginUser.getUserName())
				|| userInfo.getEmail().equals(loginUser.getUserName()))
				&& isPasswordMatch(loginUser.getPassword(), userInfo.getPassword())) {
			return true;
		}
		return false;

	}

	private Boolean isPasswordMatch(String plainPassword, String hashPassword) {
		return BCrypt.checkpw(plainPassword, hashPassword);
	}

	@Override
	public UserInfo deleteUser(String userId) {
		Optional<UserInfo> userEntity = userRpository.findById(userId);
		if (!userEntity.isPresent())
			throw new RuntimeException("User is not present");

		userRpository.deleteById(userId);
		
		if(userEntity.get().getRole().equals(Roles.USER)) {
			CartGenerationEvent cartRemoveEvent = new CartGenerationEvent();
			cartRemoveEvent.setUserId(userId);
			cartRemoveEventProducer.sendMessage(cartRemoveEvent);
		}
		
		return userEntity.get();
	}

	@Override
	public UserInfo updateUser(UserInfo userInfo, String userId) {
		if (!userRpository.findById(userId).isPresent()) {
			throw new RuntimeException("user is not present");
		}
		return userRpository.save(userInfo);
	}

	@Override
	public List<UserInfo> getAllUsers() {
		return userRpository.findAll();
	}

	@Override
	public UserInfo getUserById(String userId) {
		Optional<UserInfo> userEntity = userRpository.findById(userId);
		if (!userEntity.isPresent()) {
			throw new RuntimeException("user is not present");
		}
		return userEntity.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userEntity = userRpository.findByMobile(username);

		if (!userEntity.isPresent())
			throw new RuntimeException("User is not available");

		return new UserDetailsInfo(userEntity.get());
	}

	@Override
	public String forgetPassword(String userId) {
		forgetPasswordEvent.setEmail(userId);
		forgetPasswordProducer.sendMessage(forgetPasswordEvent);
		return "";
	}
}
