package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {
    
	Optional<UserInfo> findByMobile(String mobile);
	
	Optional<UserInfo> findByMobileOrEmail(String mobile,String email);
	
}
