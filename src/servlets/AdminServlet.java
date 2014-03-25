package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.facebook.utilisateur.Statut;
import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 6935251785798019829L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/admin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doGet(req, resp);
	}

}
