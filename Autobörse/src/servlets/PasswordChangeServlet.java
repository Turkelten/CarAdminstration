package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO TJERK
		int userId = Integer.parseInt(request.getParameter("userid"));
		String userName = request.getParameter("username");
		try
		{
			if(Controller.ResetUserPassword(userId, userName))
			{
				//Hat geklappt
			}
			else
			{
				//RIP

			}

		} catch (SQLException throwables)
		{
			//Irgend ne Query hat nicht hingehauen
			throwables.printStackTrace();

		} catch (MessagingException e)
		{
			//Das email senden hat nicht hingehauen
			e.printStackTrace();
		}

	}

}
