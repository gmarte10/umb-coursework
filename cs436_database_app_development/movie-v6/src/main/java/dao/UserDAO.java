package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.User;

public class UserDAO {
	private DbDAO dbDao;
	private Connection connection;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DaoErrorMessage dem;
	
	public UserDAO(DbDAO db) {
		dbDao = db;
		connection = dbDao.getConnection();
		dem = new DaoErrorMessage();
	}
	
	public void insertUser(User user) {
		String email = user.getEmail();
		String pass = user.getPass();
		query = "INSERT INTO users" + " (email, password) VALUES " + " (?, ?)";
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, pass);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
	
	public void deleteUser(String email) {
		query = "DELETE FROM users WHERE email = ?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);;
		}
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		query = "SELECT * FROM users";
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String email = rs.getString("email");
				String pass = rs.getString("password");
				users.add(new User(email, pass));
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return users;
	}
	
	public void updateUserPass(User user) {
		query = "UPDATE users SET password = ? WHERE email = ?";
		String email = user.getEmail();
		String pass = user.getPass();
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, pass);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);;
		}
	}
}
