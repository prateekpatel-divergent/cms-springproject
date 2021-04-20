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
 * Drug Class
 * 
 * @author Divergent
 *
 */
public class DrugDao {

	public static final String ID = "did";
	public static final String NAME = "dname";
	public static final String RATE = "rate";

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
	public DrugDao(ClinicDatabase clinicDatabase) {
		this.clinicDatabase = clinicDatabase;
	}

	/**
	 * Delete Method by Id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int delete(String id) throws SQLException {
		Connection con = null;
		Statement st = null;
		con = clinicDatabase.getConnection();
		st = con.createStatement();
		st.executeUpdate("delete from drug where d_id = '" + id + "'");
		st.close();
		con.close();
		return 1;

	}

	/**
	 * Search Record By Id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Map searchById(String id) throws SQLException {

		Connection con = null;
		Statement st = null;
		Map<String, String> map = new HashMap<String, String>();

		con = clinicDatabase.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select * from drug where d_id = '" + id + "'");

		if (rs.next()) {
			map.put(ID, rs.getString(1));
			map.put(NAME, rs.getString(2));
			map.put(RATE, rs.getString(3));
			st.close();
			con.close();
		}
		return map;
	}

	/**
	 * Insert Record Into Data Base By Insert Data
	 * 
	 * @param id
	 * @param name
	 * @param rate
	 * @return
	 * @throws SQLException
	 */
	public int insert(String id, String name, String rate) throws SQLException {
		Connection con = null;

		con = clinicDatabase.getConnection();
		String sql = "insert into drug values(?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, ID);
		stmt.setString(2, NAME);
		stmt.setString(3, RATE);
		int i = stmt.executeUpdate();
		LOGGER.fine("\ninserted record successfully...");
		con.close();
		return i;
	}

	/**
	 * List Of All Record into DataBase
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> list() throws SQLException {
		Connection con = null;
		Statement st = null;
		con = clinicDatabase.getConnection();
		st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from drug");
		List<Map<String, String>> durgList = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ID, rs.getString(1));
			map.put(NAME, rs.getString(2));
			map.put(RATE, rs.getString(3));
			durgList.add(map);
		}
		return durgList;
	}

}
