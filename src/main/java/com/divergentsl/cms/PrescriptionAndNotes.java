package com.divergentsl.cms;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.PrescriptionAndNotesDao;

/**
 * Prescription And Notes
 * 
 * @author Divergent
 *
 */
public class PrescriptionAndNotes {

	static Scanner sc = new Scanner(System.in);

	private final static Logger LOGGER;
	private static PrescriptionAndNotesDao prescriptionAndNotesDao;

	static {
		LOGGER = Logger.getLogger(Patient.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext();
		prescriptionAndNotesDao = context.getBean("prescriptionAndNotesDao",PrescriptionAndNotesDao.class);
	}

	/**
	 * Make Custructor
	 */
	private PrescriptionAndNotes() {

	}

	/**
	 * Get Information
	 * 
	 * @return
	 */
	public static Map<String, String> inputPrescriptionData() {

		System.out.println("Enter Prescription Id");
		String prescriId = sc.nextLine();
		System.out.println("Enter Patient Id");
		String patientId = sc.nextLine();
		System.out.println("Enter Prescription");
		String prescription = sc.nextLine();
		System.out.println("Enter Notes");
		String note = sc.nextLine();
		System.out.println("Enter doctor id");
		String doctorid = sc.nextLine();

		Map<String, String> map = new HashMap<String, String>();
		map.put("1", prescriId);
		map.put("2", patientId);
		map.put("3", prescription);
		map.put("4", note);
		map.put("5", doctorid);
		return map;
	}

	/**
	 * Patient Prescription
	 */
	public static void prescriptionPatient() {
		try {
			prescriptionAndNotesDao.insert("prescriId", "patientId", "prescription", "note", "doctorid");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}

	}

	/**
	 * History and Prescription of all Patient
	 */
	public static void historyAndPresciption() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(ClinicDatabase.URL, ClinicDatabase.USERNAME,
					ClinicDatabase.PASSWORD);
			Statement st = con.createStatement();
			String sql = "select patient.p_id,patient.p_name,patient.age,patient.gender,appoinment.d_id,appoinment.d_name,\r\n"
					+ "appoinment.appoinment_id,appoinment.problem,appoinment.appoinment_date,\r\n"
					+ "prescription.dprescription,prescription.dnotes\r\n"
					+ "from patient inner join appoinment on patient.p_id =  appoinment.p_id   \r\n"
					+ "inner join prescription on patient.p_id = prescription.p_id\r\n"
					+ "order by appoinment.appoinment_date desc;";
			ResultSet rs = st.executeQuery(sql);
			System.out.println("\n*-*-*-*-*-*-*-*-*-* History Of Patient *-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println(
					"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Patient Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			while (rs.next()) {
				String pid = rs.getString(1);
				String pname = rs.getString(2);
				String page = rs.getString(3);
				String pgender = rs.getString(4);
				String doctorId = rs.getString(5);
				String doctorname = rs.getString(6);
				String appoinId = rs.getString(7);
				String pproblem = rs.getString(8);
				String appoindate = rs.getString(9);
				String prescription = rs.getString(10);
				String note = rs.getString(11);
				System.out.printf("%5s  %20s  %5s  %7s  %5s  %20s  %3s  %15s  %15s  %25s  %25s\n", pid, pname, page,
						pgender, doctorId, doctorname, appoinId, pproblem, appoindate, prescription, note);
			}
			System.out.println(
					"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

}