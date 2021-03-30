import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
	try {
		Properties props = new Properties();						// Create a new Properties object
		FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
		props.load(input);										// Load the file contents into the Properties object
		driver = props.getProperty("jdbc.driver");				// Load the driver
		url = props.getProperty("jdbc.url");						// Load the url
		username = props.getProperty("jdbc.username");			// Load the username
		password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static boolean newCustomer(String name, String gender, String age, String pin) 
	{
		try {
			System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
			name = name.trim();
			gender = gender.trim();
			age = age.trim();
			pin = pin.trim();
			if (name.length() == 0 
				|| gender.length() == 0
				|| age.length() == 0
				|| pin.length() == 0)
			{
				System.out.println(":: CREATE NEW CUSTOMER - ERROR - EMPTY INPUTS");
				return false;
			}
			if (name.length() > 15)
			{
				System.out.println(":: CREATE NEW CUSTOMER - ERROR - NAME IS TOO LONG");
				return false;
			}
			if (!gender.equals("M") && !gender.equals("F"))
			{
				System.out.println(":: CREATE NEW CUSTOMER - ERROR - INVALID GENDER");
				return false;
			}
			if (BankingSystem.isOnlyDigits(age) == false)
			{
				System.out.println(":: CREATE NEW CUSTOMER - ERROR - INVALID AGE");
				return false;
			}
			if (BankingSystem.isOnlyDigits(pin) == false)
			{
				System.out.println(":: CREATE NEW CUSTOMER - ERROR - INVALID PIN");
				return false;
			}

			String sql = "INSERT INTO p1.customer"
						+ " (name, gender, age, pin) "
						+ " VALUES ("
						+ BankingSystem.quoteStr(name, false)
						+ BankingSystem.quoteStr(gender, true)
						+ ", " + age
						+ ", " + pin
						+")";
					/* insert your code here */

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			stmt.executeUpdate(sql);

			//clean up
			stmt.close();
			con.close();
			System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
			return true;
		}catch (Exception e) {
			System.out.println(":: FAILED CREATE NEW CUSTOMER");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return  false;
	}

	/**
	 * Customer Login.
	 * @param id customer id
	 * @param password password
	 */
	public static boolean customerLogin(String id, String pin) 
	{
		try {
			System.out.println(":: CUSTOMER LOGIN - RUNNING");
			id = id.trim();
			pin = pin.trim();
			if (id.length() == 0 
				|| pin.length() == 0)
			{
				System.out.println("::  CUSTOMER LOGIN - ERROR - EMPTY INPUTS");
				return false;
			}
			if (BankingSystem.isOnlyDigits(id) == false)
			{
				System.out.println(":: CUSTOMER LOGIN - ERROR - INVALID ID");
				return false;
			}
			if (BankingSystem.isOnlyDigits(pin) == false)
			{
				System.out.println(":: CUSTOMER LOGIN  - ERROR - INVALID PASSWORD");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.customer"
							+" WHERE ID= " + id
							+" AND PIN = " + pin;
			rs = stmt.executeQuery(selectQ);
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt("COUNT");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();

			if (rowCount > 0)
			{
				System.out.println(":: CUSTOMER LOGIN - SUCCESS");
				return true;
			}
			System.out.println(":: CUSTOMER LOGIN - ERROR - ID NOT FOUND");
			return false;
		}catch (Exception e) {
			System.out.println(":: FAILED OPEN NEW ACCOUNT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static boolean openAccount(String id, String type, String amount) 
	{
		try {
			System.out.println(":: OPEN NEW ACCOUNT - RUNNING");
			id = id.trim();
			type = type.trim();
			amount = amount.trim();
			if (id.length() == 0 
				|| type.length() == 0
				|| amount.length() == 0)
			{
				System.out.println(":: OPEN NEW ACCOUNT - ERROR - EMPTY INPUTS");
				return false;
			}
			if (BankingSystem.isOnlyDigits(id) == false)
			{
				System.out.println(":: OPEN NEW ACCOUNT - ERROR - INVALID ID");
				return false;
			}
			if (!type.equals("S") && !type.equals("C"))
			{
				System.out.println(":: OPEN NEW ACCOUNT - ERROR - INVALID TYPE");
				return false;
			}
			if (BankingSystem.isOnlyDigits(amount) == false)
			{
				System.out.println(":: OPEN NEW ACCOUNT - ERROR - INVALID AMOUNT");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.customer WHERE ID=" + id;
			rs = stmt.executeQuery(selectQ);
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt("COUNT");
			}

			if (rowCount > 0)
			{

				String insertQ = "INSERT INTO p1.account"
						+ " (id, balance, type, status) "
						+ " VALUES ("
						+ id
						+ ", " + amount
						+ BankingSystem.quoteStr(type, true)
						+ ", 'A')";
				//System.out.println(insertQ);
				stmt.executeUpdate(insertQ);
				System.out.println(":: OPEN NEW ACCOUNT - SUCCESS");
			}
			else
			{
				System.out.println(":: OPEN NEW ACCOUNT - ERROR - ID NOT FOUND");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();

			if (rowCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch (Exception e) {
			System.out.println(":: FAILED OPEN NEW ACCOUNT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}
	public static boolean verifyAccount(String cust_id, String number) 
	{
		try {
			System.out.println(":: VERIFY ACCOUNT - RUNNING");
			cust_id = cust_id.trim();
			number = number.trim();
			if (cust_id.length() == 0 
				|| number.length() == 0)
			{
				System.out.println(":: VERIFY ACCOUNT - ERROR - EMPTY INPUTS");
				return false;
			}
			if (BankingSystem.isOnlyDigits(cust_id) == false)
			{
				System.out.println(":: VERIFY ACCOUNT - ERROR - INVALID CUSTOMER ID");
				return false;
			}
			if (BankingSystem.isOnlyDigits(number) == false)
			{
				System.out.println(":: VERIFY ACCOUNT - ERROR - INVALID ACCOUNT ID");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.account"
				+" WHERE ID= " + cust_id + " AND NUMBER = " + number;
			rs = stmt.executeQuery(selectQ);
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt("COUNT");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();

			if (rowCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch (Exception e) {
			System.out.println(":: FAILED OPEN NEW ACCOUNT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static boolean closeAccount(String accNum) 
	{
		try {
			System.out.println(":: CLOSE ACCOUNT - RUNNING");
			accNum = accNum.trim();
			if (accNum.length() == 0 ||  BankingSystem.isOnlyDigits(accNum) == false)
			{
				System.out.println(":: CLOSE ACCOUNT  - ERROR - INVALID ACCOUNT NUMBER");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.account WHERE NUMBER=" + accNum;
			rs = stmt.executeQuery(selectQ);
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt("COUNT");
			}

			if (rowCount == 1)
			{

				String insertQ = "UPDATE p1.account"
						+ " SET status = 'I', Balance = 0"
						+ " WHERE NUMBER = "
						+ BankingSystem.quoteStr(accNum, false);
				//System.out.println(insertQ);
				stmt.executeUpdate(insertQ);
				System.out.println(":: CLOSE ACCOUNT - SUCCESS");
			}
			else
			{
				System.out.println(":: CLOSE ACCOUNT - ERROR - ACCOUNT NUMBER NOT FOUND");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();

			if (rowCount == 1) {
				return true;
			}
			return false;
		}catch (Exception e) {
			System.out.println(":: FAILED CLOSE ACCOUNT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static boolean deposit(String accNum, String amount) 
	{
		try {
			System.out.println(":: DEPOSIT - RUNNING");
			accNum = accNum.trim();
			amount = amount.trim();
			if (accNum.length() == 0 ||  BankingSystem.isOnlyDigits(accNum) == false)
			{
				System.out.println(":: DEPOSIT  - ERROR - INVALID ACCOUNT NUMBER");
				return false;
			}
			if (amount.length() == 0 ||  BankingSystem.isOnlyDigits(amount) == false)
			{
				System.out.println(":: DEPOSIT  - ERROR - INVALID AMOUNT");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.account WHERE NUMBER=" + accNum;
			rs = stmt.executeQuery(selectQ);
			int rowCount = 0;
			if (rs.next()) {
				rowCount = rs.getInt("COUNT");
			}

			boolean result = false;
			if (rowCount == 1)
			{
				boolean verify = false;
				selectQ = "SELECT STATUS FROM p1.account WHERE NUMBER=" + accNum;
				rs = stmt.executeQuery(selectQ);
				if (rs.next()) {
					String mystatus = rs.getString("STATUS");
					if (mystatus.equals("A")) {
						verify = true;
					}
				}
						
				if (verify == true) {
					String insertQ = "UPDATE p1.account"
							+ " SET BALANCE = BALANCE + " + amount
							+ " WHERE NUMBER = "
							+ BankingSystem.quoteStr(accNum, false);
					//System.out.println(insertQ);
					stmt.executeUpdate(insertQ);
					System.out.println(":: DEPOSIT - SUCCESS");
					result = true;
				}
			}
			else
			{
				System.out.println(":: DEPOSIT - ERROR - INVALID ACCOUNT NUMBER");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();
			return result;
		}catch (Exception e) {
			System.out.println(":: FAILED DEPOSIT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static boolean withdraw(String accNum, String amount) 
	{
		try {
			System.out.println(":: WITHDRAW - RUNNING");
			accNum = accNum.trim();
			amount = amount.trim();
			if (accNum.length() == 0 ||  BankingSystem.isOnlyDigits(accNum) == false)
			{
				System.out.println(":: WITHDRAW  - ERROR - INVALID ACCOUNT NUMBER");
				return false;
			}
			if (amount.length() == 0 ||  BankingSystem.isOnlyDigits(amount) == false)
			{
				System.out.println(":: WITHDRAW  - ERROR - INVALID AMOUNT");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			boolean verify1 = false;
			String selectQ = "SELECT STATUS FROM p1.account WHERE NUMBER=" + accNum;
			rs = stmt.executeQuery(selectQ);
			if (rs.next()) {
				String s = rs.getString("STATUS");
				if (s.equals("A")) {
					verify1 = true;
				}
			}
			boolean result =  false;
			if (verify1 == true) {
				selectQ = "SELECT BALANCE FROM p1.account WHERE NUMBER=" + accNum;
				rs = stmt.executeQuery(selectQ);

				if (rs.next()) {
					int balance = rs.getInt("BALANCE");
					int withdraw_amount = Integer.parseInt(amount);
					if (withdraw_amount > balance)
					{
						System.out.println(":: WITHDRAW - ERROR - NOT ENOUGH FUND");
						result = false;
					}
					else
					{
						String insertQ = "UPDATE p1.account"
							+ " SET BALANCE = BALANCE - " + amount
							+ " WHERE NUMBER = "
							+ BankingSystem.quoteStr(accNum, false);
						//System.out.println(insertQ);
						stmt.executeUpdate(insertQ);
						System.out.println(":: WITHDRAW - SUCCESS");
						result = true;
					}
				}
			}
			else
			{
				System.out.println(":: WITHDRAW - ERROR - INVALID ACCOUNT NUMBER");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();

			return result;
		}catch (Exception e) {
			System.out.println(":: WITHDRAW DEPOSIT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static boolean transfer(String srcAccNum, String destAccNum, String amount) 
	{
		try {
			System.out.println(":: TRANSFER - RUNNING");
			srcAccNum = srcAccNum.trim();
			destAccNum = destAccNum.trim();
			amount = amount.trim();
			if (srcAccNum.length() == 0 ||  BankingSystem.isOnlyDigits(srcAccNum) == false)
			{
				System.out.println(":: TRANSFER  - ERROR - INVALID SOURCE ACCOUNT NUMBER");
				return false;
			}
			if (destAccNum.length() == 0 ||  BankingSystem.isOnlyDigits(destAccNum) == false)
			{
				System.out.println(":: TRANSFER  - ERROR - INVALID DESTINATION ACCOUNT NUMBER");
				return false;
			}
			if (amount.length() == 0 ||  BankingSystem.isOnlyDigits(amount) == false)
			{
				System.out.println(":: TRANSFER  - ERROR - INVALID AMOUNT");
				return false;
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			boolean verify1 = true;
			boolean verify2 = true;
			String selectQ = "SELECT STATUS FROM p1.account WHERE NUMBER=" + srcAccNum;
			rs = stmt.executeQuery(selectQ);
			if (rs.next())
			{
				String mystatus = rs.getString("STATUS");
				if (mystatus.equals("I")) {
					verify1 = false;
				}
			}
			selectQ = "SELECT STATUS FROM p1.account WHERE NUMBER=" + destAccNum;
			rs = stmt.executeQuery(selectQ);
			if (rs.next())
			{
				String mystatus = rs.getString("STATUS");
				if (mystatus.equals("I")) {
					verify2 = false;
				}
			}
		
			boolean result = false;
			if (verify1 == true && verify2 == true) {
				selectQ = "SELECT BALANCE FROM p1.account WHERE NUMBER=" + srcAccNum;
				rs = stmt.executeQuery(selectQ);

				if (rs.next()) {
					int balance = rs.getInt("BALANCE");
					int withdraw_amount = Integer.parseInt(amount);
					if (withdraw_amount > balance)
					{
							System.out.println(":: TRANSFER - ERROR - NOT ENOUGH FUNDS");
							result = false;
					}
					else
					{
						String insertQ1 = "UPDATE p1.account"
							+ " SET BALANCE = BALANCE - " + amount
							+ " WHERE NUMBER = "
							+ BankingSystem.quoteStr(srcAccNum, false);
						//System.out.println(insertQ);
						stmt.executeUpdate(insertQ1);

						String insertQ2 = "UPDATE p1.account"
							+ " SET BALANCE = BALANCE + " + amount
							+ " WHERE NUMBER = "
							+ BankingSystem.quoteStr(destAccNum, false);
						//System.out.println(insertQ);
						stmt.executeUpdate(insertQ2);

						System.out.println(":: TRANSFER - SUCCESS");
						result = true;
					}
				}
			}
			else
			{
				System.out.println(":: TRANSFER - ERROR - INVALID ACCOUNT NUMBER");
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();
			return result;
		}catch (Exception e) {
			System.out.println(":: WITHDRAW DEPOSIT");
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static String accountSummary(String cusID) 
	{
		try {
			System.out.println(":: ACCOUNT SUMMARY - RUNNING");
			cusID = cusID.trim();
			if (cusID.length() == 0 || BankingSystem.isOnlyDigits(cusID) == false)
			{
				System.out.println(":: ACCOUNT SUMMARY - ERROR - INVALID CUSTOMER ID");
				return "";
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String selectQ = "SELECT COUNT(*) AS COUNT FROM p1.account WHERE ID=" + cusID;
			rs = stmt.executeQuery(selectQ);
			int count = 0;
			if (rs.next()) {
				count = rs.getInt("COUNT");
			}
			String result = "";
			if (count > 0) {
			
				result = String.format("%-11s %-11s%n", "NUMBER", "BALANCE");
				result += String.format("----------- -----------%n");
				System.out.printf("%-11s %-11s%n", "NUMBER", "BALANCE");
				System.out.printf("----------- -----------%n");

				selectQ = "SELECT NUMBER, BALANCE FROM p1.account WHERE STATUS != 'I' AND ID=" + cusID;
				rs = stmt.executeQuery(selectQ);
				while (rs.next()) {
					String number = rs.getString("NUMBER");
					String balance = rs.getString("BALANCE");
					System.out.printf("%11s %11s%n", number, balance);
					result += String.format("%11s %11s%n", number, balance);
				}

				result += String.format("-----------------------%n");
				System.out.printf("-----------------------%n");
				selectQ = "SELECT SUM(BALANCE) AS SUM FROM p1.account WHERE ID=" + cusID;
				rs = stmt.executeQuery(selectQ);
				if (rs.next()) {
					String sum = rs.getString("SUM");
					System.out.printf("%-11s %11s%n", "TOTAL", sum);
					result += String.format("%-11s %11s%n", "TOTAL", sum);
				}
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
			return result;
		}catch (Exception e) {
			System.out.println(":: FAILED ACCOUNT SUMMARY");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static String reportA() 
	{
		try {
			System.out.println(":: REPORT A - RUNNING");

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String result = "";
			result += String.format("%-10s %-15s %-6s %-11s %-11s%n", "ID", "NAME", "GENDER", "AGE", "TOTAL");
			System.out.printf("%-10s %-15s %-6s %-11s %-11s%n", "ID", "NAME", "GENDER", "AGE", "TOTAL");
			System.out.printf("---------- --------------- ------ ----------- -----------%n");

			String selectQ = "SELECT CUS.ID, NAME, GENDER, AGE, TOTAL"
							+ " FROM P1.customer CUS"
							+" INNER JOIN"
							+" (select ACC.ID, SUM(BALANCE) AS TOTAL from p1.account ACC GROUP BY ID) GRP"
							+" ON CUS.ID = GRP.ID"
							+" ORDER BY TOTAL DESC";
			rs = stmt.executeQuery(selectQ);
			while (rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				String gender = rs.getString("GENDER");
				String age = rs.getString("AGE");
				String total = rs.getString("TOTAL");

				System.out.printf("%10s %-15s %-6s %11s %11s%n", id, name, gender, age, total);
				result += String.format("%10s %-15s %-6s %11s %11s%n", id, name, gender, age, total);
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: REPORT A - SUCCESS");
			return result;
		}catch (Exception e) {
			System.out.println(":: FAILED REPORT A");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static String reportB(String min, String max) 
	{
		try {

			System.out.println(":: REPORT B - RUNNING");
			min = min.trim();
			max = max.trim();
			if (min.length() == 0 || max.length() == 0
				||BankingSystem.isOnlyDigits(min) == false
				||BankingSystem.isOnlyDigits(max) == false) {
				System.out.println(":: REPORT B - ERROR - INVALID AGE");
				return "";
			}

			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			String result = "";

			result += String.format("%-11s%n", "AVERAGE");
			result += String.format("-----------%n");
			System.out.printf("%-11s%n", "AVERAGE");
			System.out.printf("-----------%n");

			String selectQ = "SELECT AVG(TOTAL) AS AVERAGE FROM"
							+ " P1.customer CUS"
							+" INNER JOIN"
							+" (select ACC.ID, SUM(BALANCE) AS TOTAL from p1.account ACC GROUP BY ID) GRP"
							+" ON CUS.ID  = GRP.ID"
							+" WHERE AGE >= " + min
							+" AND"
							+" AGE <= " + max;
			rs = stmt.executeQuery(selectQ);
			if (rs.next()) {
				String average = rs.getString("AVERAGE");

				System.out.printf("%11s%n", average);
				result += String.format("%11s%n", average);
			}

			// clean up
			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: REPORT B - SUCCESS");
			return result;
		}catch (Exception e) {
			System.out.println(":: FAILED REPORT B");
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";
	}

	private static String quoteStr(String content, boolean addComma)
	{
		String result = "'" + content + "'";
		if (addComma)
		{
			result = ", " + result;
		}
		return result;
	}
	private static boolean isOnlyDigits(String number)
	{
		for(int i = 0; i < number.length(); i++)
		{
			if(number.charAt(i) < '0'|| number.charAt(i) > '9')
			{
				return false;
			}
		}
		return true;
	}
}
