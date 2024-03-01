package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Order;

public class OrdersDAO {
	private DbDAO dbDao;
	private Connection connection;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DaoErrorMessage dem;
	
	public OrdersDAO(DbDAO db) {
		dbDao = db;
		connection = dbDao.getConnection();
		dem = new DaoErrorMessage();
	}
	
	public void deleteOrder(int id) {
		query = "DELETE FROM orders WHERE id = ?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);;
		}
	}
	
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		query = "SELECT * FROM orders";
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("user_email");
				int movieId = rs.getInt("movie_id");
				String date = rs.getString("order_date");
				orders.add(new Order(id, email, movieId, date));
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return orders;
	}
	
	public void insertOrder(Order order) {
		int id = order.getId();
		String email = order.getEmail();
		int mId = order.getMovieId();
		String date = order.getDate();
		query = "INSERT INTO orders" + " (id, user_email, movie_id, order_date) VALUES " + " (?, ?, ?, ?)";
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, email);
			pstmt.setInt(3, mId);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
	

}
