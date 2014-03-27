package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import fr.miage.facebook.forms.UploadForm;
import fr.miage.facebook.utilisateur.Photo;
import fr.miage.facebook.utilisateur.Statut;
import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class InfosPersoServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(UtilisateurService.currentUser);
		if (utilisateur != null) {
			// Synchronisation de l'utilisateur avec les infos en BDD
			if (req.getParameter("InputPassword").equals(req.getParameter("InputPassword2"))){
				Utilisateur u =UtilisateurService.connexion(utilisateur.getMail(), req.getParameter("InputPassword"));
				if (u != null && u.getId().equals(utilisateur.getId())){
					utilisateur.setNom(req.getParameter("InputNom"));
					utilisateur.setPrenom(req.getParameter("InputPrenom"));
					utilisateur = UtilisateurService.modifier(utilisateur);
				}
			}
			
			resp.sendRedirect(resp.encodeRedirectURL("option"));
		}else
			resp.sendRedirect(resp.encodeRedirectURL("connexion"));
	}
}
