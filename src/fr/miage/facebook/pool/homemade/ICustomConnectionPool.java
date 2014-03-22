package fr.miage.facebook.pool.homemade;

/**
 * Interface customisé d'un bassin de connexion
 * @author Mohamed
 *
 */
public interface ICustomConnectionPool extends IConnectionPool {
 
    /**
     * Permet de calculer le nombre de connexion disponible dans le bassin.
     * @return nombre de connexion disponible.
     */
    public int getNumberOfAvailableConnections();
 
    /**
     * Permet de calculer le nombre de connexion prise dans le bassin.
     * @return number of busy connections.
     */
    public int getNumberOfBusyConnections();
 
    /**
     * Ferme toutes les connexions.
     */
    public void closeAllConnections();
 
}