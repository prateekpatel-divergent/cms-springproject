package com.divergentsl.cms;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Main Class In Which We can Do a admin and Doctor Login
 * 
 * @author Divergent
 *
 */
public class ClinicManagmentSystem {
	/**
	 * Main Method
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("cms.xml");
		MainMenu mainMenu = context.getBean("mainMenu",MainMenu.class);
		
		mainMenu.startAgain();
	}
}
