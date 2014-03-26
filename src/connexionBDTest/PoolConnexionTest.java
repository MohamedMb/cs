package connexionBDTest;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;


/**
 * 
 * @author Bouhlal
 *
 */
 
 
public class PoolConnexionTest {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	  private static final String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";
	//3020 limite ordinateur mohamed, à changer !!!!!!!!!!!!!!!
	private static final int MAX_CONNECTIONS = 1000; //limite qu'il ne dépasse pas !
	private static final int INITIAL_CONNECTIONS = 1000; //créé les connexion sur place
	private static final boolean WAIT_IF_BUSY = true;
	
	private ArrayList<HashMap<Integer, Long>> dureesConnexions = null;
	
	private static final boolean UTILISER_THREAD = false;

	private Logger logger = Logger.getLogger(getClass());


	@Test
	public void connexionBDTest(){
		long beginGlobal;
		long endGlobal;
		
		int palier = 200;
		
		long begin = 0;
		long end;
		
		int nbConnexionPool = 0;
		beginGlobal = System.currentTimeMillis();
		try {
			CustomConnectionPoolImpl pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
					INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
			pool.run();
			beginGlobal = System.currentTimeMillis();
			for (int i = palier; i <= MAX_CONNECTIONS ; i += palier) {
				begin = System.currentTimeMillis();
				List<Connection> conn = new ArrayList<Connection>();
				for(int j = 0 ; j < i ; j++) {
					conn.add(pool.getConnection()); // Prends la connexion par référence du pool
				}
				for (int k = 0 ; k < conn.size() ; k++) {
					if(UTILISER_THREAD) {
						Thread threadConnexion = new Thread(new ConnexionThreadTest(pool, conn.get(k)));
						threadConnexion.run();
					}
					else {
						PreparedStatement ps = conn.get(k).prepareStatement("SELECT * FROM facebook.utilisateur");
						ResultSet res = ps.executeQuery();
						res.close();
						pool.releaseConnection(conn.get(k)); // Renvoi de la connexion vers le pool
					}
				}
				
				end = System.currentTimeMillis();
				conn = null;
				nbConnexionPool++;
				//long dureeEtape = end-begin;
				//dureesConnexions.add(new HashMap<Integer, Long>(Integer.valueOf(i), Long.valueOf(dureeEtape)));
				System.out.println("Pool avec : " + i + " connexions a durée : " + (end - begin));
				
			}
			endGlobal = System.currentTimeMillis();
			pool = null;
			System.out.println("Temps total des opérations d'exécution des " + nbConnexionPool + " pools a duré " + (endGlobal - beginGlobal));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Pour obtenir les infos afin de dessiner le graph et à recupérer via ajax
	 * @throws Exception 
	 */
	public ArrayList<HashMap<Integer, Long>> getDureesConnexions() throws Exception {
		if(dureesConnexions != null) {
			return dureesConnexions;
		}
		throw new Exception();
	}
}