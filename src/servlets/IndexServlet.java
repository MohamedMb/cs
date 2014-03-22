package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class IndexServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(UtilisateurService.currentUser);
		String url = "";
		if (utilisateur != null)
			this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/index.jsp").forward(req, resp);
		else
			resp.sendRedirect(resp.encodeRedirectURL("connexion"));
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
