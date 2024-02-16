package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Movie;

public class MovieDAO {
	private DbDAO dbDao;
	private Connection connection;
	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DaoErrorMessage dem;
	
	public MovieDAO(DbDAO db) {
		dbDao = db;
		connection = dbDao.getConnection();
		dem = new DaoErrorMessage();
	}
	
	public void deleteMovie(int id) {
		query = "DELETE FROM movies WHERE id = ?";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
	
	public Movie selectMovieById(int id) {
		query = "SELECT name, price, genre, year, content_rating, director, image, video, rent_price FROM movies WHERE id = ?";
		Movie movie = null;
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String genre = rs.getString("genre");
				int year = rs.getInt("year");
				String contentRating = rs.getString("content_rating");
				String director = rs.getString("director");
				String image = rs.getString("image");
				String video = rs.getString("video");
				int rentPrice = rs.getInt("rent_price");
				movie = new Movie(id, name, price, genre, year, contentRating, director, image, video, rentPrice);
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return movie;
	}
	
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		query = "SELECT * FROM movies";
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String genre = rs.getString("genre");
				int year = rs.getInt("year");
				String contentRating = rs.getString("content_rating");
				String director = rs.getString("director");
				String image = rs.getString("image");
				String video = rs.getString("video");
				int rentPrice = rs.getInt("rent_price");
				movies.add(new Movie(id, name, price, genre, year, contentRating, director, image, video, rentPrice));
			}
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
		return movies;
	}
	
	public void insertMovie(Movie movie) {
		int id = movie.getId();
		String name = movie.getName();
		int price = movie.getPrice();
		String genre = movie.getGenre();
		int year = movie.getYear();
		String contentRating = movie.getContentRating();
		String director = movie.getDirector();
		String image = movie.getImage();
		String video = movie.getVideo();
		int rentPrice = movie.getRentPrice();
		query = "INSERT INTO movies" + " (id, name, price, genre, year, content_rating, director, image, video, rent_price) VALUES " 
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, price);
			pstmt.setString(4, genre);
			pstmt.setInt(5, year);
			pstmt.setString(6, contentRating);
			pstmt.setString(7, director);
			pstmt.setString(8, image);
			pstmt.setString(9, video);
			pstmt.setInt(10, rentPrice);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			dem.printSQLException(e);
		}
	}
	

}
