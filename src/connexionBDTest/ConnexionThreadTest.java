package connexionBDTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	/**
	 * Thread sans gestion d'un pool de connexion
	 * @param connexion
	 * @throws SQLException 
	 */
	public ConnexionThreadTest() throws SQLException {
		this.connexion = DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	/**
	 * Thread avec gestion d'un pool de connexion
	 * @param pool
	 * @param connexion
	 */
	public ConnexionThreadTest(CustomConnectionPoolImpl pool, Connection connexion) {
		this.connexion = connexion;
		this.pool = pool;
	}
	
	@Override
	public void run() {
		try {
			PreparedStatement ps = connexion.prepareStatement("SELECT * FROM facebook.utilisateur");
			ResultSet res = ps.executeQuery();
			res.close();
			if(pool != null) {
				pool.releaseConnection(connexion);
			}
			else {
				connexion.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
