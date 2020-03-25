package com.flip.service;

import java.util.List;

import com.flip.entity.Address;

public interface AddressService {
	
	public Address addAddress(Address address,String userId);
	
	public Address deleteAddress(Long addressId);
	
	public Address updateAddress(Address address,Long addressId);
	
	public List<Address> getAllAddresss();
	
	public Address getAddressById(Long addressId);
	

}
