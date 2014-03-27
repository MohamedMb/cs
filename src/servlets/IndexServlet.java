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

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 6935251785798019829L;
	private static final String CHEMIN_UPLOAD_PHOTO = "/upload";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		UtilisateurService us = new UtilisateurService();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(UtilisateurService.currentUser);
		String url = "";
		if (utilisateur != null) {
			// Synchronisation de l'utilisateur avec les infos en BDD
			UtilisateurService.synchronization(utilisateur);
			List<Statut> statuts = new ArrayList<Statut>(utilisateur.getStatuts().size());
			statuts.addAll(utilisateur.getStatuts());
			session.setAttribute("statuts", statuts);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/facebook/index.jsp").forward(req, resp);
		}else
			resp.sendRedirect(resp.encodeRedirectURL("connexion"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(UtilisateurService.currentUser);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html;charset=UTF-8");
		
		//--- ajout d'un nouveau statut ---
		if (req.getParameter("statut") != null) {
			String libelle = req.getParameter("statut");
			Statut statut = new Statut(utilisateur, libelle);
			
			UtilisateurService us = new UtilisateurService();
			if (us.ajouterStatut(statut)) {
				out.println("Le statut vient d'être ajouté.");
			}else{
				out.println("Une erreur est survenue lors de l'ajout du statut.");
			}
		
		//--- upload de fichier ---
		}else if (ServletFileUpload.isMultipartContent(req)) {
			String chemin = this.getServletConfig().getInitParameter(utilisateur.getId() + "/" + CHEMIN_UPLOAD_PHOTO);
			UploadForm form = new UploadForm();
			Photo photo = form.enregistrerFichier(req, chemin);
			Map<String, String> map = form.getErreurs();
			Set cles = map.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()){
			   String cle = (String) it.next();
			   out.println(map.get(cle));
			}
		}else{
			out.print("Erreur lors de l'import de photos") ;
		}
	}
}
