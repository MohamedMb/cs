package benchmark;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gson.Gson;

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
	private boolean UTILISER_THREAD;
	public static int NB_REQ; 
	public static String TYPE_REQ; //sinon "insert"
	private static long dureeEtape;
	public static TreeMap<Integer, Integer> dureesConnexions = new TreeMap<Integer, Integer>();

	public PoolConnexionTest(boolean useThread, int nbQuery, String typeQuery) {
		UTILISER_THREAD = useThread;
		NB_REQ = nbQuery;
		TYPE_REQ = typeQuery;
	}
	
	public void startBenchmark(){
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
				
				//System.out.println(pool.getNumberOfAvailableConnections());
				for (int k = 0 ; k < i ; k++) {
					Connection conn = pool.getConnection();
					if(UTILISER_THREAD) {
						Thread threadConnexion = new Thread(new ConnexionThreadTest(conn, NB_REQ, TYPE_REQ));
						threadConnexion.run();
					}
					else {
						
						String req = "";
						if(TYPE_REQ.equals("select")) {
							req = "SELECT * FROM facebook.utilisateur where id = 1";
							PreparedStatement ps = conn.prepareStatement(req);
							for(int j = 0 ; j < NB_REQ ; j++) {
								ResultSet res = ps.executeQuery();
								res.close();
							}
						}
						else if(TYPE_REQ.equals("insert")) {
							req = "INSERT INTO utilisateur(`nom`,`prenom`, `password`) values('test', 'test', 'password')";
							PreparedStatement ps = conn.prepareStatement(req);
							for(int j = 0 ; j < NB_REQ ; j++) {
								ps.executeUpdate();
							}
						}
					}
					pool.releaseConnection(conn);
				}
				end = System.currentTimeMillis();
				nbConnexionPool++;
				dureeEtape = end-begin;
				Integer tempNbConnexion = new Integer(Integer.valueOf(i));
				Integer tempDuree = new Integer(Integer.valueOf(String.valueOf(dureeEtape)));
				dureesConnexions.put(tempNbConnexion, tempDuree);
				
				//System.out.println("Pool avec " + i + " connexions a durée " + (end - begin) + " ms");
			}
			endGlobal = System.currentTimeMillis();
			pool.closeAllConnections();
			pool = null;
			System.out.println("Temps total des opérations d'exécution des " + nbConnexionPool + " pools a duré " + (endGlobal - beginGlobal) + " ms");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Pour obtenir les infos afin de dessiner le graph et à recupérer via ajax
	 * @throws Exception 
	 */
	@Override
	public String toString() {
		String result = "";
		result = new Gson().toJson(dureesConnexions);
		return result;
	}
}