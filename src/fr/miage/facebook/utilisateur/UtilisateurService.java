/**
 * Service utilisé par les utilisateurs
 */
package fr.miage.facebook.utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
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
	public static Utilisateur connexion(String mail, String password){
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
	
	public static void synchronization(Utilisateur utilisateur){
		UtilisateurService.getDemandeAmis(utilisateur);
		UtilisateurService.getFriends(utilisateur);
		StatutService.getStatuts(utilisateur);
	}
	
	/**
	 * Retourne la liste d'amis passé en paramètre
	 * @param utilisateur
	 * @return
	 */
	public static List<Utilisateur> getFriends(Utilisateur utilisateur){
		List<Utilisateur> amis = new ArrayList<Utilisateur>();
		try {
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			
			String query = "SELECT * FROM utilisateur WHERE id IN ("
							 + "SELECT a.id_ami FROM utilisateur u "
							 			+ "JOIN ami a ON u.id = a.id_utilisateur "
							 + "WHERE a.id_utilisateur = " + utilisateur.getId() + ")";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				amis.add(UtilisateurService.load(rs));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return amis;
	}
	
	public static void getDemandeAmis(Utilisateur utilisateur){
		Set<Utilisateur> amis = new HashSet<Utilisateur>();
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			String query = "SELECT ami.id_utilisateur as id, utilisateur.nom, utilisateur.prenom," +
						   " utilisateur.mail, ami.is_validation_demande, ami.date_demande, ami.date_reponse" +
						   " FROM facebook.ami " +
						   "INNER JOIN facebook.utilisateur on ami.id_utilisateur = utilisateur.id" +
						   " WHERE id_ami = " + utilisateur.getId() + " AND date_reponse is null";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				amis.add(UtilisateurService.load(rs));
			}
			rs.close();
			utilisateur.setDemandes(amis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void demandeAmis(Utilisateur demandeur, Utilisateur demande){
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("INSERT INTO ami (id_utilisateur, id_ami)" +
							   "VALUES ('" + demandeur.getId() + "', '" + demande.getId() + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void accepterDemande(Utilisateur demande, Utilisateur demandeur){
		try {
			//Connection connexion = UtilisateurService.getContext().getInstance().getConnection();
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("UPDATE ami " +
							   "SET date_reponse = '" + Calendar.getInstance().getTime() + "'," +
							   		" is_validation_demande = '" + 1 + "' " +
							   "WHERE id_utilisateur = " + demandeur.getId() + " AND id_ami = " + demande.getId());
			
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
			
			String query = "INSERT INTO statut (id_utilisateur, libelle) VALUES ('?', '?')";
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			preparedStatement.setInt(1, statut.getUtilisateur().getId());
			preparedStatement.setString(2, statut.getLibelle());
			
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Retourne la liste des statuts de l'utilisateur passé en paramètre.
	 * @param utilisateur
	 * @param is_statuts_perso Vrai si l'on veut récupérer seulement les statuts de l'utilisateur et non de ses amis.
	 * @return
	 */
	public static List<Statut> getStatuts (Utilisateur utilisateur, boolean is_statuts_perso){
		List<Statut> statuts = new ArrayList<Statut>();
		try {
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			
			String query = "SELECT * FROM statut s "
						 		  + "JOIN utilisateur u ON u.id = s.id_utilisateur WHERE s.id_utilisateur ";
			if (is_statuts_perso)
				query += "= " + utilisateur.getId();
			else
				query += "IN (SELECT a.id_ami "
						   + "FROM utilisateur u "
				 		   + "JOIN ami a ON u.id = a.id_utilisateur "
				 		   + "WHERE a.id_utilisateur = " + utilisateur.getId()
				 		   + " UNION SELECT " + utilisateur.getId() + ")";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Utilisateur user = UtilisateurService.load(rs);
				statuts.add(new Statut(user, rs.getString("libelle")));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statuts;
	}
	
	public static Utilisateur modifier(Utilisateur utilisateur){
		try {
			Connection connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			Statement stmt = connexion.createStatement();
			stmt.executeUpdate("UPDATE utilisateur " +
							   "SET nom = '" + utilisateur.getNom() + "'," +
							   " prenom = '" + utilisateur.getPrenom() + "' " +
							   "WHERE id = " + utilisateur.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return utilisateur;
	}
}
