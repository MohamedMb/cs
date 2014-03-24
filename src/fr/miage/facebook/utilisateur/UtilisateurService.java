/**
 * Service utilisé par les utilisateurs
 */
package fr.miage.facebook.utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import com.mysql.jdbc.Driver;

//import connexion.MySQLAccess;

import fr.miage.facebook.BusinessEntityService;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public class UtilisateurService extends BusinessEntityService<Utilisateur> {
	public static final String currentUser = "currentUser";
	
	
	/**
	 * Regroupement des utilisateurs de l'application
	 */
	private Map<Integer, Utilisateur> inscrits;
	
	/**
	 * Tente de se connecter à partir d'un mail et d'un mot de passe
	 * @param mail Email de l'utilisateur
	 * @param password Mot de passe de l'utilisateur
	 * @return l'utilisateur inscrit, null si aucun utilisateur ne correspond au arguments
	 */
	public Utilisateur connexion(String mail, String password){
		Utilisateur utilisateur = null;
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			String query = "SELECT * FROM facebook.utilisateur " +
						   "WHERE mail = '" + mail + "' AND password = '" + password + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				utilisateur = new Utilisateur();
				utilisateur.setId(rs.getInt("id"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setMail(rs.getString("mail"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return utilisateur;
	}
	
	/**
	 * 
	 * @param user L'utilisateur qui ajoute un amis
	 * @param amis L'ami a ajouter
	 */
	public void ajouter(Utilisateur user, Utilisateur amis){
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			String query = "INSERT INTO ami(id_utilisateur, id_ami) VALUES ( " + user.getId() + ", " + amis.getId() +")";
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Suppression d'un ami de la part d'un utilisateur
	 * @param user	Utilisateur dont on veut supprimer l'ami
	 * @param amis	Ami à supprimer
	 */
	public void supprimer(Utilisateur user, Utilisateur amis){}
	
	/**
	 * Blocage d'un utilisateur de la part d'un autre.
	 * @param user Utilisateur effectuant le blocage
	 * @param userToBlock Utilisateur à bloquer
	 */
	public void bloquer(Utilisateur user, Utilisateur userToBlock){}
	
	/**
	 * Ajout d'une photo de la part d'un utilisateur.
	 * @param user Utilisateur voulant ajouter la photo
	 * @param photo Photo à ajouter
	 */
	public void ajouterPhoto(Utilisateur user, Photo photo){}
	
	/**
	 * Suppression d'une photo de la part d'un utilisateur.
	 * @param user Utilisateur voulant supprimer la photo
	 * @param photo Photo à supprimer
	 */
	public void supprimerPhoto(Utilisateur user, Photo photo){}
	
	/**
	 * Ajout d'un album photo.
	 * @param user Utilisateur voulant ajouter l'album
	 * @param album Album à ajouter
	 */
	public void ajouterAlbum(Utilisateur user, Album album){}
	
	/**
	 * Suppression d'un album photo.
	 * @param user Utilisateur voulant supprimer l'album photo
	 * @param album Album à supprimer
	 */
	public void supprimerAlbum(Utilisateur user, Album album){}
	
	/**
	 * L'inscription de l'utilisateur entraîne sa sauvegarde dans la BDD
	 * @param utilisateur
	 * @return l'utilisateur sauvegardé
	 */
	public Utilisateur inscrire(Utilisateur utilisateur){
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("INSERT INTO utilisateur (nom, prenom, mail, password)" +
							   "VALUES ('" + utilisateur.getNom() + "', '" + utilisateur.getPrenom() + "', '" + utilisateur.getMail() + "', '" + utilisateur.getPassword() +"' )"
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return utilisateur;
	}
	
	/**
	 * Retourne la liste d'amis passé en paramètre
	 * @param user
	 * @return
	 */
	public static List<Utilisateur> getFriends(Utilisateur user){
		List<Utilisateur> amis = new ArrayList<Utilisateur>();
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			//TODO Créer la query pour récupérer la liste d'amis
			String query ="";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				amis.add(UtilisateurService.load(rs));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return amis;
	}
	
	public static void getDemandeAmis(Utilisateur user){
		Set<Utilisateur> amis = new HashSet<Utilisateur>();
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			String query ="SELECT * FROM ami WHERE id_utilisateur = " + user.getId() + " AND is_validation_demande = 0";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				amis.add(UtilisateurService.load(rs));
			}
			rs.close();
			user.setDemandes(amis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static Utilisateur load(ResultSet rs) throws SQLException{
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(rs.getInt("id"));
		utilisateur.setNom(rs.getString("nom"));
		utilisateur.setPrenom(rs.getString("prenom"));
		utilisateur.setMail(rs.getString("mail"));
		return utilisateur;
	}
	
	/*
	 * Ajout d'un statut pour un utilisateur.
	 * @param Statut statut Statut de l'utilisateur
	 * @return vrai si le statut à été correctement ajouté, faux sinon.
	 */
	public static boolean ajouterStatut(Statut statut) {
		try {
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			String query ="INSERT INTO statut ('id_utilisateur', 'libelle') VALUES ('" + statut.getUtilisateur().getId() + "', '" + statut.getLibelle() + "')";
			ResultSet rs = stmt.executeQuery(query);
			rs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
