package com.flip.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flip.enumeration.City;
import com.flip.enumeration.Country;
import com.flip.enumeration.State;

import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	private String nickName;

	private String addressLine1;

	private String addressLine2;

	private String area;

	private String pinCode;

	private String landMark;

	@Enumerated(EnumType.STRING)
	private City city;

	@Enumerated(EnumType.STRING)
	private State state;

	@Enumerated(EnumType.STRING)
	private Country country;

	private Boolean primaryAddress = Boolean.FALSE;

	@ManyToOne
	@JsonBackReference
	//@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID_X"
	@JoinColumn(name = "USER_ID")
	private UserInfo user;

}