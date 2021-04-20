package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.divergentsl.cms.ClinicDatabase;


/**
 * Admin Dao
 * 
 * @author Divergent
 *
 */
public class AdminDao {

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	ClinicDatabase clinicDatabase;

	/**
	 * Make Custructor AdminDao
	 * 
	 * @param clinicDatabase
	 */
	public AdminDao(ClinicDatabase clinicDatabase) {
		this.clinicDatabase = clinicDatabase;
	}

	/**
	 * Admin LOgin Method With Parameter
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean adminLogin(String username, String password) throws SQLException {

		Connection con = null;
		Statement st = null;

		con = clinicDatabase.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select * from administration where a_username = '" + username
				+ "' AND a_password = '" + password + "'");

		if (rs.next())
			return true;
		else
			return false;
	}
}
