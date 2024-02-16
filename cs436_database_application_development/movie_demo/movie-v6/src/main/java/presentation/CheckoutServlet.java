package presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import domain.Movie;
import domain.Order;
import domain.User;
import service.UserService;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Config config = new Config();
		UserService us = config.getUserService();
		ArrayList<Movie> cartMovies = (ArrayList<Movie>) session.getAttribute("movies-cart");
		if (cartMovies == null) {
			response.sendRedirect("cart.jsp");
		}
		if (cartMovies != null) {
			for (Movie m : cartMovies) {
				int id = idGen();
				User u = (User) session.getAttribute("user");
				String email = u.getEmail();
				LocalDate ld = LocalDate.now();
				String date = ld.toString();
				int movie_id = m.getId();
				Order o = new Order(id, email, movie_id, date);
				us.uploardOrder(o);
				
			}
			cartMovies = new ArrayList<Movie>();
	        session.setAttribute("movies-cart", cartMovies);
			out.println("<h3 style='color:crimson; text-align: center'>Your Order has been recieved. You will get your copy by email"
					+ " <a href='home.jsp'>Go to Home Page</a></h3>");
		}
	}
	
	private int idGen() {
		Config config = new Config();
		UserService us = config.getUserService();
		ArrayList<Order> o = (ArrayList<Order>) us.getAllOrders();
		Random ran = new Random();
		int id = ran.nextInt(100000);
		for (Order x : o) {
			if (id == x.getId()) {
				idGen();
			} else {
				break;
			}
		}
		return id;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
