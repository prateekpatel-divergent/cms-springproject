package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.Date;
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
 * Patient Class
 * 
 * @author Divergent
 *
 */
public class PatientDao {

	public static final String ID = "p_id";
	public static final String PNAME = "p_name";
	public static final String ADDRESS = "address";
	public static final String AGE = "age";
	public static final String WEIGHT = "weight";
	public static final String GENDER = "gender";
	public static final String CONTACTNO = "contactno";
	public static final String ACURRENTDATE = "acurrentdate";
	public static final String APPOINTMENTDATE = "appointmentdate";
	public static final String PROBLEM = "problem";

	ClinicDatabase clinicDatabase;

	private final static Logger LOGGER = Logger.getLogger(AppoinmentDao.class.getName());

	static {
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
	}

	/**
	 * Make Custructor
	 * 
	 * @param clinicDatabase
	 */
	public PatientDao(ClinicDatabase clinicDatabase) {
		this.clinicDatabase = clinicDatabase;
	}

	/**
	 * Delete Record By ID
	 * 
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public int delete(String pid) throws SQLException {
		Connection con = null;
		Statement st = null;

		con = clinicDatabase.getConnection();
		st = con.createStatement();

		st.executeUpdate("delete from patient where p_id = '" + pid + "';");

		st.close();
		con.close();
		return 1;

	}

	/**
	 * Search Record By ID
	 * 
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public Map searchById(String pid) throws SQLException {

		Connection con = null;
		Statement st = null;
		Map<String, String> map = new HashMap<String, String>();

		con = clinicDatabase.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select * from patient where p_id = '" + pid + "'");

		if (rs.next()) {
			map.put(ID, rs.getString(1));
			map.put(PNAME, rs.getString(2));
			map.put(ADDRESS, rs.getString(3));
			map.put(AGE, rs.getString(4));
			map.put(WEIGHT, rs.getString(5));
			map.put(GENDER, rs.getString(6));
			map.put(CONTACTNO, rs.getString(7));
			map.put(ACURRENTDATE, rs.getString(8));
			map.put(APPOINTMENTDATE, rs.getString(9));
			map.put(PROBLEM, rs.getString(10));
			st.close();
			con.close();
		}
		return map;
	}

	/**
	 * Insert Record Into Database By Parameter
	 * 
	 * @param pid
	 * @param pname
	 * @param address
	 * @param age
	 * @param weight
	 * @param gender
	 * @param contactno
	 * @param curdate
	 * @param appoinmentdate
	 * @param problem
	 * @return
	 * @throws SQLException
	 */
	public int insert(String pid, String pname, String address, String age, String weight, String gender,
			String contactno, String curdate, String appoinmentdate, String problem) throws SQLException {

		Connection con = null;

		con = clinicDatabase.getConnection();
		String sql = "insert into patient(P_ID,P_NAME,Addresss,Age,weight,Gender,Contact_No,ACurrent_Date,Appoiment_Date,Problem) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, ID);
		stmt.setString(2, PNAME);
		stmt.setString(3, ADDRESS);
		stmt.setString(4, AGE);
		stmt.setString(5, WEIGHT);
		stmt.setString(6, GENDER);
		stmt.setString(7, CONTACTNO);
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		stmt.setDate(8, date);
		stmt.setString(9, APPOINTMENTDATE);
		stmt.setString(10, PROBLEM);
		int i = stmt.executeUpdate();
		LOGGER.fine("\ninserted record successfully...");
		con.close();
		return i;
	}

	/**
	 * List of all Patient
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> list() throws SQLException {
		Connection con = null;
		Statement st = null;
		con = clinicDatabase.getConnection();
		st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from patient");
		List<Map<String, String>> doctorList = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> doctorRecord = new HashMap<String, String>();
			doctorRecord.put(ID, rs.getString(1));
			doctorRecord.put(PNAME, rs.getString(2));
			doctorRecord.put(ADDRESS, rs.getString(3));
			doctorRecord.put(AGE, rs.getString(4));
			doctorRecord.put(WEIGHT, rs.getString(5));
			doctorRecord.put(GENDER, rs.getString(6));
			doctorRecord.put(CONTACTNO, rs.getString(7));
			doctorRecord.put(ACURRENTDATE, rs.getString(8));
			doctorRecord.put(APPOINTMENTDATE, rs.getString(9));
			doctorRecord.put(PROBLEM, rs.getString(10));
			doctorList.add(doctorRecord);
		}
		return doctorList;
	}

}
