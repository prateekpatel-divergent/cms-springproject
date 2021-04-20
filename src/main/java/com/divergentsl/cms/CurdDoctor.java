package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.DoctorDao;



public class CurdDoctor {

	static Scanner sc = new Scanner(System.in);

	private final static Logger LOGGER;
	private static DoctorDao doctorDao;
	
	static {
		LOGGER = Logger.getLogger(ClinicManagmentSystem.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		doctorDao = context.getBean("doctorDao",DoctorDao.class);
	}

	private CurdDoctor() {

	}

	/**
	 * It show Option on console
	 */
	public static void showCRUDDoctor() {
		System.out.println("1. Insert Doctor Data");
		System.out.println("2. Update Doctor Data");
		System.out.println("3. Search Doctor Data");
		System.out.println("4. Delete Doctor Data");
		System.out.println("5. List All Doctor");
		System.out.println("6. Back");
	}

	/**
	 * It Select Option on Console Panel to choice on It.
	 */
	public static void docterPanel() {
		while (true) {
			System.out.println("Enter Your Choice : ");

			showCRUDDoctor();
			String input = sc.nextLine();
			switch (input) {
			case "1":
				insertDoctorData();
				break;
			case "2":
				break;
			case "3":
				searchDoctorData();
				break;
			case "4":
				deleteDoctorData();
				break;
			case "5":
				listAllDoctor();
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
	 * Get Data By Admit for insert data
	 * 
	 * @return
	 */
	public static Map<String, String> inputDoctorData() {
		System.out.println("Enter Doctor_Id");
		String did = sc.nextLine();
		System.out.println("Enter Doctor Name");
		String dname = sc.nextLine();
		System.out.println("Enter Speciaslity");
		String dspecia = sc.nextLine();
		System.out.println("Enter ContactNo");
		String dcontact = sc.nextLine();
		System.out.println("Enter Fee");
		String dfee = sc.nextLine();
		System.out.println("Enter Degree");
		String ddegree = sc.nextLine();

		Map<String, String> map = new HashMap<>();
		map.put("1", did);
		map.put("2", dname);
		map.put("3", dspecia);
		map.put("4", dcontact);
		map.put("5", dfee);
		map.put("6", ddegree);
		return map;
	}

	/**
	 * Insert Doctor Data
	 */
	public static void insertDoctorData() {
		
		try {
			doctorDao.insert("did", "dname", "speciality", "contactno", "fee", "degree");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Search Doctor Data admin
	 */
	public static void searchDoctorData() {

		LOGGER.fine("\nEnter Doctor Id :");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		try {
			if (doctorDao.searchById(id).size() == 0) {
				LOGGER.fine("\nDoctor not found!");
			} else {

				try {
					Map<String, String> aDoctor = DoctorDao.searchById(id);
					if (aDoctor.size() != 0) {
						LOGGER.fine(
								"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
						System.out.printf("%10s  %15s  %15s  %15s  %10s  %20s\n", aDoctor.get(DoctorDao.ID),
								aDoctor.get(DoctorDao.NAME), aDoctor.get(DoctorDao.SPECIALITY),
								aDoctor.get(DoctorDao.CONTACT_NO), aDoctor.get(DoctorDao.FEE),
								aDoctor.get(DoctorDao.DEGREE));
						LOGGER.fine(
								"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
					} else {
						LOGGER.fine("Record is not found");
					}
					LOGGER.fine("\nRecord Find Successfully...");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Delete Doctor Data by Id
	 */
	public static void deleteDoctorData() {
		LOGGER.fine("\nEnter Doctor Id :");
		Scanner sc = new Scanner(System.in);
		String did = sc.nextLine();

		
		try {
			if (DoctorDao.searchById(did).size() == 0) {
				LOGGER.fine("\nDoctor not found!");
			} else {
				try {
					doctorDao.delete(did);
					LOGGER.fine("\nRecord Deleted Successfully...");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Show All List Of Doctor
	 */
	public static void listAllDoctor() {
		List<Map<String, String>> doctorList = null;
		try {
			doctorList = doctorDao.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.fine(
				"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
		for (Map<String, String> aDoctor : doctorList) {
			System.out.printf("%10s  %15s  %15s  %15s  %10s  %20s\n", aDoctor.get(DoctorDao.ID),
					aDoctor.get(DoctorDao.NAME), aDoctor.get(DoctorDao.SPECIALITY),
					aDoctor.get(DoctorDao.CONTACT_NO), aDoctor.get(DoctorDao.FEE), aDoctor.get(DoctorDao.DEGREE));
		}
		LOGGER.fine(
				"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
	}
}
