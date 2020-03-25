package com.flip.service;

import java.util.List;

import com.flip.dto.LoginUserInfoDto;
import com.flip.entity.UserInfo;

public interface UserInfoService {
	
	public UserInfo addUser(UserInfo userInfo);
	
	public Boolean validateUser(LoginUserInfoDto loginUser);
	
	public UserInfo deleteUser(String userId);
	
	public UserInfo updateUser(UserInfo userInfo,String userId);
	
	public List<UserInfo> getAllUsers();
	
	public UserInfo getUserById(String userId);
	
	public String forgetPassword(String userId);

}
