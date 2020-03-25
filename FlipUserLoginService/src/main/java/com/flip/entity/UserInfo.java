package com.flip.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flip.enumeration.Roles;

import lombok.Data;

@Data
@Entity
public class UserInfo {

	@Id
	@GenericGenerator(name = "sequence_id_gen", strategy = "com.flip.generator.IdGenerator")
	@GeneratedValue(generator = "sequence_id_gen") 
	private String id;

	private String firstName;
	
	private String lastName;

	private String mobile;

	private String email;

	private String password;

	@Enumerated(EnumType.STRING)
	private Roles role;
	
	//@JsonProperty(access = Access.READ_ONLY)
	@OneToMany(targetEntity = Address.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Address> addressLst = new ArrayList<>();
	
}
