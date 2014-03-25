/**
 * 
 */
package connexionBDTest;


import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;

import client.UtilisateurPoolConnexionThead;
import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;

/**
 * @author Bouhlal
 *
 */
public class CustomConnectionPoolImplThreadTest {

	private static final int NB_UTISATEUR = 1_000;
	private static final int NB_QUERY = 3;


	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/facebook";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "mysql";
	private static final int MAX_CONNECTIONS = 15;
	private static final int INITIAL_CONNECTIONS = 2;
	private static final boolean WAIT_IF_BUSY = true;






	private Logger logger = Logger.getLogger(getClass());


	@Test
	public void connexionBDTest(){

		long begin = 0;
		long end;
		int nbUtilisateur = 0;

		begin = System.currentTimeMillis();

		try{
			for ( nbUtilisateur = 1; nbUtilisateur <= NB_UTISATEUR ; nbUtilisateur++){

				CustomConnectionPoolImpl pool = new CustomConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
						INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
				UtilisateurPoolConnexionThead uPool = new UtilisateurPoolConnexionThead(NB_QUERY, pool);
				uPool.start();
				
				System.out.print(".");
			}
		
		
		}catch(SQLException e){
			e.printStackTrace();

		}finally{

			end = System.currentTimeMillis();

			System.out.println();
			System.out.println( "* Le nombre d'utilisateur est : " + NB_UTISATEUR );
			System.out.println( "* Le nombre de requete envoyée par chaque utilsateur est : " + NB_QUERY );
			System.out.println( "* Le Temps mit pour envoyer les " + (NB_QUERY * NB_UTISATEUR) + " est : " + (end - begin) +" ms" );
			System.out.println( "* En moyenne, chaque requete met " +  (end - begin) / (NB_QUERY * NB_UTISATEUR) + " ms");

		}

	}



}
