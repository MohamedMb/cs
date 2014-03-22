package fr.miage.facebook.pool.homemade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
//import org.apache.log4j.Logger;
 
/**
 * Implémentation du bassin de connexion.
 * Classe à utiliser pour les bassins de connexions.
 * 
 * @author Mohamed
 * 
 */
public class CustomConnectionPoolImpl implements Runnable, ICustomConnectionPool {
 
    //private Logger log = Logger.getLogger(getClass());
     
    private String driver, url, username, password;
    private int maxConnections;
    private boolean waitIfBusy;
    private List<Connection> availableConnections, busyConnections;
    private boolean connectionPending = false;
 
    /**
     * 
     * @param driver du type com.mysql.jdbc.Driver
     * @param url connexion jdbc au sgbd (jdbc:mysql://127.0.0.1:3306/facebook)
     * @param username nom de l'utilisateur de la bdd (root)
     * @param password mot de passe de l'utilisateur de la bdd ("")
     * @param initialConnections nombre initial de connexion
     * @param maxConnections maximum de connexion autorisées
     * @param waitIfBusy prend une pause si au milieu d'une opération
     * @throws SQLException
     */
    public CustomConnectionPoolImpl(String driver, String url, String username,
            String password, int initialConnections, int maxConnections,
            boolean waitIfBusy) throws SQLException {
 
        if (driver == null) {
            throw new IllegalArgumentException("Le driver donné est de valeur null.");
        }
        if (url == null) {
            throw new IllegalArgumentException("L'URL donnée est de valeur null.");
        }
        if (username == null || password == null) {
            throw new IllegalArgumentException(
                    "Le username ou le password sont de valeur null.");
        }
        if (maxConnections <= 0) {
            throw new IllegalArgumentException(
                    "Le nombre maximum de connexion doit être supérieur à 0.");
        }
 
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
        this.waitIfBusy = waitIfBusy;
        if (initialConnections > maxConnections) {
            initialConnections = maxConnections;
        }
        
        //synchronizedList permet de locker la liste en cas de travail multi-thread
        //afin de ne pas la corrompre.
        availableConnections = Collections
                .synchronizedList(new ArrayList<Connection>(initialConnections));
        busyConnections = Collections
                .synchronizedList(new ArrayList<Connection>());
        for (int i = 0; i < initialConnections; i++) {
            availableConnections.add(makeNewConnection()); //objet connexion ajouté à la liste
        }
 
        //log.debug("Nombre de connexions disponibles : " + availableConnections.size());
    }
 
    
    public synchronized Connection getConnection() throws SQLException {
        if (!availableConnections.isEmpty()) { //si connexion disponible
            int lastIndex = availableConnections.size() - 1;
            Connection existingConnection = (Connection) availableConnections
                    .get(lastIndex);
            availableConnections.remove(lastIndex);
 
            //log.debug("Nombre de connexions disponibles : " + availableConnections.size());
 
            // Si la connexion sur la liste available est fermée (par un time out par exemple),
            // alors on l'efface de la liste available et on répète le processus
            // d'obtention d'une connexion. On réveille alors les threads qui attendaient
            // une connexion car la limite de maxConnexion était atteinte. 
            if (existingConnection.isClosed()) {
            	// Notify tout les thread qu'une connexion vient d'être libéré
                notifyAll();
                return (getConnection());
            } else {
                busyConnections.add(existingConnection); 
                return existingConnection;
            }
        } else {
            // Trois cas possible :
        	// 1) nous n'avons pas atteind la limit de maxConnections. Alors on en établit une en
        	// arrière plan s'il n'y en a pas une déjà en attente, alors on attend la
        	// prochaine connexion disponible (que ce soit nouvellement disponible ou non).
        	// 2) nous avons atteint la limit de maxConnexion et la valeur de waitIfBusy
        	// est à false.
        	// Renvoie une exception SQLException dans ce cas.
        	// 3) nous avons atteint la limite de maxConnexion et la valeur de waitIfBusy est
        	// à true.
        	// Alors on fait la même chose comme dans la seconde partie de l'étape 1 : on attend
        	// la prochaine connexion disponible.
            if (((getNumberOfAvailableConnections() + getNumberOfBusyConnections())
            		< maxConnections) && !connectionPending) {
                makeBackgroundConnection();
            } else if (!waitIfBusy) {
                throw new SQLException("Limite de connexions atteintes");
            }
 
            // Attend qu'une nouvelle connexion soit établie (si la méthode
            // makeBackgroundConnection a été appelée) ou qu'une connexion
            // existante soit libérée.
            try {
                wait();
            } catch (InterruptedException ie) {
            }
 
            //log.debug("Nombre de connexions disponibles : " + availableConnections.size());
 
            // Une connexion a été libérée, alors on réessaye.
            return getConnection();
        }
    }
 
