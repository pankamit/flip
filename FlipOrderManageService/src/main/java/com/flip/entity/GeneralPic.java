package com.flip.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity
@Data
public class GeneralPic implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Lob
	private byte[] images;

	public GeneralPic(byte[] images) {
		super();
		this.images = images;
	}

	public GeneralPic() {
		super();
	}
	
	

}
