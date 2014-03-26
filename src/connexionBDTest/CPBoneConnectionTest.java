package connexionBDTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


public class CPBoneConnectionTest {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";
	//3020 limite ordinateur mohamed, à changer !!!!!!!!!!!!!!!
	private static final int MAX_CONNECTIONS_PARTITION = 500; //limite qu'il ne dépasse pas !
	private static final int MIN_CONNECTIONS_PARTITION = 100; //créé les connexion sur place
	public static int NB_PARTITION = 10;
	private int nbConnexion = 3000;
	private BoneCPConfig config = new BoneCPConfig();
	private BoneCP pool = null;
	
	private ArrayList<HashMap<Integer, Long>> dureesConnexions = null;
	

	//private Logger logger = Logger.getLogger(getClass());


	@Test
	public void connexionBDTest() throws ClassNotFoundException{
		long beginGlobal;
		long endGlobal;
		
		int palier = 800;
		
		long begin = 0;
		long end;
		
		int nbConnexionPool = 0;
		beginGlobal = System.currentTimeMillis();
		try {
			Class.forName(DRIVER);
			config.setJdbcUrl(URL);
			config.setUsername(USER_NAME);
			config.setPassword(PASSWORD);
			config.setMaxConnectionsPerPartition(MAX_CONNECTIONS_PARTITION);
			config.setMinConnectionsPerPartition(MIN_CONNECTIONS_PARTITION);
			config.setPartitionCount(NB_PARTITION);
			pool = new BoneCP(config);
			beginGlobal = System.currentTimeMillis();
			for (int i = palier; i <= nbConnexion ; i += palier) {
				begin = System.currentTimeMillis();
				List<Connection> conn = new ArrayList<Connection>();
				for(int j = 0 ; j < i ; j++) {
					conn.add(pool.getConnection()); // Prends la connexion par référence du pool
				}
				for (int k = 0 ; k < conn.size() ; k++) {
					PreparedStatement ps = conn.get(k).prepareStatement("SELECT * FROM facebook.utilisateur");
					ResultSet res = ps.executeQuery();
					res.close();
					//if(NB_PARTITION <= 3)
						conn.get(k).close();
				}
				
				end = System.currentTimeMillis();
				conn = null;
				nbConnexionPool++;
				//long dureeEtape = end-begin;
				//dureesConnexions.add(new HashMap<Integer, Long>(Integer.valueOf(i), Long.valueOf(dureeEtape)));
				System.out.println("Pool CPBone avec " + i + " connexions a durée " + (end - begin) + " ms");
				
			}
			endGlobal = System.currentTimeMillis();
			pool = null;
			System.out.println("Temps total des opérations d'exécution des " + nbConnexionPool + " pools a duré " + (endGlobal - beginGlobal) + " ms");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
