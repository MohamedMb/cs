package benchmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLConnexionTest {
	public static String DRIVER = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	public static String USER = "root";
	public static String PASSWORD = "";
	public static int NB_CONNEXION = 1000;
	private Connection connexion;
	private boolean UTILISER_THREAD;
	
	private HashMap<Integer, Long> dureesConnexions = new HashMap<Integer, Long>();
	
	
	private int NB_REQ; 
	private String TYPE_REQ; //"select" ou "insert"
	
	public MySQLConnexionTest(boolean useThread, int nbQuery, String typeQuery) {
		UTILISER_THREAD = useThread;
		NB_REQ = nbQuery;
		TYPE_REQ = typeQuery;
	}
	
	
	public void startBenchmark() throws ClassNotFoundException, SQLException {
		long beginGlobal;
		long endGlobal;
		int connexionInitial = 200;
		int palier = 200;
		long begin;
		long end = 0;
		long dureeEtape;
		int operations = 0;
		
		Class.forName(DRIVER);
		
		beginGlobal = System.currentTimeMillis();
		begin = System.currentTimeMillis();
		
		if(UTILISER_THREAD) {
			begin = System.currentTimeMillis();
			for(int i = 0 ; i < NB_CONNEXION ; i++) {
				if(i == connexionInitial) {
					end = System.currentTimeMillis();
					//System.out.println(connexionInitial + " connexions a durée " + (end - begin) + " ms");
					
					dureeEtape = end-begin;
					Integer tempInteger = new Integer(Integer.valueOf(i));
					Long tempLong = new Long(Long.valueOf(dureeEtape));
					dureesConnexions.put(tempInteger, tempLong);
					
					connexionInitial += palier;
					i = 0;
					operations++;
					begin = System.currentTimeMillis();
				}
				Thread threadConnexion = new Thread(new ConnexionThreadTest(NB_REQ, TYPE_REQ));
				threadConnexion.run();
			}
			end = System.currentTimeMillis();
			//System.out.println(connexionInitial + " connexions a durée " + (end - begin) + " ms");
			dureeEtape = end-begin;
			Integer tempInteger = new Integer(Integer.valueOf(connexionInitial));
			Long tempLong = new Long(Long.valueOf(dureeEtape));
			dureesConnexions.put(tempInteger, tempLong);
		}
		else {
			begin = System.currentTimeMillis();
			for(int i = 0 ; i < NB_CONNEXION ; i++) {
				if(i == connexionInitial) {
					end = System.currentTimeMillis();
					//System.out.println(connexionInitial + " connexions a durée " + (end - begin)  + " ms");
					dureeEtape = end-begin;
					Integer tempInteger = new Integer(Integer.valueOf(i));
					Long tempLong = new Long(Long.valueOf(dureeEtape));
					dureesConnexions.put(tempInteger, tempLong);
					
					connexionInitial += palier;
					i = 0;
					operations++;
					begin = System.currentTimeMillis();
				}
				connexion = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String req = "";
				if(TYPE_REQ == "select") {
					req = "SELECT * FROM facebook.utilisateur";
					PreparedStatement ps = connexion.prepareStatement(req);
					for(int j = 0 ; j < NB_REQ ; j++) {
						ResultSet res = ps.executeQuery();
						res.close();
					}
					ps.close();
				}
				else if(TYPE_REQ == "insert") {
					req = "INSERT INTO utilisateur(`nom`,`prenom`, `password`) values('test', 'test', 'password')";
					PreparedStatement ps = connexion.prepareStatement(req);
					for(int j = 0 ; j < NB_REQ ; j++) {
						ps.executeUpdate();
					}
					ps.close();
				}
				connexion.close();
			}
			end = System.currentTimeMillis();
			//System.out.println(connexionInitial + " connexions a durée " + (end - begin) + " ms");
			
			dureeEtape = end-begin;
			Integer tempInteger = new Integer(Integer.valueOf(connexionInitial));
			Long tempLong = new Long(Long.valueOf(dureeEtape));
			dureesConnexions.put(tempInteger, tempLong);
		}
		
		endGlobal = System.currentTimeMillis();
		System.out.println("Temps total des opérations d'exécution des " + operations + " opérations a duré " + (endGlobal - beginGlobal)  + " ms");
	}
	
	/**
	 * Pour obtenir les infos afin de dessiner le graph et à recupérer via ajax
	 * @throws Exception 
	 */
	public HashMap<Integer, Long> getDureesConnexions() throws Exception {
		return dureesConnexions;
	}
}
