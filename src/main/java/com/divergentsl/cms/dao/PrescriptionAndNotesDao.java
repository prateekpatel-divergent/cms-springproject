package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.divergentsl.cms.ClinicDatabase;


/**
 * Prescription And Notes Class
 * 
 * @author Divergent
 *
 */
public class PrescriptionAndNotesDao {

	public static final String PREID = "pre_ID";
	public static final String PID = "P_ID";
	public static final String PRESCRIPTION = "dprescription";
	public static final String NOTE = "dnotes";
	public static final String DID = "d_id";

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
	 * @param dataBaseManager
	 */
	public PrescriptionAndNotesDao(ClinicDatabase dataBaseManager) {
		this.clinicDatabase = dataBaseManager;
	}

	/**
	 * Insert Record Into Prescription and Notes
	 * 
	 * @param preId
	 * @param pId
	 * @param prescription
	 * @param note
	 * @param dId
	 * @return
	 * @throws SQLException
	 */
	public int insert(String preId, String pId, String prescription, String note, String dId) throws SQLException {
		Connection con = null;
		con = clinicDatabase.getConnection();
		String sql = "insert into prescription values(?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, preId);
		stmt.setString(2, pId);
		stmt.setString(3, prescription);
		stmt.setString(4, note);
		stmt.setString(5, dId);
		int i = stmt.executeUpdate();
		LOGGER.fine("\ninserted record successfully...");
		con.close();
		return i;
	}

}
