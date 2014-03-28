package benchmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Mohamed
 *
 */
public class ConnexionThreadTest extends Thread {
	private Connection connexion;
	public static String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	public static String USER = "root";
	public static String PASSWORD = "";
	
	public static int NB_REQ; 
	public static String TYPE_REQ; //sinon "insert"
	
	/**
	 * Thread sans gestion d'un pool de connexion
	 * @param connexion
	 * @throws SQLException 
	 */
	public ConnexionThreadTest(int nbReq, String typeReq) throws SQLException {
		this.connexion = DriverManager.getConnection(URL, USER, PASSWORD);
		NB_REQ = nbReq;
		TYPE_REQ = typeReq;
	}
	
	/**
	 * Thread avec gestion d'un pool de connexion
	 * @param pool
	 * @param connexion
	 */
	public ConnexionThreadTest(Connection connexion, int nbReq, String typeReq) {
		this.connexion = connexion;
		NB_REQ = nbReq;
		TYPE_REQ = typeReq;
	}
	
	@Override
	public void run() {
		try {
			String req = "";
			if(TYPE_REQ.equals("select")) {
				req = "SELECT * FROM facebook.utilisateur";
				PreparedStatement ps = connexion.prepareStatement(req);
				for(int j = 0 ; j < NB_REQ ; j++) {
					ResultSet res = ps.executeQuery();
					res.close();
				}
			}
			else if(TYPE_REQ.equals("insert")) {
				req = "INSERT INTO utilisateur(`nom`,`prenom`, `password`) values('test', 'test', 'password')";
				PreparedStatement ps = connexion.prepareStatement(req);
				for(int j = 0 ; j < NB_REQ ; j++) {
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.interrupt();
	}
}
