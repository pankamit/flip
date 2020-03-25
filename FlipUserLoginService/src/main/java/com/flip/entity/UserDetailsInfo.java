package com.flip.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsInfo implements UserDetails{

	private String userName;
	private String password;
	private List<GrantedAuthority> authoritiesLst;
	
	public UserDetailsInfo(UserInfo userInfo) {
		super();
		this.userName = userInfo.getMobile();
		this.password = userInfo.getPassword();
		this.authoritiesLst = new ArrayList<>();
		this.authoritiesLst.add(new SimpleGrantedAuthority("ROLE_"+userInfo.getRole().name()));

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authoritiesLst;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
