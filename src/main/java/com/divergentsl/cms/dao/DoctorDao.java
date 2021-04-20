package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.divergentsl.cms.ClinicDatabase;

/**
 * Doctor Dao Class
 * 
 * @author Divergent
 *
 */
public class DoctorDao {
	public static final String ID = "did";
	public static final String NAME = "dname";
	public static final String SPECIALITY = "speciality";
	public static final String CONTACT_NO = "dcontact";
	public static final String FEE = "dfee";
	public static final String DEGREE = "ddegree";

	static ClinicDatabase clinicDatabase;

	private final static Logger LOGGER = Logger.getLogger(AppoinmentDao.class.getName());

	static {
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
	}

	/**
	 * Make Doctor Dao Custructor
	 * 
	 * @param clinicDatabase
	 */
	public DoctorDao(ClinicDatabase clinicDatabase) {
		this.clinicDatabase = clinicDatabase;
	}

	/**
	 * Delete Method by String Id
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 */
	public static int delete(String string) throws SQLException {
		Connection con = null;
		Statement st = null;
		con = clinicDatabase.getConnection();
		st = con.createStatement();
		int st1 = st.executeUpdate("delete from doctor where d_id = '" + string + "'");
		st.close();
		con.close();
		return st1;
	}

	/**
	 * Search Record by that Method
	 * 
	 * @param did
	 * @return
	 * @throws SQLException
	 */
	public static Map searchById(String did) throws SQLException {

		Connection con = null;
		Statement st = null;
		Map<String, String> map = new HashMap<String, String>();

		con = clinicDatabase.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select * from doctor where d_id ='" + did + "'");

		if (rs.next()) {
			map.put(ID, rs.getString(1));
			map.put(NAME, rs.getString(2));
			map.put(SPECIALITY, rs.getString(3));
			map.put(CONTACT_NO, rs.getString(4));
			map.put(FEE, rs.getString(5));
			map.put(DEGREE, rs.getString(6));
			st.close();
			con.close();
		}
		return map;
	}

	/**
	 * Insert Doctor Data By Insert Query And Parameter Below That
	 * 
	 * @param did
	 * @param dname
	 * @param speciality
	 * @param contactno
	 * @param fee
	 * @param degree
	 * @return
	 * @throws SQLException
	 */
	public static int insert(String did, String dname, String speciality, String contactno, String fee, String degree)
			throws SQLException {

		Connection con = null;
		Statement st = null;

		con = clinicDatabase.getConnection();
		String sql = "insert into doctor values(?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, ID);
		stmt.setString(2, NAME);
		stmt.setString(3, SPECIALITY);
		stmt.setString(4, CONTACT_NO);
		stmt.setString(5, FEE);
		stmt.setString(6, DEGREE);
		int i = stmt.executeUpdate();
		LOGGER.fine("\ninserted record successfully...");
		con.close();
		return i;
	}

	/**
	 * List Of All Doctor Data
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> list() throws SQLException {
		Connection con = null;
		Statement st = null;
		con = clinicDatabase.getConnection();
		st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from doctor");
		List<Map<String, String>> doctorList = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> doctorRecord = new HashMap<String, String>();
			doctorRecord.put(ID, rs.getString(1));
			doctorRecord.put(NAME, rs.getString(2));
			doctorRecord.put(SPECIALITY, rs.getString(3));
			doctorRecord.put(CONTACT_NO, rs.getString(4));
			doctorRecord.put(FEE, rs.getString(5));
			doctorRecord.put(DEGREE, rs.getString(6));
			doctorList.add(doctorRecord);
		}
		return doctorList;
	}

}
