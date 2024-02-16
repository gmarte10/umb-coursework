package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbDAO {
	private String userSql = "gcmarte";
	private String passSql = "gcmarte";
	private String dbSql = "jdbc:oracle:thin:@localhost:1521:dbs3";
	private Connection connection;
	
	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection(dbSql, userSql, passSql);
		} catch (SQLException e) {
			DaoErrorMessage dem = new DaoErrorMessage();
			dem.printSQLException(e);
		}
		return connection;
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public void intitialzieDb() {
		clearTable("users");
		clearTable("movies");
		clearTable("cart");
		clearTable("orders");
		clearTable("admin");
	}
	
	private void clearTable(String tableName) {
		String query = "DELETE FROM " + tableName;
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
