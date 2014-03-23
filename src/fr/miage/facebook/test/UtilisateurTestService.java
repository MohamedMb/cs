/**
 * 
 */
package fr.miage.facebook.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.miage.facebook.utilisateur.Utilisateur;
import fr.miage.facebook.utilisateur.UtilisateurService;

/**
 * @author Stephane
 *
 */
public class UtilisateurTestService {
	private static UtilisateurService utilisateurService;
	
	@BeforeClass
	public static void initialization(){
		if(utilisateurService == null)
			utilisateurService = new UtilisateurService(); 
	}
	
	@Test
	@Ignore
	public void inscription(){
		List<String> firstnames = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		try {
			//Chargement des prénoms
			FileReader firstNamesFile = new FileReader(new File("src/resources/Prenom.txt"));
			BufferedReader brFirstnames = new BufferedReader(firstNamesFile);
			
			String data = null;
			
			while ((data = brFirstnames.readLine()) != null){
				firstnames.add(data.trim());
			}
			
			brFirstnames.close();
			firstNamesFile.close();
			
			//Chargements des noms
			FileReader namesFile = new FileReader(new File("src/resources/Nom.txt"));
			BufferedReader brNames = new BufferedReader(namesFile);
			
			data = null;
			while ((data = brNames.readLine()) != null){
				names.add(data.toUpperCase().trim());
			}
			brNames.close();
			namesFile.close();
		} catch (Exception e) {
			System.err.println("Impossible de charger les noms/prénoms de test : " + e.getStackTrace());
		}
		
		//Génération d'utilisateurs
		for(int i = 0; i < 5000; i++){
			Random random = new Random();
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setNom(names.get(random.nextInt(names.size())));
			utilisateur.setPrenom(firstnames.get(random.nextInt(firstnames.size())));
			utilisateur.setPassword("password");
			utilisateur.setMail((utilisateur.getNom() + "." + utilisateur.getPrenom() + "@miage.fr").toLowerCase());
			assertEquals(utilisateur, utilisateurService.inscrire(utilisateur));
		}
		
		
	}
	
	@Test
	@Ignore
	public void connexion(){
		Utilisateur stephane = new Utilisateur();
		String mail = "lopes.stephane1@miage.fr";
		String password = "password";
		stephane = utilisateurService.connexion(mail, password);
		assertTrue(stephane.getId().equals(1));
	}
	
	@Test
	@Ignore
	public void generateAmis(){
		Connection connexion;
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try {
			connexion = UtilisateurService.getContext().getInstance().getConnection();
			Statement stmt = connexion.createStatement();
			String query = "SELECT id FROM facebook.utilisateur ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setId(rs.getInt("ID"));
				utilisateurs.add(utilisateur);
			}
			for(int i = 0; i < 5000; i++){
				Random random = new Random();
				Random random2 = new Random();
				Utilisateur u1, u2;
				u1 = utilisateurs.get(random.nextInt(utilisateurs.size()));
				u2 = utilisateurs.get(random2.nextInt(utilisateurs.size()));
				utilisateurService.ajouter(u1, u2);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*@Test
	public void getDemandeAmis(){
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(1);
		List<Utilisateur> demandes = UtilisateurService.getDemandeAmis(utilisateur);
		System.out.println(demandes);
	}*/
	
	
}
