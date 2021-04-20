
package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main Class In Which We can Do a admin and Doctor Login
 * 
 * @author Divergent
 *
 */
public class MainMenu {

	private final static Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

	
	static {
		LOGGER.setLevel(Level.FINE);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
	}

	/**
	 * Main Method
	 * 
	 * @param args
	 * @throws SQLException
	 */

	/**
	 * Admin Start Method
	 * 
	 * @throws SQLException
	 */
	public static void startAgain() throws SQLException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n----Login Panel----");
			System.out.println("1. Admin");
			System.out.println("2. Doctor");
			System.out.println("3. Exit");
			String input = sc.nextLine();
			switch (input) {
			case "1":
				if (Admin.adminLogin()) {
					while (true) {
						Admin.printAdminOptions();
						if (sc.nextLine().equals("6")) {
							startAgain();
							break;
						} else {
							LOGGER.fine("Select Right Option");
						}
					}
				} else {
					LOGGER.fine("You are not Authorised");
				}
				break;

			case "2":
				if (Doctor.doctorLogin()) {
					while (true) {
						Doctor.printDoctorOptions();
						if (sc.nextLine().equals("5")) {
							startAgain();
							break;
						} else {
							LOGGER.fine("Select Right Option");
						}
					}

				} else {
					LOGGER.fine("You are not Authorised");
				}
				break;

			case "3":
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Input");
				break;
			}

		}
	}
}
