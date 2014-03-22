/**
 * Contexte de persistence utilisé par l'application
 */
package connexion;
import fr.miage.facebook.pool.homemade.*;

import java.sql.SQLException;


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
			1842; //max sur machine avec 1GB de ram (prévoir 10% de moins pour plus de stabilité)
	
	
	public CustomConnectionPoolImpl getInstance() throws SQLException { //Contrôle de l'instance de l'objet (singleton)
		if(pool == null) {
			pool = new CustomConnectionPoolImpl(DRIVER, URL, USER, PASSWORD, NB_INITIAL_CONNEXION, MAX_CONNEXION, true);
		}
		return pool;
	}
	
	//public Connection getConnection() throws Exception{
		//return mySQLAccess.getConnect(); // Connexion directement avec le driver
		//CustomConnectionPoolImpl conn = new CustomConnectionPoolImpl("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/facebook", "root", "", 1, 100, false);
		//return conn.getConnection(); //Connexion via le connection pool
	//}
}
