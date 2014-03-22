package fr.miage.facebook.pool.homemade;

import java.sql.Connection;
import java.sql.SQLException;
 
/**
 * Interface d'un bassin de connexion
 * @author Mohamed
 * 
 */
public interface IConnectionPool {
 
    /**
     * Retourne une connexion depuis le bassin de connexion
     * 
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
 
    /**
     * Libère une connexion vers le bassin de connexion
     * 
     * @param connection la connexion à renvoyée depuis le bassin
     * @throws java.sql.SQLException
     */
    void releaseConnection(Connection connection) throws SQLException;
}