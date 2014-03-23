package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class ConnexionServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3976021118683231696L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/connexion.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Utilisateur utilisateur = null;
		if (req.getParameter("mail") == null || req.getParameter("password") == null)
			doGet(req, resp);
		else{
			UtilisateurService utilisateurService = new UtilisateurService();
			utilisateur = utilisateurService.connexion(req.getParameter("mail"), req.getParameter("password"));
			if (utilisateur != null){
				HttpSession session = req.getSession();
				session.setAttribute(UtilisateurService.currentUser, utilisateur);
				resp.sendRedirect(resp.encodeRedirectURL("index"));
			}
		}
	}
}
