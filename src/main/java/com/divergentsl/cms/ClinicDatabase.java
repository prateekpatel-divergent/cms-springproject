package com.divergentsl.cms;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * In Which Get Url ,Username, Password Get
 * 
 * @author Divergent
 *
 */
public interface ClinicDatabase {
	final String URL = "jdbc:mysql://localhost:3306/clinic_Managment_System";
	final String USERNAME = "root";
	final String PASSWORD = "root";
	
	public Connection getConnection() throws SQLException;
}