    /**
     * On ne peut pas créer une nouvelle connexion au premier plan quand aucune n'est
     * disponible, car cela pourrait prendre plusieurs secondes (sur un réseau lent par exemple).
     * A la place, on démarre un nouveau thread qui établiera cette nouvelle connexion,
     * qui attend. On sera notifié (et réveillé) lorsque la nouvelle connexion sera établie
     * ou si un thread aura finit avec une connexion existante.
     */
    private void makeBackgroundConnection() {
        connectionPending = true;
        try {
            Thread connectThread = new Thread(this);
            connectThread.start();
        } catch (OutOfMemoryError oome) {
            // Abondon de la nouvelle connexion
        }
    }
    
    /**
     * Méthode lancée à l'instantiation de cette classe.
     */
    public void run() {
        try {
            Connection connection = makeNewConnection();
            synchronized (this) {
                availableConnections.add(connection);
                connectionPending = false;
                notifyAll();
            }
        } catch (Exception e) { // SQLException ou OutOfMemory
            // Abondonne la nouvelle connexion et attend qu'une existante se libère.
        }
    }
 
    /**
     * Créée de nouvelles connexions. Elle est appelée au premier plan lors de
     * l'inialisation de ConnectionPoolImpl et à l'arrière plan lorsqu'elle est tourne.
     * @return Connection
     * @throws SQLException
     */
    private Connection makeNewConnection() throws SQLException {
        try {
        	// Charge le driver de la bdd si ce n'est déjà fait
            Class.forName(driver);
            // Etablie une connexion réseau à la bdd
            Connection connection = DriverManager.getConnection(url, username,
                    password);
            return (connection);
        } catch (ClassNotFoundException cnfe) {
            throw new SQLException("Impossible de trouver la classe du driver : " + driver);
        }
    }
 
    /**
     * Libère une connexion
     */
    public synchronized void releaseConnection(Connection connection)
            throws SQLException {
        busyConnections.remove(connection);
        availableConnections.add(connection);
        // Réveil les threads en attente d'une connexion
        notifyAll();
    }
 
    /**
     * Ferme toute les connexions. On a besoin d'être sûr qu'aucune connexion
     * ne soit en train d'être utilisée avant son appel.
     */
    public synchronized void closeAllConnections() {
        closeConnections(availableConnections);
        availableConnections = Collections
                .synchronizedList(new ArrayList<Connection>());
        closeConnections(busyConnections);
        busyConnections = Collections
                .synchronizedList(new ArrayList<Connection>());
    }
 
    /**
     * Ferme la liste des connexions (chaque élement est clos un à un)
     * passées en paramètres
     * 
     * @param connections Liste de Connection
     */
    private void closeConnections(List<Connection> connections) {
        try {
            for (Connection connection : connections) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException sqle) {
        }
    }
 
    /**
     * Retourne le nombre de connexions disponibles.
     */
    public synchronized int getNumberOfAvailableConnections() {
        return availableConnections.size();
    }
 
    /**
     * Retourne le nombre de connexions utilisées.
     */
    public synchronized int getNumberOfBusyConnections() {
        return busyConnections.size();
    }
 
    /**
     * Récapitulatif du bassin de connexion
     */
    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Classe : ").append(this.getClass().getName()).append("\n");
        result.append(" disponibles : ").append(availableConnections.size()).append("\n");
        result.append(" occupées: ").append(busyConnections.size()).append("\n");
        result.append(" maximum: ").append(maxConnections).append("\n");
        return result.toString();
    }
}