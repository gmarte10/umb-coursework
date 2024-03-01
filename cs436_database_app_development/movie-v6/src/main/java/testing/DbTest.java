package testing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import dao.AdminDAO;
import dao.CartDAO;
import dao.DbDAO;
import dao.MovieDAO;
import dao.OrdersDAO;
import dao.UserDAO;
import domain.Admin;
import domain.Movie;
import domain.Order;
import domain.User;

public class DbTest {
	DbDAO dbd = new DbDAO();
	User u = new User("bob@gmail.com", "hi");
	UserDAO ud = new UserDAO(dbd);
	public static void main(String[] args) throws SQLException {
		DbDAO dbd = new DbDAO();
		Config c = new Config();
		// userDb(dbd);
		// dbd.intitialzieDb();
		// movieDb(dbd);

		// cartDb(dbd);
		// orderDb(dbd);
		AdminDAO ad = new AdminDAO(dbd);
		Admin a = new Admin("jason", "x8");
		// ad.insertAdmin(a);
		// System.out.println("success");
		// MovieDAO md = new MovieDAO(dbd);
		//Movie m = md.selectMovieById(1);
		//System.out.println(m.getContentRating());
		// OrdersDAO cd = new OrdersDAO(dbd);
		// userDb(dbd);
		
	}
	private static void orderDb(DbDAO db) {
		int id = 1;
		String email = "gary@gmail.com";
		int mId = 1;
		String date = "2/3/2004";
		
		Order o = new Order(id, email, mId, date);
		OrdersDAO od = new OrdersDAO(db);
		od.insertOrder(o);
	}
	
	private static void userDb(DbDAO db) {
		// User u = new User("sarah@gmail.com", "mint");
		UserDAO ud = new UserDAO(db);
		// ud.insertUser(u);
		// System.out.println("success inserting user");	
		ud.deleteUser("new3@gmail.com");
		System.out.println("success deleting user");
		// ud.updateUserPass(u);
		List<User> ul = new ArrayList<User>();
		ul = ud.getAllUsers();
		for (User x : ul) {
			System.out.println(x.getEmail() + " | " + x.getPass());
		}
	}
	private static void cartDb(DbDAO db) {
		int id = 1;
		CartDAO cd = new CartDAO(db);
		// cd.insertMovieId(id);
		cd.deleteMovieId(1);
		List<Integer> cl = new ArrayList<Integer>();
		cl = cd.getAllIdsFromCart();
		for (int x : cl) {
			System.out.println(x);
		}
	}
	
	private static void movieDb(DbDAO db) {
		int id = 7;
		String name = "SpiderMan:SpiderVerse";
		int price = 15;
		String genre = "animation";
		int year = 2018;
		String contentRating = "PG";
		String director = "Peter Ramsey";
		String image = "spiderman.jpg";
		String video = "g4Hbz2jLxvQ";
		int rentPrice = 5;

		Movie m = new Movie(id, name, price, genre, year, contentRating, director, image, video, rentPrice);
		MovieDAO md = new MovieDAO(db);
		md.insertMovie(m);
		System.out.println("success inserting movie");
		
		// md.deleteMovie(1);
		// System.out.println("success deleting movie");
		
		List<Movie> ml = new ArrayList<Movie>();
		ml = md.getAllMovies();
		for (Movie x : ml) {
			System.out.println(x.getId() + " | " + x.getName() + " | " + x.getPrice() + " | " + x.getGenre());
		}
		
	}
	
	

}
