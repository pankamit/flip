package com.flip.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.flip.enumeration.Roles;

public class IdGenerator implements IdentifierGenerator {

	private static Roles role;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		Connection connection = session.connection();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st
					.executeQuery("Select count(ID) from User_Info where role = '" + role + "'");
			if (rs.next()) {

				int nextNo = rs.getInt(1) + 1;

				return role + Integer.valueOf(nextNo).toString();
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	public static Roles getRole() {
		return role;
	}

	public static void setRole(Roles role) {
		IdGenerator.role = role;
	}

	

}
