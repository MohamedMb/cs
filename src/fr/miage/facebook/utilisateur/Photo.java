﻿/**
 * Photo déposées par un utilisateur connecté
 */
package fr.miage.facebook.utilisateur;

import java.net.URL;

import fr.miage.facebook.BusinessEntity;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public class Photo extends BusinessEntity {
	/**
	 * Lien permettant d'accéder à la photo
	 */
	private String lien;
	
	/**
	 * Constructeur
	 */
	public Photo() {}
	
	/**
	 * Constructeur
	 */
	public Photo(String lien) {this.lien = lien;}

	public String getLien() {return lien;}

	public void setLien(String lien) {this.lien = lien;}
	
	
}
