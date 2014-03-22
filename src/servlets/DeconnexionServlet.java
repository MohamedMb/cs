package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class DeconnexionServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getSession().getAttribute(UtilisateurService.currentUser) != null)
			req.getSession().setAttribute(UtilisateurService.currentUser, null);
		resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "connexion"));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	

}
