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
			
			Utilisateur profilUtilisateur;
			if (req.getParameter("id") != null && !utilisateur.getId().equals(Integer.parseInt(req.getParameter("id")))){
				profilUtilisateur = UtilisateurService.getUtilisateur(Integer.parseInt(req.getParameter("id")));
			}
			else{
				profilUtilisateur = utilisateur;
			}
				
			UtilisateurService.synchronization(profilUtilisateur);
			session.setAttribute("profilUtilisateur", profilUtilisateur);
			
			// recherche des amis de l'utilisateur
			List<Utilisateur> amis = UtilisateurService.getFriends(profilUtilisateur);
			List<Statut> statuts = UtilisateurService.getStatuts(profilUtilisateur, true);
			// recherche des statuts de l'utilisateur
			req.setAttribute("statuts", statuts);
			req.setAttribute("amis", amis);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/profil.jsp").forward(req, resp);
		}else
			resp.sendRedirect(resp.encodeRedirectURL("connexion"));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur)session.getAttribute(UtilisateurService.currentUser);
		Utilisateur profilUtilisateur = (Utilisateur)session.getAttribute("profilUtilisateur");
		
		if (!utilisateur.equals(profilUtilisateur)){
			UtilisateurService.synchronization(utilisateur);
			if (!utilisateur.getDemandes().contains(profilUtilisateur)){
				if (!utilisateur.getAmis().contains(profilUtilisateur)){
					UtilisateurService.demandeAmis(utilisateur, profilUtilisateur);
				}
				else
					UtilisateurService.accepterDemande(profilUtilisateur, utilisateur);
			}
		}
		this.doGet(req, resp);
	}
}
