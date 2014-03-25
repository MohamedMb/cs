/**
 * 
 */
package client; 

import java.io.IOException;
import java.sql.Connection;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.sun.istack.internal.logging.Logger;

import fr.miage.facebook.pool.homemade.CustomConnectionPoolImpl;

/**
 * Classe permettant de jouer le rôle d'un navigateur afin de
 * tester le nombre maximal de connexion simultanée dont peut supporter le serveur
 * 
 * @author Bouhlal
 *
 */
public class Client{
	
	public static Logger logger = Logger.getLogger(Client.class);

	private HttpClient client;
	private PostMethod method;
	private Header header;

	private String password;
	private String mail;

	private Connection cx;


	/**
	 * @param uri 
	 */
	private Client(String uri) {
		//Creer une instance HttpClient qui va jouer le role d'un navigateur
		this.client = new HttpClient();

		//utilisation du user-agent de firefox
		this.header = new Header("User-Agent", "Mozilla/4.42.0.0");

		this.method = new PostMethod(uri);
	}

	/**
	 * 
	 */
	public Client(String mail, String password) {
		this("http://localhost:8080/cs/connexion");
	
		this.mail = mail;
		
		this.password = password;
	}
	
	public Client() {
		this("http://localhost:8080/cs/connexion");
	}
	
//	/**
//	 * Demande la page sans l'obligation de se connecter
//	 */
//	public void request(){
//		this.method.setRequestHeader(header);
//	}
//	
	
	/***
	 * Connecter dynamiquement à la page index
	 */
	public void connect(){

		this.method.setRequestHeader(header);
		this.method.addParameter("mail", this.mail);
		this.method.addParameter("password" , this.password);

		//Executer la méthode
		int statusCode;
		try {
			statusCode = this.client.executeMethod(this.method);

			if (statusCode != HttpStatus.SC_OK)
				System.err.println("Method failed: " + this.method.getStatusLine());


			//s'il ya une redirection on l'utilise 
			while (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				String redirectLocation = "";
				
				//on récupère l'url de la redirection
				Header locationHeader = this.method.getResponseHeader("location");
				if (locationHeader != null) {
					redirectLocation = locationHeader.getValue();
				}

				logger.info(redirectLocation);

				this.method = new PostMethod(redirectLocation);
				this.method.setRequestHeader(this.header);

				statusCode = this.client.executeMethod(this.method);
			}
			

			
			
//			//Afficher le code de la page
//			System.out.println(new String(this.method.getResponseBody()));
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPassword() {
		return password;
	}

	public String getMail() {
		return mail;
	}


}
