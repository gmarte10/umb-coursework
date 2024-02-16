package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CartDAO;
import dao.MovieDAO;
import dao.OrdersDAO;
import dao.UserDAO;
import domain.Movie;
import domain.Order;
import domain.User;

public class UserService {
	MovieDAO mdao;
	UserDAO udao;
	CartDAO cdao;
	OrdersDAO odao;
	public UserService(MovieDAO mdao, UserDAO udao, CartDAO cdao, OrdersDAO odao) {
		super();
		this.mdao = mdao;
		this.udao = udao;
		this.cdao = cdao;
		this.odao = odao;
	}
	public void uploardOrder(Order o) {
		odao.insertOrder(o);
	}
	
	public List<Order> getAllOrders() {
		return odao.getAllOrders();
	}
	public void uploadToCart(int id) {
		cdao.insertMovieId(id);
	}
	
	public Movie getMovieById(int id) {
		return mdao.selectMovieById(id);
	}
	
	public int getTotalPrice(List<Movie> m) {
		int total = 0;
		for (Movie x : m) {
			total += x.getPrice();
		}
		return total;
		
	}
	
	public User makeUser(String email, String pass) {
		return new User(email, pass);
	}
	
	public void registerUser(User u) {
		udao.insertUser(u);
	}
	
	public List<Movie> moviesOrdered() {
		List<Movie> mn = new ArrayList<>();
		for (Order o : odao.getAllOrders()) {
			mn.add(mdao.selectMovieById(o.getMovieId()));
		}
		return mn;
	}
	
	public List<Movie> moviesInCart() {
		List<Movie> mn = new ArrayList<>();
		for (int i : cdao.getAllIdsFromCart()) {
			mn.add(mdao.selectMovieById(i));
		}
		return mn;
	}
	
	public List<String> getMovieNames() {
		List<String> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			mn.add(m.getName());
		}
		return mn;
	}
	public List<Movie> filterMovieGenre(String g) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getGenre().equals(g)) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMoviePrice(int p) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getPrice() == p) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieRentPrice(int p) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getRentPrice() == p) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieYear(int y) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getYear() == y) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieContentRating(String c) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getContentRating().equals(c)) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieDirector(String d) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getDirector().equals(d)) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieImage(String i) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getImage().equals(i)) {
				mn.add(m);
			}
		}
		return mn;
	}
	public List<Movie> filterMovieVideo(String d) {
		List<Movie> mn = new ArrayList<>();
		for (Movie m : mdao.getAllMovies()) {
			if (m.getVideo().equals(d)) {
				mn.add(m);
			}
		}
		return mn;
	}
	
	public List<Movie> getMovies() {
		return mdao.getAllMovies();
	}
	
	public boolean checkLogin(String email, String pass) {
		for (User u : udao.getAllUsers()) {
			if (email.equals(u.getEmail()) && pass.equals(u.getPass())) {
				return true;
			}
		}
		return false;
	}
}
