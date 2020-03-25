package com.flip.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import lombok.Data;

@Data
public class IdGenerator implements IdentifierGenerator {

	private static String categoryCode;
	
	private static String subCategoryCode;
	
	private static String brandCode;
	
	private static Long categoryId;
	
	private static Long subCategoryId;
	
	private static Long brandId;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		Connection connection = session.connection();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st
					.executeQuery("Select count(ID) from PRODUCT_DETAIL where CATEGORY_ID = '" + categoryId + "' AND SUB_CATEGORY_ID = '"+subCategoryId+"' AND BRAND_ID = '"+brandId+"'");
			if (rs.next()) {

				int nextNo = rs.getInt(1) + 1;

				return categoryCode+"0"+subCategoryCode+"0"+brandCode+"0"+ Integer.valueOf(nextNo).toString();
				
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static String getCategoryCode() {
		return categoryCode;
	}

	public static void setCategoryCode(String categoryCode) {
		IdGenerator.categoryCode = categoryCode;
	}

	public static String getSubCategoryCode() {
		return subCategoryCode;
	}

	public static void setSubCategoryCode(String subCategoryCode) {
		IdGenerator.subCategoryCode = subCategoryCode;
	}

	public static String getBrandCode() {
		return brandCode;
	}

	public static void setBrandCode(String brandCode) {
		IdGenerator.brandCode = brandCode;
	}

	public static Long getCategoryId() {
		return categoryId;
	}

	public static void setCategoryId(Long categoryId) {
		IdGenerator.categoryId = categoryId;
	}

	public static Long getSubCategoryId() {
		return subCategoryId;
	}

	public static void setSubCategoryId(Long subCategoryId) {
		IdGenerator.subCategoryId = subCategoryId;
	}

	public static Long getBrandId() {
		return brandId;
	}

	public static void setBrandId(Long brandId) {
		IdGenerator.brandId = brandId;
	}
	
	
	

}
