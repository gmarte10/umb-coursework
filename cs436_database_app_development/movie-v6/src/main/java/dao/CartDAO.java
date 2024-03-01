package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CartDAO {
	private DbDAO dbDao;
	private Connection connection;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DaoErrorMessage dem;
	
	public CartDAO(DbDAO db) {
		dbDao = db;
		connection = dbDao.getConnection();
		dem = new DaoErrorMessage();
	}
	
	public void deleteMovieId(int id) {
		query = "DELETE FROM cart WHERE movie_id = ?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);;
		}
	}
	
	public List<Integer> getAllIdsFromCart() {
		List<Integer> movieIds = new ArrayList<>();
		query = "SELECT * FROM cart";
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				movieIds.add(id);
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return movieIds;
	}
	
	public void insertMovieId(int id) {
		query = "INSERT INTO cart" + " (movie_id) VALUES " + " (?)";
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
}
