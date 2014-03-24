/**
 * Utilisateur de l'application
 */
package fr.miage.facebook.utilisateur;

import java.util.Calendar;
import java.util.Set;

import fr.miage.facebook.BusinessEntity;

/**
 * @author Stephane
 *
 */
public class Utilisateur extends BusinessEntity {
	
	//--- propriétés ---
	private String nom;						// nom de l'utilisateur
	private String prenom;					// prénom de l'utilisateur
	private String mail;					// adresse mail de l'utilisateur
	private String telephone;				// numéro de téléphone de l'utilisateur
	private Calendar dateNaissance;			// date de naissance de l'utilisateur
	private String password;				// mot de passe de l'utilisateur
	private Set<Utilisateur> amis;			// liste d'amis de l'utilisateur
	private Set<Photo> photos;				// liste des photos sans album
	private Set<Album> albums;				// liste des albums photos
	private Set<Utilisateur> demandes;		// liste des demandes d'amis
	
	//--- getters ---
	public String getNom() {return nom;}
	public String getPrenom() {return prenom;}
	public String getMail() {return mail;}
	public String getTelephone() {return telephone;}
	public Calendar getDateNaissance() {return dateNaissance;}
	public String getPassword() {return password;}
	public Set<Utilisateur> getAmis() {return amis;}
	public Set<Photo> getPhotos() {return photos;}
	public Set<Album> getAlbums() {return albums;}
	
	//--- setters ---
	public void setNom(String nom) {this.nom = nom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}
	public void setMail(String mail) {this.mail = mail;}
	public void setTelephone(String telephone) {this.telephone = telephone;}
	public void setDateNaissance(Calendar dateNaissance) {this.dateNaissance = dateNaissance;}
	public void setPassword(String password) {this.password = password;}
	public void setAmis(Set<Utilisateur> amis) {this.amis = amis;}
	public void setPhotos(Set<Photo> photos) {this.photos = photos;}
	public void setAlbums(Set<Album> albums) {this.albums = albums;}
	
	/**
	 * Constructeur
	 */
	public Utilisateur(){};
	
	/**
	 * Constructeur
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @param telephone
	 * @param dateNaissance
	 * @param password
	 */
	public Utilisateur(String nom, String prenom, String mail,
					   String telephone, Calendar dateNaissance, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.telephone = telephone;
		this.dateNaissance = dateNaissance;
		this.password = password;
	}
	
	public Set<Utilisateur> getDemandes() {
		return demandes;
	}
	
	public void setDemandes(Set<Utilisateur> demandes) {
		this.demandes = demandes;
	}
	
	public String toString(){
		return super.toString() + " " + this.getPrenom() + " " + this.getNom();
	}
}
