package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/common?";
	private static final String NAME = "root";
	private static final String PASSWORD = "0405";
	private Connection conn = null;

	public Connection connectDataBase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(URL, NAME, PASSWORD);
		return conn;
	} 
}