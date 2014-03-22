package connexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLAccess {
	private static Connection connect = null;
	private Statement statement = null;


	/**
	 * @return the connection to the DBB
	 * @throws Exception 
	 */
	public static Connection getConnect() throws Exception {
		if (connect == null)
			MySQLAccess.initialization();
		return connect;
	}
	
	/**
	 * Initialize the connexion
	 */
	private static void initialization(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
						
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/facebook", "root", "");
		} catch(Exception se){
			System.err.println("Impossible de se connecter à la BDD : " + se.getMessage() + " Stack:" +se.getStackTrace());
		}
	}
}
