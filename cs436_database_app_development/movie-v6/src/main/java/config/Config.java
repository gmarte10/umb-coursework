package config;

import java.sql.SQLException;

import dao.AdminDAO;
import dao.CartDAO;
import dao.DbDAO;
import dao.MovieDAO;
import dao.OrdersDAO;
import dao.UserDAO;
import service.AdminService;
import service.UserService;

public class Config {
	UserService us;
	AdminService as;
	DbDAO db;
	public Config() {
		db = new DbDAO();
		MovieDAO mdao = new MovieDAO(db);
		UserDAO udao = new UserDAO(db);
		CartDAO cdao = new CartDAO(db);
		OrdersDAO odao = new OrdersDAO(db);
		AdminDAO adao = new AdminDAO(db);
		us = new UserService(mdao, udao, cdao, odao);
		as = new AdminService(mdao, udao, cdao, odao, adao);
	}
	
	public UserService getUserService() {
		return us;
	}
	public AdminService getAdminService() {
		return as;
	}
	
	public void close() throws SQLException {
		db.close();
	}
}
