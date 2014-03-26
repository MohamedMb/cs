/**
 * Contexte de persistence utilisé par l'application
 */
package connexion;
import fr.miage.facebook.pool.homemade.*;

import java.sql.SQLException;
import java.sql.Connection;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
///import com.mysql.jdbc.Connection;


/**
 * @author Stephane
 *
 */
public class PersistenceContext {
	//private MySQLAccess mySQLAccess;
	private CustomConnectionPoolImpl pool = null;
	public static String DRIVER = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	public static String USER = "root";
	public static String PASSWORD = "";
	// Chaque connexion est créée dans un thread du côté de MySql
	public static int NB_INITIAL_CONNEXION = 10;
	public static int MAX_CONNEXION = /*152;Maximum configuré sur mysql (pour mettre autre chose, commande SQL : SET GLOBAL max_connections=xxx)*/
			184; //max sur machine avec 1GB de ram (prévoir 10% de moins pour plus de stabilité)
	public static int NB_PARTITION = 3;
	private BoneCPConfig config = new BoneCPConfig();
	private BoneCP poolBC = null;
	
	
	/**
	 * Notre Pool à nous
	 * @return
	 * @throws SQLException
	 */
	public CustomConnectionPoolImpl getInstance() throws SQLException { //Contrôle de l'instance de l'objet (singleton)
		if(pool == null) {
			pool = new CustomConnectionPoolImpl(DRIVER, URL, USER, PASSWORD, NB_INITIAL_CONNEXION, MAX_CONNEXION, true);
		}
		return pool;
	}
	
	/**
	 * Pool pro
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public BoneCP getInstanceBoneCP() throws SQLException, ClassNotFoundException {
		if(poolBC == null) {
			Class.forName(DRIVER);
			config.setJdbcUrl(URL);
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			config.setMaxConnectionsPerPartition(MAX_CONNEXION);
			config.setMinConnectionsPerPartition(NB_INITIAL_CONNEXION);
			config.setPartitionCount(NB_PARTITION);
			poolBC = new BoneCP(config);
		}
		return poolBC;
	}
	
	/**
	 * Connexion directe à la BdD sans pooling connection
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		MySQLAccess mySQLAccess = new MySQLAccess();
		return mySQLAccess.getConnect();
	}
}
