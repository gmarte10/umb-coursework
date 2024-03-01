package presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import domain.User;
import service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("login-email");
		String pass = request.getParameter("login-password");
		Config config = new Config();
		UserService us = config.getUserService();
		if (us.checkLogin(email, pass)) {
			HttpSession session = request.getSession();
			session.setAttribute("config-user", config);
			session.setAttribute("user-service", us);
			User u = new User(email, pass);
			session.setAttribute("user", u);
			response.sendRedirect("home.jsp");
		}
		else {
			response.sendRedirect("login.jsp");
		}
	}

}
