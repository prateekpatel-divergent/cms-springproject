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

import com.divergentsl.cms.dao.AppoinmentDao;



/**
 * Appointment All Patient
 * 
 * @author Divergent
 *
 */
public class Appoinment {
	

	private final static Logger LOGGER;
	private static AppoinmentDao appoinmentDao;
	
	static {
		LOGGER = Logger.getLogger(Appoinment.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		appoinmentDao = context.getBean("appoinmentDao",AppoinmentDao.class);
	}
	/**
	 * Make Private Custructor
	 */
	private Appoinment() {

	}

	/**
	 * Get Patient Information
	 * 
	 * @return
	 */
	public static Map<String, String> inputAppoinmentData() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Appoinment Id ");
		String appoinid = sc.nextLine();
		System.out.println("Enter Patient Name");
		String patientname = sc.nextLine();
		System.out.println("Enter Docter Name");
		String doctername = sc.nextLine();
		System.out.println("Enter Problem");
		String problem = sc.nextLine();
		System.out.println("Enter Appoinment Date");
		String appoindate = sc.nextLine();
		System.out.println("Enter doctor id");
		String doctorid = sc.nextLine();
		System.out.println("Enter Patient Id");
		String patientid = sc.nextLine();

		Map<String, String> map = new HashMap<>();
		map.put("1", appoinid);
		map.put("2", patientname);
		map.put("3", doctername);
		map.put("4", problem);
		map.put("5", appoindate);
		map.put("6", doctorid);
		map.put("7", patientid);
		return map;
	}

	/**
	 * All Information Stored By This Method
	 */
	public static void appoinmentPanel() {
		
		try {
			appoinmentDao.insert("appoinid", "patientname", "doctername", "problem", "appoindate","currentdate", "doctorid", "patientid");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}

	/**
	 * Show All Appointment
	 */
	public static void showAllAppoinment() {
		try {
			System.out.println(
					"\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Docter Data*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
			List<Map<String, String>> patientList = appoinmentDao.list();

			for (Map<String, String> patient : patientList) {
				System.out.printf("%5s  %15s  %20s  %15s  %15s  %20s  %5s  %5s\n", patient.get(AppoinmentDao.ID),
						patient.get(AppoinmentDao.PNAME), patient.get(AppoinmentDao.DNAME),
						patient.get(AppoinmentDao.APPOINMENTDATE), patient.get(AppoinmentDao.CURRENTDATE),
						patient.get(AppoinmentDao.PROBLEM), patient.get(AppoinmentDao.PID),
						patient.get(AppoinmentDao.PID));
			}
			System.out.println(
					"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
	}
}
