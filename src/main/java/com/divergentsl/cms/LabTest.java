package com.divergentsl.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.LabTestDao;

import java.sql.*;

/**
 * All Opeartion Of CRUD Lab Test
 * 
 * @author Divergent
 *
 */
public class LabTest {

	private final static Logger LOGGER;
	private static LabTestDao labTestDao;
	
	static {
		LOGGER = Logger.getLogger(Doctor.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
			
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		labTestDao = context.getBean("labTestDao", LabTestDao.class);
	}

	/**
	 * Make Custructor
	 */
	private LabTest() {

	}

	/**
	 * Show All Operation on Console
	 */
	public static void showCRUDDrug() {
		System.out.println(
				"1. Insert Lab Test Data \n  2. Update Lab Test Data \n  3. Search Lab Test Data \n  4. Delete Lab Test Data \n  5. List All Lab Test \n  6. Back");
	}

	/**
	 * Lab Test Panel
	 * 
	 * @throws SQLException
	 */
	public static void labTestPanel() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter Your Choice : ");

			showCRUDDrug();
			String input = sc.nextLine();
			switch (input) {
			case "1":
				insertLabTestData();
				break;
			case "2":
				updateLabTestDoctor();
				break;
			case "3":
				searchLabTestData();
				break;
			case "4":
				deleteLabTestData();
				break;
			case "5":
				listAllLabTest();
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
	 * Input Lab Test Data By Admin
	 * 
	 * @return
	 */
	public static Map<String, String> inputLabTestData() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter LabTest_Id");
		String lbid = sc.nextLine();
		System.out.println("Enter Patient Id");
		String pid = sc.nextLine();
		System.out.println("Enter Test");
		String testname = sc.nextLine();
		System.out.println("Enter Rate of Test");
		int rate = sc.nextInt();
		String ratestring = Integer.toString(rate);

		Map<String, String> map = new HashMap<String, String>();
		map.put("1", lbid);
		map.put("2", pid);
		map.put("3", testname);
		map.put("4", ratestring);
		return map;
	}

	/**
	 * Insert Lab Test Data
	 */
	public static void insertLabTestData() {
		try {
			labTestDao.insert("lbid", "pid", "testname", "currentdate", "5");
			LOGGER.fine("Insert successfully!!!!!!");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Update Lab Test Data
	 */
	public static void updateLabTestDoctor() {
		System.out.println("Not Complete");
	}

	/**
	 * Search Lab Test Data By Id
	 */
	public static void searchLabTestData() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Lab Test Id : ");
		String id = sc.nextLine();

		try {
			if (labTestDao.searchById(id).size() == 0) {
				LOGGER.fine("\n Record is Not Found");
			} else {
				try {
					Map<String, String> labTest = labTestDao.searchById(id);
					if (labTest.size() != 0) {
						System.out.println(
								"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Drug Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");

						System.out.printf("%5s  %5s  %20s  %15s  %4s\n", labTest.get(LabTestDao.ID),
								labTest.get(LabTestDao.PID), labTest.get(LabTestDao.TEST),
								labTest.get(LabTestDao.TCURRENTDATE), labTest.get(LabTestDao.RATE));
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
	 * Delete Lab Test Data By Id
	 */
	public static void deleteLabTestData() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Lab Test Id : ");
		String id = sc.nextLine();

		try {
			if (labTestDao.searchById(id).size() == 0) {
				LOGGER.fine("\nTest is not found!");
			} else {
				try {
					labTestDao.delete(id);
					LOGGER.fine("\nRecord Deleted Successfully...");
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
	 * List And Show All Lab Test Data
	 */
	public static void listAllLabTest() {
		try {
			System.out.println(
					"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			List<Map<String, String>> labTestList = labTestDao.list();

			for (Map<String, String> labTest : labTestList) {
				System.out.printf("%5s  %5s  %20s  %15s  %4s\n", labTest.get(LabTestDao.ID),
						labTest.get(LabTestDao.PID), labTest.get(LabTestDao.TEST), labTest.get(LabTestDao.TCURRENTDATE),
						labTest.get(LabTestDao.RATE));
			}
			System.out.println(
					"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}
}
