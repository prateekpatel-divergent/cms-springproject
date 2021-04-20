package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.DoctorLoginDao;


/**
 * Doctor Class In Which All CRUD Operaton Done
 * 
 * @author Divergent
 *
 */
public class Doctor {

	private final static Logger LOGGER;
	private static DoctorLoginDao doctorLoginDao;
	
	static {
		LOGGER = Logger.getLogger(Doctor.class.getName());
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		doctorLoginDao = context.getBean("doctorLoginDao",DoctorLoginDao.class);
	}

	/**
	 * Get Doctor Data
	 */
	public static void printDoctorOptions() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\nDoctor Panel");
			System.out.println("1. List of patient");
			System.out.println("2. Add prescription and notes for a patient");
			System.out.println("3. See booked appointments for him");
			System.out.println("4. Check patient history and his prescription");
			System.out.println("5. Generate Invoice");
			System.out.println("6. Logout");
			System.out.println("Enter Your Choice: ");

			int input = sc.nextInt();

			switch (input) {
			case 1:
				Patient.listAllPatientData();
				break;
			case 2:
				PrescriptionAndNotes.prescriptionPatient();
				break;
			case 3:
				Appoinment.showAllAppoinment();
				break;
			case 4:
				PrescriptionAndNotes.historyAndPresciption();
				break;
			case 5:
				Patient.generateInvoice();
				break;
			case 6:
				LOGGER.fine("Logout Successfully");
				try {
					MainMenu.startAgain();
				} catch (SQLException e) {
					LOGGER.fine(e.getMessage());
					e.printStackTrace();
				}
				break;
			default:
				LOGGER.fine("Choice Right Option");
				break;
			}
		}
	}

	/**
	 * Doctor Login Method
	 * 
	 * @return
	 */
	public static boolean doctorLogin() {

		Scanner sc = new Scanner(System.in);
		try {

			System.out.println("\n-----Doctor Login------");
			System.out.println("\nEnter Username: ");
			String username = sc.nextLine();

			System.out.println("\nEnter Password: ");
			String password = sc.nextLine();
			
			if (doctorLoginDao.doctorLogin(username, password)) {
				LOGGER.fine("Doctor Login Successful");
				return true;
			} else {
				LOGGER.fine("Incorrect Username & Password");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.fine(e.getMessage());
		}
		return false;
	}

}
