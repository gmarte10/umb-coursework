package presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import domain.Movie;
import service.UserService;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		Config config = new Config();
		UserService us = config.getUserService();
		
		ArrayList<Movie> cart = new ArrayList<Movie>();
		HttpSession session = request.getSession();
        ArrayList<Movie> cartMovies = (ArrayList<Movie>) session.getAttribute("movies-cart");
        if (cartMovies == null) {
        	cart.add(us.getMovieById(id));
        	session.setAttribute("movies-cart", cart);
        	response.sendRedirect("home.jsp");
        } else {
        	cart = cartMovies;
        	boolean exist = false;
        	for (Movie m : cartMovies) {
        		if (m.getId() == id) {
        			exist = true;
        			out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='cart.jsp'>GO to Cart Page</a></h3>");
        		}
        	}
        	if (!exist) {
        		cart.add(us.getMovieById(id));
        		response.sendRedirect("home.jsp");
        	}
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
