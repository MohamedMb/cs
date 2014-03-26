package connexionBDTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;

public class MySQLConnexionTest {
	public static String DRIVER = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	public static String USER = "root";
	public static String PASSWORD = "";
	public static int NB_CONNEXION = 1000;
	private Connection connexion;
	public static final boolean UTILISER_THREAD = false;
	
	@Test
	public void Test() throws ClassNotFoundException, SQLException {
		long beginGlobal;
		long endGlobal;
		int connexionInitial = 200;
		int palier = 200;
		long begin;
		long end = 0;
		int nbConnexion = 0;
		
		Class.forName(DRIVER);
		
		beginGlobal = System.currentTimeMillis();
		begin = System.currentTimeMillis();
		
		if(UTILISER_THREAD) {
			begin = System.currentTimeMillis();
			for(int i = 0 ; i < NB_CONNEXION ; i++) {
				if(i == connexionInitial) {
					end = System.currentTimeMillis();
					System.out.println(connexionInitial + " connexions a durée : " + (end - begin));
					connexionInitial += palier;
					i = 0;
					nbConnexion++;
					begin = System.currentTimeMillis();
				}
				Thread threadConnexion = new Thread(new ConnexionThreadTest());
				threadConnexion.run();
			}
			end = System.currentTimeMillis();
			System.out.println(connexionInitial + " connexions a durée : " + (end - begin));
		}
		else {
			begin = System.currentTimeMillis();
			for(int i = 0 ; i < NB_CONNEXION ; i++) {
				if(i == connexionInitial) {
					end = System.currentTimeMillis();
					System.out.println(connexionInitial + " connexions a durée : " + (end - begin));
					connexionInitial += palier;
					i = 0;
					nbConnexion++;
					begin = System.currentTimeMillis();
				}
				connexion = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connexion.prepareStatement("SELECT * FROM facebook.utilisateur");
				ResultSet res = ps.executeQuery();
				res.close();
				connexion.close();
			}
			end = System.currentTimeMillis();
			System.out.println(connexionInitial + " connexions a durée : " + (end - begin));
		}
		
		endGlobal = System.currentTimeMillis();
		System.out.println("Temps total des opérations d'exécution des " + nbConnexion + " opérations a duré " + (endGlobal - beginGlobal));
		
		
	}
}
