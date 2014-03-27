package benchmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;

/**
 * 
 * @author Mohamed
 *
 */
public class ConnexionThreadTest extends Thread {
	private Connection connexion;
	private CustomConnectionPoolImpl pool = null;
	public static String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	public static String USER = "root";
	public static String PASSWORD = "";
	//private long begin;
	//private long end;
	//private long dureeEtape;
	
	//public static HashMap<Integer, Long> dureesConnexions = new HashMap<Integer, Long>();
	
	private int NB_REQ; 
	private String TYPE_REQ; //sinon "insert"
	
	/**
	 * Thread sans gestion d'un pool de connexion
	 * @param connexion
	 * @throws SQLException 
	 */
	public ConnexionThreadTest(int nbReq, String typeReq) throws SQLException {
		//begin = System.currentTimeMillis();
		this.connexion = DriverManager.getConnection(URL, USER, PASSWORD);
		NB_REQ = nbReq;
		TYPE_REQ = typeReq;
	}
	
	/**
	 * Thread avec gestion d'un pool de connexion
	 * @param pool
	 * @param connexion
	 */
	public ConnexionThreadTest(CustomConnectionPoolImpl pool, Connection connexion, int nbReq, String typeReq) {
		this.connexion = connexion;
		this.pool = pool;
		NB_REQ = nbReq;
		TYPE_REQ = typeReq;
	}
	
	@Override
	public void run() {
		try {
			String req = "";
			if(TYPE_REQ == "select") {
				req = "SELECT * FROM facebook.utilisateur";
				PreparedStatement ps = connexion.prepareStatement(req);
				for(int j = 0 ; j < NB_REQ ; j++) {
					//res = ps.executeQuery();
					ps.executeQuery();
				}
				//end = System.currentTimeMillis();
				
			}
			else if(TYPE_REQ == "insert") {
				req = "INSERT INTO utilisateur(`nom`,`prenom`, `password`) values('test', 'test', 'password')";
				PreparedStatement ps = connexion.prepareStatement(req);
				for(int j = 0 ; j < NB_REQ ; j++) {
					//res = ps.executeQuery();
					ps.executeUpdate();
				}
				//end = System.currentTimeMillis();
			}
			//res.close();
			if(pool != null) {
				pool.releaseConnection(connexion);
			}
			else {
				connexion.close();
			}
			/*dureeEtape = end-begin;
			Integer tempInteger = new Integer(Integer.valueOf(i));
			Long tempLong = new Long(Long.valueOf(dureeEtape));
			dureesConnexions.put(tempInteger, tempLong);*/
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
