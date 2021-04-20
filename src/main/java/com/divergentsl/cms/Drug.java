package com.divergentsl.cms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.DrugDao;


/**
 * Drug Class For All CRUD Operation
 * 
 * @author Divergent
 *
 */
public class Drug {

	static Scanner sc = new Scanner(System.in);

	private final static Logger LOGGER;
	private static DrugDao drugDao;
	
	static {
		LOGGER = Logger.getLogger(Doctor.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		drugDao = context.getBean("drugDao",DrugDao.class);
	}

	private Drug() {

	}

	/**
	 * Show All Option
	 */
	public static void showCRUDDrug() {
		System.out.println(
				"1. Insert Drug Data \n  2. Update Drug Data \n  3. Search Drug Data \n  4. Delete Drug Data \n  5. List All Drug \n  6. Back");
	}

	/**
	 * Get all Panel Option
	 */
	public static void drugPanel() {
		while (true) {
			System.out.println("Enter Your Choice : ");
			showCRUDDrug();
			String input = sc.nextLine();
			switch (input) {
			case "1":
				insertDrugData();
				break;
			case "2":
				break;
			case "3":
				searchDrugData();
				break;
			case "4":
				deleteDrugData();
				break;
			case "5":
				listAllDrug();
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
	 * It get Drug Infomation
	 * 
	 * @return
	 */
	public static Map<String, String> inputDoctorData() {
		System.out.println("Enter Drug_Id");
		String did = sc.nextLine();
		System.out.println("Enter Drug Name");
		String dname = sc.nextLine();
		System.out.println("Enter Drug Rate");
		String drate = sc.nextLine();

		Map<String, String> map = new HashMap<>();
		map.put("1", did);
		map.put("2", dname);
		map.put("3", drate);
		return map;
	}

	/**
	 * Insert all Drug Data
	 */
	public static void insertDrugData() {
		try {
			Map<String, String> map = inputDoctorData();
			map.get("id");
			map.get("name");
			map.get("rate");
			drugDao.insert("id", "name", "rate");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Search Drug Data
	 */
	public static void searchDrugData() {
		System.out.println("Enter Drug Id : ");
		String id = sc.nextLine();
		try {
			if (drugDao.searchById(id).size() == 0) {
				LOGGER.fine("\n Record is Not Found");
			} else {
				try {
					Map<String, String> drug = drugDao.searchById(id);
					if (drug.size() != 0) {
						System.out.println(
								"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Drug Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");

						System.out.printf("%5s  %20s  %25s  \n", drug.get(DrugDao.ID), drug.get(DrugDao.NAME),
								drug.get(DrugDao.RATE));
						System.out.println(
								"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					LOGGER.fine(e.getMessage());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}

	}

	/**
	 * Delete Drug Data
	 */
	public static void deleteDrugData() {
		System.out.println("Enter Drug Id : ");
		String id = sc.nextLine();

		try {
			if (drugDao.searchById(id).size() == 0) {
				LOGGER.fine("\nDrug not found!");
			} else {
				try {
					drugDao.delete(id);
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
	 * Show All Drug List
	 */
	public static void listAllDrug() {
		DrugDao drugDao = new DrugDao(new DataBaseManager());
		try {
			List<Map<String, String>> doctorList = drugDao.list();
			System.out.println(
					"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			for (Map<String, String> drug : doctorList) {
				System.out.printf("%5s  %20s  %25s  \n", drug.get(DrugDao.ID), drug.get(DrugDao.NAME),
						drug.get(DrugDao.RATE));
			}
			System.out.println(
					"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}
}
