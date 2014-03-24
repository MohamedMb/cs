/**
 * Statut lié à un utilisateur
 */
package fr.miage.facebook.utilisateur;

import fr.miage.facebook.BusinessEntity;

/**
 * @author Stephane
 * @version 0.1
 */
public class Statut extends BusinessEntity {

	private Utilisateur utilisateur;			// créateur du statut
	private String libelle;						// contenu du statut
	
	/**
	 * Constructeur.
	 * @param utilisateur Créateur du statut.
	 * @param libelle Contenu du statut.
	 */
	public Statut(Utilisateur utilisateur, String libelle) {
		this.utilisateur = utilisateur;
		this.libelle = libelle;
	}

	//--- getters ---
	public Utilisateur getUtilisateur() {return utilisateur;}
	public String getLibelle() {return libelle;}
	
	//--- setters
	public void setUtilisateur(Utilisateur utilisateur) {this.utilisateur = utilisateur;}
	public void setLibelle(String libelle) {this.libelle = libelle;}
}
