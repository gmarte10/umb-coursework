package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Admin;
import domain.User;

public class AdminDAO {
	private DbDAO dbDao;
	private Connection connection;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DaoErrorMessage dem;
	
	public AdminDAO(DbDAO db) {
		dbDao = db;
		connection = dbDao.getConnection();
		dem = new DaoErrorMessage();
	}
	public List<Admin> getAllAdmin() {
		List<Admin> admins = new ArrayList<>();
		query = "SELECT * FROM admin";
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String uname = rs.getString("username");
				String pass = rs.getString("password");
				admins.add(new Admin(uname, pass));
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return admins;
	}
	
	public void insertAdmin(Admin admin) {
		String username = admin.getUsername();
		String pass = admin.getPass();
		query = "INSERT INTO admin" + " (username, password) VALUES " + " (?, ?)";
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, pass);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
	
	public void deleteAdmin(String username) {
		query = "DELETE FROM admin WHERE username = ?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);;
		}
	}
}
