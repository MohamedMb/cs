/**
 * Service utilisé par les utilisateurs
 */
package fr.miage.facebook.utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
//import com.mysql.jdbc.Driver;

//import connexion.MySQLAccess;

import fr.miage.facebook.BusinessEntityService;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public class StatutService extends BusinessEntityService<Utilisateur> {
	
	public static void getStatuts(Utilisateur utilisateur){
		Connection connexion;
		try {
			connexion = UtilisateurService.getContext().getInstanceBoneCP().getConnection();
			List<Statut> statuts = new ArrayList<Statut>();
			Statement stmt = connexion.createStatement();
			String query ="SELECT * FROM STATUT WHERE ID_UTILISATEUR = " + utilisateur.getId();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				statuts.add(StatutService.load(rs));
			}
			rs.close();
			Collections.sort(statuts);
			utilisateur.setStatuts(statuts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected static Statut load(ResultSet rs) throws SQLException{
		Statut statut = new Statut();
		statut.setId(rs.getInt("id"));
		statut.setLibelle(rs.getString("libelle"));
		Calendar datePost = Calendar.getInstance();
		datePost.setTime(rs.getDate("date_post"));
		statut.setDatePost(datePost);
		return statut;
	}

}
