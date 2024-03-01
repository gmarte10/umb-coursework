package presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.Config;
import domain.Movie;
import service.AdminService;

/**
 * Servlet implementation class AddMovieServlet
 */
@WebServlet("/AddMovieServlet")
public class AddMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("add-id"));
		String name = request.getParameter("add-name");
		int price = Integer.parseInt(request.getParameter("add-price"));
		String genre = request.getParameter("add-genre");
		int year = Integer.parseInt(request.getParameter("add-year"));
		String rating = request.getParameter("add-rating");
		String director = request.getParameter("add-director");
		String image = request.getParameter("add-image");
		String video = request.getParameter("add-video");
		int rent = Integer.parseInt(request.getParameter("add-rent"));
		Movie m = new Movie(id, name, price, genre, year, rating, director, image, video, rent);
		Config config = new Config();
		AdminService as = config.getAdminService();
		as.addMovie(m);
		response.sendRedirect("admin-home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
