package database;

import account.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class MysqlTutorial {
 
	static Connection crunchifyConn = null;
	static PreparedStatement crunchifyPrepareStat = null;
 
	public static void makeJDBCConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}

		try
		{
			// DriverManager: The basic service for managing a set of JDBC drivers.
			crunchifyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EconomyView?verifyServerCertificate=false&useSSL=false", "root", "password");
			if (crunchifyConn != null)
			{
				System.out.println("Connection Successful! Enjoy. Now it's time to push data");
			}
			else
			{
				System.out.println("Failed to make connection!")
				;
			}
		}
		catch (SQLException e)
		{
			System.out.println("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
	}

	public void insertRecord(Record record)
	{
		String insertQueryStatement = "INSERT  INTO  Economy  VALUES  (?,?,?,?)";

		try
		{
			Date test = Date.valueOf(record.getDate());
			System.out.println("insertRecords");
			crunchifyPrepareStat = crunchifyConn.prepareStatement(insertQueryStatement);
			crunchifyPrepareStat.setString(1, record.getEvent());
			crunchifyPrepareStat.setString(2, record.getAmount());
			crunchifyPrepareStat.setDate(3, test);
			crunchifyPrepareStat.setDate(4, null);
			crunchifyPrepareStat.executeUpdate();
			System.out.println("Record added successfully");
			System.out.println("afterinsert");
		}
		catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}

//
//		// execute insert SQL statement
//		} catch (
//
//		SQLException e) {
//		e.printStackTrace();
//		}
	}

	public void closeSQLConnection()
	{
		try
		{
			crunchifyConn.close();
		}
		catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}
	// private static void addDataToDB(String companyName, String address, int totalEmployee, String webSite) {
 
	// 	try {
	// 		String insertQueryStatement = "INSERT  INTO  Employee  VALUES  (?,?,?,?)";
 
	// 		crunchifyPrepareStat = crunchifyConn.prepareStatement(insertQueryStatement);
	// 		crunchifyPrepareStat.setString(1, companyName);
	// 		crunchifyPrepareStat.setString(2, address);
	// 		crunchifyPrepareStat.setInt(3, totalEmployee);
	// 		crunchifyPrepareStat.setString(4, webSite);
 
	// 		// execute insert SQL statement
	// 		crunchifyPrepareStat.executeUpdate();
	// 		System.out.println(companyName + " added successfully");
	// 	} catch (
 
	// 	SQLException e) {
	// 		e.printStackTrace();
	// 	}
	// }
 
	// private static void getDataFromDB() {
 
	// 	try {
	// 		// MySQL Select Query Tutorial
	// 		String getQueryStatement = "SELECT * FROM employee";
 
	// 		crunchifyPrepareStat = crunchifyConn.prepareStatement(getQueryStatement);
 
	// 		// Execute the Query, and get a java ResultSet
	// 		ResultSet rs = crunchifyPrepareStat.executeQuery();
 
	// 		// Let's iterate through the java ResultSet
	// 		while (rs.next()) {
	// 			String name = rs.getString("Name");
	// 			String address = rs.getString("Address");
	// 			int employeeCount = rs.getInt("EmployeeCount");
	// 			String website = rs.getString("Website");
 
	// 			// Simply Print the results
	// 			System.out.format("%s, %s, %s, %s\n", name, address, employeeCount, website);
	// 		}
 
	// 	} catch (
 
	// 	SQLException e) {
	// 		e.printStackTrace();
	// 	}
 
	// }
 
	// // Simple System.out.println utility
	// private static void System.out.println(String string) {
	// 	System.out.println(string);
 
	// }
}