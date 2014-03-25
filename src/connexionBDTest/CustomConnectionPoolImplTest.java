/**
 * 
 */
package connexionBDTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;

/**
 * @author Bouhlal
 *
 */
public class CustomConnectionPoolImplTest {

	private static final int NB_QUERY = 5;
	private static final int NB_MINUTE = 1;


	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "mysql";
	private static final int MAX_CONNECTIONS = 15;
	private static final int INITIAL_CONNECTIONS = 2;
	private static final boolean WAIT_IF_BUSY = true;


	

	private CustomConnectionPoolImpl pool = null;


	private Logger logger = Logger.getLogger(getClass());


	private Connection cx;


	@Before
	public void initialization(){


		try {
			pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
					INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);

			logger.debug("Connection pool created" + pool);

			this.cx = this.pool.getConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	@After
	public void close(){
		//Fermer toutes les connexions 
		this.pool.closeAllConnections();
		logger.debug("All conncetions are closed");

		//Fermer la connxexion
		try {
			this.cx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void connexionBDTest(){

		ResultSet res = null;
		long begin = 0;
		long end;
		int nbCx = 0;
		
		begin = System.currentTimeMillis();
	

		try {
			for ( ; ; nbCx++){
				
				this.pool.run();

				//Envoyer les requetes
				for ( int i = 0; i < NB_QUERY; i++){
					java.sql.PreparedStatement ps = cx.prepareStatement("SELECT * FROM facebook.utilisateur");
					res = ps.executeQuery();
					res.close();
				}
								
				//Libérer la connexion pour un autre utilisateur
				this.pool.releaseConnection(cx);
				
				end = System.currentTimeMillis();
				
				
				if ( end  - begin >= NB_MINUTE * 60 * 1000){
					logger.info( "Pendant "+ NB_MINUTE  +" minute(s), on a " + nbCx + "connexion(s) BD simultanée(s)." );
					///System.out.println(this.pool.toString()+ "\n");
					System.out.println( "Le nombre de requete envoyée par chaque utilsateur est : " + NB_QUERY );
					System.out.println( "Pendant "+ NB_MINUTE  +" minute(s), on a " + nbCx + " connexion(s) BD simultanée(s)." );
					System.out.println( "En moyenne, chaque connexion met  " + (end  - begin) / nbCx + " ms" );
					System.out.println( "\nLe nombre total de requete envoyée a la BD est : " + (NB_QUERY * nbCx) );
					System.out.println( "En moyenne, chaque requete met " + (( NB_MINUTE * 60 * 1000)  / (NB_QUERY * nbCx))  + " ms.");

					
					break;
				}
				
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}






}
