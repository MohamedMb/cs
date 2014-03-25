package fr.miage.facebook.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import fr.miage.facebook.utilisateur.Photo;

public final class UploadForm {
	
	private static final String CHAMP_FICHIER = "photo";
	private static final int TAILLE_TAMPON = 1024;
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat() {return resultat;}
	public Map<String, String> getErreurs() {return erreurs;}
	
	public Photo enregistrerFichier(HttpServletRequest request, String chemin) {
		Photo photo = new Photo();
		String nomFichier = null;
		InputStream contenuFichier = null;
		try {
			Part part = request.getPart("file");
			nomFichier = getNomFichier(part);
			
			if (nomFichier != null && !nomFichier.isEmpty()) {
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
									   .substring(nomFichier.lastIndexOf('\\') + 1);
				contenuFichier = part.getInputStream();
			}
		} catch(IllegalStateException e) {
			e.printStackTrace();
			setErreur(CHAMP_FICHIER, "Les données envoyées sont trop volumineuses.");
		} catch(IOException e) {
			e.printStackTrace();
			setErreur(CHAMP_FICHIER, "Erreur de configuration du serveur");
		} catch(ServletException e) {
			e.printStackTrace();
			setErreur(CHAMP_FICHIER, "Ce type de requête n'est pas supporté, merci d'avance d'utiliser le formulaire prévu pour envoyer votre fichier.");
		}
		
		if (erreurs.isEmpty()) {
			try {
				validationPhoto(nomFichier, contenuFichier);
			} catch (Exception e) {
				setErreur(CHAMP_FICHIER, e.getMessage());
			}
			photo.setLien(nomFichier);
		}
		
		if (erreurs.isEmpty()) {
			try {
				ecrireFichier(contenuFichier, nomFichier, chemin);
			} catch(Exception e) {
				setErreur(CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le disque.");
			}
		}
		
		// initialisation du résultat global de la validation.
		if (erreurs.isEmpty()) {
			resultat = "Succès de l'envoi du fichier";
		}else{
			resultat = "Echec de l'envoi du fichier";
		}
		
		return photo;
	}
	
	private void validationPhoto(String nomFichier, InputStream contenuFichier) throws Exception {
		if (nomFichier == null || contenuFichier == null) {
			throw new Exception("Merci de sélectionner un fichier à envoyer");
		}
	}
	
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	private static String gatValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		}else{
			return valeur;
		}
	}
	
	/**
	 * Test si le champ traité est de type "File" et si c'est le cas retourne son nom.
	 * @param part
	 * @return
	 */
	public static String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	private void ecrireFichier(InputStream contenu, String nomFichier, String chemin) throws Exception {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(contenu, TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur = 0;
			while((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {}
			try {
				entree.close();
			} catch (IOException ignore) {}
		}
	}
}
