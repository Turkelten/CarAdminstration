package servlets;

import Objects.Inserat;
import Objects.User;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InseratServlet
 */
@WebServlet("/InseratServlet")
public class InseratServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InseratServlet() {
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
		String beschreibung = request.getParameter("beschreibung");
		String marke = request.getParameter("marke");
		String PS = request.getParameter("PS");
		String verbrauch = request.getParameter("verbrauch");
		String groesse = request.getParameter("groesse");
		String kilometerstand = request.getParameter("kilometerstand");
		String Verbrauchsstoff = request.getParameter("verbrauchsstoff");
		String Bezeichnung = request.getParameter("bezeichnung");
		String Ausstattung = request.getParameter("ausstattung");

		Inserat newInserat = null;

		try
		{
			int iPS = Integer.parseInt(PS);
			float fVerbrauch = Float.parseFloat(verbrauch);
			int iGroesse = Integer.parseInt(groesse);
			int iKilometer = Integer.parseInt(kilometerstand);

			newInserat = Controller.InsertInserat(beschreibung, marke, iPS, fVerbrauch, iGroesse, iKilometer, Verbrauchsstoff,
					Bezeichnung, Ausstattung);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		catch (NumberFormatException numberFormatException)
		{
			//PS, Verbrauch, Groesse oder Kilometer im nicht valid (Buchstaben o.ä)

		}

		String destPage = "login.html";

		//TODO Turk
		if(newInserat != null)
		{
			destPage = "login.html";
		}
		else
		{
			destPage = "index.html";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
	}

}
