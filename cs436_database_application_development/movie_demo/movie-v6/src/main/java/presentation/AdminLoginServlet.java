package presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import domain.Admin;
import domain.User;
import service.AdminService;
import service.UserService;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("admin-login-username");
		String pass = request.getParameter("admin-login-password");
		Config config = new Config();
		AdminService as = config.getAdminService();
		
		if (as.checkLogin(uname, pass)) {
			HttpSession session = request.getSession();
			session.setAttribute("config", config);
			session.setAttribute("admin-service", as);
			Admin a = new Admin(uname, pass);
			session.setAttribute("admin", a);
			response.sendRedirect("admin-home.jsp");
		}
		else {
			response.sendRedirect("admin-login.jsp");
		}
	}

}
