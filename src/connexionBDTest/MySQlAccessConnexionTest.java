/**
 * 
 */
package connexionBDTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import connexion.MySQLAccess;

/** 
 * @author Bouhlal
 *
 */
public class MySQlAccessConnexionTest {

	private static final int NB_MINUTE = 5; 

	/**
	 * Tester le nombre de connexion de la BD pendant 1 minute
	 * @throws SQLException
	 */
	//@Test(timeout = 60 * 1000)
	@Test(expected = OutOfMemoryError.class)
	public void connextionDriverTest() throws SQLException{


		Connection cx ;
		int i = 0;

		long begin = 0;
		long end;

		try {

			begin = System.currentTimeMillis();
			
			for ( i = 0; ; i++){
				
				cx = MySQLAccess.getConnect();
				
				java.sql.PreparedStatement ps = cx.prepareStatement("SELECT * FROM facebook.utilisateur");
				ResultSet res = ps.executeQuery();

				res.close();
				
				cx.close();
				
				
				end = System.currentTimeMillis();
				
				
				//Sortir de la boucle après 'NB_MINUTE' minute(s) de son lancement 
				if ( end - begin >= NB_MINUTE * 60 * 1000)	break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			System.out.println(NB_MINUTE + "  minute(s) : " + i + " connexions");
		}
	}

}
