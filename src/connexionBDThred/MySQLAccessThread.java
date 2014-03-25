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
public class MySQLAccessThread extends Thread{

	private Connection cx = null;
	private int nbQuery;



	/**
	 * 
	 * @param nbQuery : nombre de requete a envoyer a la BD
	 */
	public MySQLAccessThread(int nbQuery){
		this.nbQuery = nbQuery;
	}

	/**
	 * Par défaut le nombre de requete est égal à 1
	 */

	public MySQLAccessThread(){
		this(1);
	}

	@Override
	public void run(){

		ResultSet res = null;
		//Connection cx = null;
		try {
			for ( int i = 0; i < this.nbQuery; i++){
				cx = MySQLAccess.getConnect();
				java.sql.PreparedStatement ps = cx.prepareStatement("SELECT * FROM facebook.utilisateur");
				res = ps.executeQuery();
				res.close();
				cx.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	//	public void closeConnection() throws SQLException{
	//			this.cx.close();
	//	}



}
