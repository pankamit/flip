package com.flip.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.entity.Address;
import com.flip.entity.UserInfo;
import com.flip.repository.AddressRepository;
import com.flip.repository.UserRepository;
import com.flip.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Address addAddress(Address address,String userId) {
		Optional<UserInfo> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new RuntimeException("User is not present");
		address.setUser(user.get());
		return addressRepository.save(address);
	}

	@Override
	public Address deleteAddress(Long addressId) {
		Optional<Address> addressEntity = addressRepository.findById(addressId);
		if(!addressEntity.isPresent())
			throw new RuntimeException("Address is not present");
		 addressRepository.deleteById(addressId);
		 return addressEntity.get();
	}

	@Override
	public Address updateAddress(Address address, Long addressId) {
		if(!addressRepository.findById(addressId).isPresent()) {
			throw new RuntimeException("Address is not present");
		}
		return addressRepository.save(address);
	}

	@Override
	public List<Address> getAllAddresss() {
		return addressRepository.findAll();
	}

	@Override
	public Address getAddressById(Long addressId) {
		return addressRepository.getOne(addressId);
	}

}
