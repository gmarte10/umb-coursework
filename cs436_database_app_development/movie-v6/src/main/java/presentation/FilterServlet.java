package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Movie;
import service.UserService;

/**
 * Servlet implementation class FilterServlet
 */
@WebServlet("/FilterServlet")
public class FilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		HttpSession session = request.getSession();
		UserService us = (UserService) session.getAttribute("user-service");
		List<Movie> movies = us.getMovies();
		List<Movie> chosen = new ArrayList<Movie>();
		for (Movie m : movies) {
			if (m.getName().equals(search)) {
				chosen.add(m);
			}
			if (m.getContentRating().equals(search)) {
				chosen.add(m);
			}
			if (m.getDirector().equals(search)) {
				chosen.add(m);
			}
			if (m.getGenre().equals(search)) {
				chosen.add(m);
			}
			if (Integer.toString(m.getPrice()).equals(search)) {
				chosen.add(m);
			}
			if (Integer.toString(m.getYear()).equals(search)) {
				chosen.add(m);
			}
		}
		session.setAttribute("search-list", chosen);
		response.sendRedirect("search.jsp");
	}
}
