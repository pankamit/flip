package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
}
