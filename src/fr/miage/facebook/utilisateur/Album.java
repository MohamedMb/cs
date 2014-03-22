/**
 * Album photo d'un utilisateur
 */
package fr.miage.facebook.utilisateur;

import java.util.Set;

import fr.miage.facebook.BusinessEntity;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public class Album extends BusinessEntity {
	/**
	 * Libellé de l'album photo
	 */
	private String libelle;
	
	/**
	 * Liste des photos composants l'album
	 */
	private Set<Photo> photos;

	/**
	 * 
	 * @return le libellé de l'album photo
	 */
	public String getLibelle() {return libelle;}

	/**
	 * Définit le libellé de la photo
	 * @param libelle Le libellé de la photo
	 */
	public void setLibelle(String libelle) {this.libelle = libelle;}

	/**
	 * 
	 * @return La liste des photos de l'album
	 */
	public Set<Photo> getPhotos() {return photos;}

	/**
	 * La liste des albums photos à définir
	 * @param photos La liste de photos
	 */
	public void setPhotos(Set<Photo> photos) {this.photos = photos;}
}
