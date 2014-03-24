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
	
	private static final long serialVersionUID = 8522485273506202275L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		
		resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/connexion"));
	}
}
