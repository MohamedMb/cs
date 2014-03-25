/**
 * 
 */
package connexionBDThred;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;

import connexion.MySQLAccess;


/**
 * @author Bouhlal
 *
 */
public class MySQLAccessThred extends Thread{

	private Connection cx = null;
	private int nbQuery;



	/**
	 * 
	 * @param nbQuery : nombre de requete a envoyer a la BD
	 */
	public MySQLAccessThred(int nbQuery){
		this.nbQuery = nbQuery;
	}

	/**
	 * Par défaut le nombre de requete est égal à 1
	 */

	public MySQLAccessThred(){
		this(1);
	}

	@Override
	public void run(){

		ResultSet res = null;
		//Connection cx = null;


		try {

			cx = MySQLAccess.getConnect();

			for ( int i = 0; i < this.nbQuery; i++){
				java.sql.PreparedStatement ps = cx.prepareStatement("SELECT * FROM facebook.utilisateur");
				res = ps.executeQuery();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {
				//Fermer ResultSet
				res.close();

				//Fermer la connexion
				cx.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}


	//	public void closeConnection() throws SQLException{
	//			this.cx.close();
	//	}



}
