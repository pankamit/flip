package com.flip.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.flip.enumeration.ProductStatus;

import lombok.Data;

@Entity(name = "PRODUCT_DETAIL")
@Data
public class Product extends AuditModel{
	
	@Id
	@GenericGenerator(name = "sequence_id_gen", strategy = "com.flip.generator.IdGenerator")
	@GeneratedValue(generator = "sequence_id_gen")
	private String id;
	
	private String name;
	
	private String desc;
	
	@OneToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@OneToOne
	@JoinColumn(name = "SUB_CATEGORY_ID")
	private SubCategory subCategory;
	
	@OneToOne
	@JoinColumn(name = "BRAND_ID")
	private Brand brand;
	
	private String seller;
	
	@Embedded
//    @AttributeOverrides(value = {
//        @AttributeOverride(name = "product__Price", column = @Column(name = "PRDUCTPRICE"))
//    })
	private Price price;
		
	private Long quantity;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	private Double avgRatingPoints;
	
	private Integer noOfRatings;
	
	@OneToMany
	@JoinColumn(name = "PRODUCT_PIC_ID")
	private List<ProductPic> picLst = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	private List<Rating> ratingLst = new ArrayList<>();
	
	@OneToOne
	@Transient
	private MobileInfo mobileInfo;

	@OneToOne
	@Transient
	private LeptopInfo leptopInfo;
	
	@OneToOne
	@Transient
	private MenFootWearInfo menFootWearInfo;
	
	@OneToOne
	@Transient
	private MenTopWearInfo menTopWearInfo;
	
	
}
