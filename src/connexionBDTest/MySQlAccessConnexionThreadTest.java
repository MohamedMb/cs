/**
 * 
 */
package connexionBDTest;


import java.sql.SQLException;

import org.junit.Test;

import connexionBDThred.MySQLAccessThred;

/**
 * @author Bouhlal
 *
 */
public class MySQlAccessConnexionThreadTest {
	



	/**
	 * Tester le nombre maximum de connexion simultanée de la BD
	 * @throws SQLException
	 */
	//@Test(timeout = 60 * 1000)
	@Test(expected = OutOfMemoryError.class)
	public void connextionDriverTest() throws SQLException{

		
		MySQLAccessThred mySQLAccessThred = null;
		int i = 0;
		
		long begin = 0;
		long end;

		try {
			
			begin = System.currentTimeMillis();
			for ( i = 0; ; i++){
				mySQLAccessThred = new MySQLAccessThred();
				mySQLAccessThred.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			end = System.currentTimeMillis();
			System.out.println("Nombre maximum de connexion simultanée de la BD  est : " + i);
			System.out.println("Le temps mis pour " + i + " connexions simultanées est : " + (end - begin) + " ms");
		}
	}


}
