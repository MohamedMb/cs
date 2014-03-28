package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.miage.facebook.utilisateur.Photo;
import fr.miage.facebook.utilisateur.Statut;
import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

public class ProfilServlet extends HttpServlet {
	
	private static final long serialVersionUID = 642083537330985189L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(UtilisateurService.currentUser);
		if (utilisateur != null) {
			// recherche des statuts de l'utilisateur
			List<Statut> statuts = UtilisateurService.getStatuts(utilisateur, true);
			req.setAttribute("statuts", statuts);
			
			// recherche des photos de l'utilisateur
			List<Photo> listPhotos = new ArrayList<Photo>();
			listPhotos.add(new Photo("upload/image1.jpg"));
			listPhotos.add(new Photo("upload/image2.jpg"));
			listPhotos.add(new Photo("upload/image3.jpg"));
			listPhotos.add(new Photo("upload/image4.jpg"));
			listPhotos.add(new Photo("upload/image1.jpg"));
			listPhotos.add(new Photo("upload/image2.jpg"));
			listPhotos.add(new Photo("upload/image3.jpg"));
			listPhotos.add(new Photo("upload/image4.jpg"));
			listPhotos.add(new Photo("upload/image1.jpg"));
			req.setAttribute("photos", listPhotos);
			
			// recherche des amis de l'utilisateur
			List<Utilisateur> amis = UtilisateurService.getFriends(utilisateur);;
			req.setAttribute("amis", amis);	
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/profil.jsp").forward(req, resp);
		}else
			resp.sendRedirect(resp.encodeRedirectURL("connexion"));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
