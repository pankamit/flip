package com.flip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flip.entity.Address;
import com.flip.service.AddressService;

@RestController
@RequestMapping("/user-service-config")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping("/addAddress/{userId}")
	public Address addAddress(@RequestBody Address address,@PathVariable("userId") String userId){		
		return addressService.addAddress(address,userId);
	}
	
	@PutMapping("/updateAddress/{userId}")
	public Address updateAddress(@RequestBody Address address,@PathVariable("addressId") Long addressId) {
		return addressService.updateAddress(address, addressId);
	}
	
	@DeleteMapping("/deleteAddress/{addressId}")
	public Address deleteAddress(@PathVariable("addressId") Long addressId) {
		 return addressService.deleteAddress(addressId);
	}
	
	@GetMapping("/address/{addressId}")
	public Address getAddressById(@PathVariable("addressId") Long addressId) {
		return addressService.getAddressById(addressId);
	}
	
	@GetMapping("/allAddress")
	public List<Address> getAllAddress() {
		return addressService.getAllAddresss();
	}
	
	@GetMapping("/helloAddress")
	public String helloAddress() {
		return "hello user";
	}
}
