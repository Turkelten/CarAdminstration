package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserChangeServlet
 */
@WebServlet("/UserChangeServlet")
public class UserChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserChangeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int UserId = Integer.parseInt(request.getParameter("userid"));
		String UserName = request.getParameter("username");
		String FirstName = request.getParameter("firstname");
		String LastName = request.getParameter("lastname");

		//TODO TJERK
		try
		{
			if(Controller.UpdateUser(UserId, UserName, FirstName, LastName))
			{
				//Hat funktioniert
			}
		} catch (SQLException throwables)
		{
			//Hat nicht funktioniert
			throwables.printStackTrace();
		}

	}

}
