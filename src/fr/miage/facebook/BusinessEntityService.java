/**
 * Classe dont devra hérité tous les services métiers.
 * Les services métiers regroupent les comportements liés à des cas d'utilisattion
 */
package fr.miage.facebook;

import connexion.PersistenceContext;

/**
 * @author Stephane
 * @version 0.1
 *
 */
public abstract class BusinessEntityService<A extends BusinessEntity> {
	private static PersistenceContext context;
	
	
	
	/**
	 * @return the context
	 */
	public static PersistenceContext getContext() {
		if (context == null)
			context = new PersistenceContext();
		return context;
	}
}
