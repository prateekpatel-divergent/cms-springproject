package com.divergentsl.cms;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.PatientDao;

/**
 * All Patient CRUD Operation Is done
 * 
 * @author Divergent
 *
 */
public class Patient {

	static Scanner sc = new Scanner(System.in);
	static Connection con;

	private final static Logger LOGGER;
	private static PatientDao patientDao;

	static {
		LOGGER = Logger.getLogger(Patient.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);

		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		patientDao = context.getBean("patientDao", PatientDao.class);
	}

	private Patient() {

	}

	/**
	 * Show All OPtion Of Admin to CRUD Operation
	 */
	public static void showAll() {
		System.out.println(
				"1. Insert Patient Data \n  2. Update Patient Data \n  3. Search Patient Data \n  4. Delete Patient Data \n  5. List All Patient \n  6. Back");
	}

	/**
	 * Select Option by this method
	 */
	public static void patientPanel() {
		while (true) {
			sc = new Scanner(System.in);
			showAll();
			String input = sc.nextLine();
			switch (input) {
			case "1":
				insertPatientData();
				break;
			case "2":
//				updatePatientData();
				break;
			case "3":
				searchPatientData();
				break;
			case "4":
				deletePatientData(null);
				break;
			case "5":
				listAllPatientData();
				break;
			case "6":
				Admin.executedMethod();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Insert Patient Data into Map
	 * 
	 * @return
	 */
	public static Map<String, String> inputPatientData() {
		System.out.println("Enter Patient_ID,Name,Address,Age,Weight,Gender,Contact_No,AppoimentDate,Problem");
		String pid = sc.nextLine();
		String pname = sc.nextLine();
		String paddress = sc.nextLine();
		String page = sc.nextLine();
		String pweight = sc.nextLine();
		String pgender = sc.nextLine();
		String pcontactno = sc.nextLine();
		String pappoimentdate = sc.nextLine();
		String pproblem = sc.nextLine();
		Map<String, String> map = new HashMap<>();
		map.put("1", pid);
		map.put("2", pname);
		map.put("3", paddress);
		map.put("4", page);
		map.put("5", pweight);
		map.put("6", pgender);
		map.put("7", pcontactno);
		map.put("9", pappoimentdate);
		map.put("10", pproblem);
		return map;
	}

	/**
	 * In Get Map Data above Method and Store by Insert Query
	 */
	public static void insertPatientData() {
		try {
			patientDao.insert("pid", "pname", "address", "age", "weight", "gender", "contactno", "appoinmentdate",
					"currentdate", "problem");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Patient Data Update
	 */
	public static void updatePatientData(String pproblem, String pid) {
		try {
			String sql = "UPDATE patient SET problem = '" + pproblem + "' where p_id= '" + pid + "';";
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Search Patient Data By Id
	 */
	public static void searchPatientData() {
		LOGGER.fine("\nEnter Patient Id :");
		Scanner sc = new Scanner(System.in);
		String did = sc.nextLine();
		try {

			Map<String, String> patient = patientDao.searchById(did);

			if (patient.size() != 0) {
				System.out.println(
						"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
				System.out.printf("%5s  %15s  %15s  %3s  %4s  %6s  %12s  %12s  %12s  %20s\n",
						patient.get(PatientDao.ID), patient.get(PatientDao.PNAME), patient.get(PatientDao.ADDRESS),
						patient.get(PatientDao.AGE), patient.get(PatientDao.WEIGHT), patient.get(PatientDao.GENDER),
						patient.get(PatientDao.CONTACTNO), patient.get(PatientDao.ACURRENTDATE),
						patient.get(PatientDao.APPOINTMENTDATE), patient.get(PatientDao.PROBLEM));
				System.out.println(
						"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			} else {
				LOGGER.fine("Record is not Found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Delete Patient Data
	 */
	public static void deletePatientData(String pid) {
		System.out.println("\nEnter Patient Id :");
		Scanner sc = new Scanner(System.in);
		String did = sc.nextLine();
		try {
			if (patientDao.searchById(did).size() == 0) {
				LOGGER.fine("\nPatient not found!");
			} else {
				try {
					patientDao.delete(did);
					LOGGER.fine("\nRecord Deleted Successfully...");
				} catch (SQLException e) {
					e.printStackTrace();
					LOGGER.fine(e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Show All Patient Data
	 */
	public static void listAllPatientData() {
		try {
			System.out.println(
					"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			List<Map<String, String>> patientList = patientDao.list();

			for (Map<String, String> patient : patientList) {
				System.out.printf("%5s  %15s  %15s  %3s  %4s  %6s  %12s  %12s  %12s  %20s\n",
						patient.get(PatientDao.ID), patient.get(PatientDao.PNAME), patient.get(PatientDao.ADDRESS),
						patient.get(PatientDao.AGE), patient.get(PatientDao.WEIGHT), patient.get(PatientDao.GENDER),
						patient.get(PatientDao.CONTACTNO), patient.get(PatientDao.ACURRENTDATE),
						patient.get(PatientDao.APPOINTMENTDATE), patient.get(PatientDao.PROBLEM));
			}
			System.out.println(
					"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Generate Invoice Of Patient
	 */
	public static void generateInvoice() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(ClinicDatabase.URL, ClinicDatabase.USERNAME,
					ClinicDatabase.PASSWORD);
			Statement st = con.createStatement();
			System.out.println("\nEnter Patient Id");
			String id = sc.nextLine();
			String sql = "select appoinment.P_ID,appoinment.P_Name,appoinment.ACurrent_Date,appoinment.Problem,doctor.D_Name,doctor.fee\r\n"
					+ "from appoinment join doctor on appoinment.d_id = doctor.D_Id where appoinment.p_id ='" + id
					+ "';";
			ResultSet rs = st.executeQuery(sql);

			if (!rs.next()) {
				LOGGER.fine("Record Is not Found!\n");
				Doctor.printDoctorOptions();
			} else {
				System.out.println(
						"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Patient Invoice*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
				String pid = rs.getString(1);
				String pname = rs.getString(2);
				Date pcurrentdate = rs.getDate(3);
				String pproblem = rs.getString(4);
				String dname = rs.getString(5);
				String fee = rs.getString(6);
				System.out.printf("%30s  %30s\n", "Patient Id :" + pid, "Date : " + pcurrentdate);
				System.out.printf("%30s %30s\n", "Patient Name :" + pname, "Problem :" + pproblem);
				System.out.printf("%30s %30s\n\n", "Docter Name :" + dname, "Fee :" + fee);
				System.out.println(
						"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			}
			Doctor.printDoctorOptions();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

}
