/**
 * Statut lié à un utilisateur
 */
package fr.miage.facebook.utilisateur;

import java.util.Calendar;

import fr.miage.facebook.BusinessEntity;

/**
 * @author Stephane
 * @version 0.1
 */
public class Statut extends BusinessEntity implements Comparable<Statut>{

	private Utilisateur utilisateur;			// créateur du statut
	private String libelle;						// contenu du statut
	private Calendar datePost;
	
	/**
	 * Constructeur.
	 * @param utilisateur Créateur du statut.
	 * @param libelle Contenu du statut.
	 */
	public Statut(Utilisateur utilisateur, String libelle) {
		this.utilisateur = utilisateur;
		this.libelle = libelle;
	}

	public Statut() {
	}

	//--- getters ---
	public Utilisateur getUtilisateur() {return utilisateur;}
	public String getLibelle() {return libelle;}
	
	//--- setters
	public void setUtilisateur(Utilisateur utilisateur) {this.utilisateur = utilisateur;}
	public void setLibelle(String libelle) {this.libelle = libelle;}

	public Calendar getDatePost() {
		return datePost;
	}

	public void setDatePost(Calendar datePost) {
		this.datePost = datePost;
	}

	@Override
	public int compareTo(Statut statut) {
		return -this.datePost.compareTo(statut.getDatePost());
	}
}
