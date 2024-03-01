package service;

import java.util.List;

import dao.AdminDAO;
import dao.CartDAO;
import dao.MovieDAO;
import dao.OrdersDAO;
import dao.UserDAO;
import domain.Admin;
import domain.Movie;
import domain.Order;
import domain.User;

public class AdminService {
	MovieDAO mdao;
	UserDAO udao;
	CartDAO cdao;
	OrdersDAO odao;
	AdminDAO adao;
	public AdminService(MovieDAO mdao, UserDAO udao, CartDAO cdao, OrdersDAO odao, AdminDAO adao) {
		super();
		this.mdao = mdao;
		this.udao = udao;
		this.cdao = cdao;
		this.odao = odao;
		this.adao = adao;
	}
	
	public boolean checkLogin(String uname, String pass) {
		for (Admin u : adao.getAllAdmin()) {
			if (uname.equals(u.getUsername()) && pass.equals(u.getPass())) {
				return true;
			}
		}
		return false;
	}
	
	public List<Order> getAllOrders() {
		return odao.getAllOrders();
	}
	public List<User> getAllUsers() {
		return udao.getAllUsers();
	}
	
	public void removeMovie(int id) {
		mdao.deleteMovie(id);
	}
	public void addMovie(Movie m) {
		mdao.insertMovie(m);
	}
	
	public void removeUser(String email) {
		udao.deleteUser(email);
	}
	public void removeOrder(int id) {
		odao.deleteOrder(id);
	}

}
