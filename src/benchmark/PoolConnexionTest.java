package benchmark;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Map.Entry;

//import org.apache.log4j.Logger;
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
	private static int MAX_CONNECTIONS = 1000; //limite qu'il ne dépasse pas !
	private static int INITIAL_CONNECTIONS = 1000; //créé les connexion sur place
	private static final boolean WAIT_IF_BUSY = true;
	
	public static boolean UTILISER_THREAD = false;
	
	public static int NB_REQ = 1; 
	public static String TYPE_REQ = "select"; //sinon "insert"
	
	private static long dureeEtape;
	public static HashMap<Integer, Long> dureesConnexions = new HashMap<Integer, Long>();

	//private Logger logger = Logger.getLogger(getClass());
	
	/*public PoolConnexionTest(boolean useThread, int nbQuery, String typeQuery) {
		UTILISER_THREAD = useThread;
		NB_REQ = nbQuery;
		TYPE_REQ = typeQuery;
	}*/
	@Test
	public static void startBenchmark(){
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
						Thread threadConnexion = new Thread(new ConnexionThreadTest(pool, conn.get(k), NB_REQ, TYPE_REQ));
						threadConnexion.run();
					}
					else {
						
						String req = "";
						if(TYPE_REQ == "select") {
							req = "SELECT * FROM facebook.utilisateur";
							PreparedStatement ps = conn.get(k).prepareStatement(req);
							ResultSet res = ps.executeQuery();
							for(int j = 0 ; j < NB_REQ ; j++) {
								//res = ps.executeQuery();
								res = ps.executeQuery();
							}
							res.close();
						}
						else if(TYPE_REQ == "insert") {
							req = "INSERT INTO utilisateur(`nom`,`prenom`, `password`) values('test', 'test', 'password')";
							PreparedStatement ps = conn.get(k).prepareStatement(req);
							for(int j = 0 ; j < NB_REQ ; j++) {
								//res = ps.executeQuery();
								ps.executeUpdate();
							}
						}
						
						//res.close();
						pool.releaseConnection(conn.get(k)); // Renvoi de la connexion vers le pool
					}
				}
				
				end = System.currentTimeMillis();
				conn = null;
				nbConnexionPool++;
				dureeEtape = end-begin;
				Integer tempInteger = new Integer(Integer.valueOf(i));
				Long tempLong = new Long(Long.valueOf(dureeEtape));
				dureesConnexions.put(tempInteger, tempLong);
				
				System.out.println("Pool avec " + i + " connexions a durée " + (end - begin) + " ms");
			}
			endGlobal = System.currentTimeMillis();
			pool.closeAllConnections();
			pool = null;
			System.out.println("Temps total des opérations d'exécution des " + nbConnexionPool + " pools a duré " + (endGlobal - beginGlobal) + " ms");
			//System.out.println(dureesConnexions);
			/*for(int i = 0 ; i < dureesConnexions.size() ; i++) {
				System.out.println(dureesConnexions.get(i));
			}*/
			/*for (Entry<Integer, Long> entry : dureesConnexions.entrySet()) {
	            System.out.println(entry.getKey() + " -> " + entry.getValue());
	        }*/
			
			/*Iterator<Integer> keyHashMapIterator = dureesConnexions.keySet().iterator();
			while (keyHashMapIterator.hasNext()) {
				Integer key = keyHashMapIterator.next();
			   System.out.println(key + " --> " + dureesConnexions.get(key));
			}*/
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Pour obtenir les infos afin de dessiner le graph et à recupérer via ajax
	 * @throws Exception 
	 */
	public HashMap<Integer, Long> getDureesConnexions() throws Exception {
		HashMap<Integer, Long> returnResult = new HashMap<Integer, Long>();
		if(dureesConnexions != null) {
			//Remise dans l'ordre
			Iterator<Integer> keyHashMapIterator = dureesConnexions.keySet().iterator();
			while (keyHashMapIterator.hasNext()) {
				Integer key = keyHashMapIterator.next();
				returnResult.put(key, dureesConnexions.get(key));
			}
			return returnResult;
		}
		throw new Exception();
	}
}