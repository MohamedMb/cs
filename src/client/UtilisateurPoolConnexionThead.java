/**
 * 
 */
package client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;

/**
 * @author Bouhlal
 *
 */
public class UtilisateurPoolConnexionThead extends Thread{

	private int nbQuery;
	private CustomConnectionPoolImpl pool;

	/**
	 * 
	 */
	public UtilisateurPoolConnexionThead(int nbQuery, CustomConnectionPoolImpl pool) {
		this.nbQuery = nbQuery;
		this.pool = pool;
	}

	public UtilisateurPoolConnexionThead(CustomConnectionPoolImpl pool) {
		this(1, pool);
	}



	@Override
	public void run(){

		Connection cx = null;
		
		this.pool.run();
		
		try {
			
			
			for ( int i = 0; i < this.nbQuery; i++){
				
				//Ouvrire la connexion
				cx = this.pool.getConnection();
				
				java.sql.PreparedStatement ps = cx.prepareStatement("SELECT * FROM facebook.utilisateur");
				ResultSet res = ps.executeQuery();
				
				res.close();
				
				//liberer la connexion
				this.pool.releaseConnection(cx);
				
				//fermer la connextion
				//cx.close();
				
			}

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



}
