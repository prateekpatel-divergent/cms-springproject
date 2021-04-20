package com.divergentsl.cms;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.AdminDao;



/**
 * Admin Class For Login and Update into all infromation
 * 
 * @author Divergent
 *
 */
public class Admin {

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(Patient.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
	}

	/**
	 * Admin Login
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static boolean adminLogin() throws SQLException {
		try {

			Scanner sc = new Scanner(System.in);
			Console cons = System.console();
			System.out.println("---------------Admin Login -------------------");
			System.out.println("Enter Username : ");
			String username = sc.nextLine();
			System.out.println("Enter Password : ");
			String password = sc.nextLine();
			AdminDao adminDao = new AdminDao(new DataBaseManager());
			if (adminDao.adminLogin(username, password)) {
				LOGGER.fine("Admin Login Successful");
				return true;
			} else {
				LOGGER.fine("Incorrect Username & Password");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void printAdminOptions() {
		while (true) {
			executedMethod();
		}
	}

	static void executedMethod() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n----Admin Panel-----");
		System.out.println(
				"1.  Patient \n  2.  Doctor \n  3. 	Drug \n  4.	Lab Test \n  5. 	Book appointment for a patient by selecting Patient, Doctor and Data/time \n  6. 	Logout");
		System.out.println("Enter Your Choice: ");

		int input = sc.nextInt();

		switch (input) {
		case 1:
			Patient.patientPanel();
			break;
		case 2:
			CurdDoctor.docterPanel();
			break;
		case 3:
			Drug.drugPanel();
			break;
		case 4:
			LabTest.labTestPanel();
			break;
		case 5:
			Appoinment.appoinmentPanel();
			break;
		case 6:
			LOGGER.fine("Logout Successfully");
			try {
				MainMenu.startAgain();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Back");
			break;
		}
	}

}
