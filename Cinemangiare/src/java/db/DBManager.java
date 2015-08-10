package db;
import Bean.Utente;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import utils.Hall;
//import utils.Seat;

/**
 * Classe che ingloba tutti i metodi necessari per interagire con il database locale
 * @author 
 */
public class DBManager implements Serializable {

    // transient -> Non viene serializzato, quando viene caricato DBmanager torna al valore di default
    private transient Connection con;
    
    /**
     * Carica il driver ed inizializza la connessione con il database
     * @param dburl L'URL del database locale
     * @throws SQLException 
     */
    public DBManager(String dburl) throws SQLException {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());

        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;

    }
    
    
    /**
     * Procedura globale per chiudere la connessione con il database
     */
    public static void shutdown() {
        try { 
            DriverManager.getConnection("jdbc:derby:;shutdown=true"); 
        } catch (SQLException ex) { 
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage()); 
        }
    }
    
    
    
    /**
     * Procedura di autenticazione dell'utente
     * @param userEmail Lo username dell'utente che sta tentando di loggarsi
     * @param password La password dell'utente che sta tentando di loggarsi
     * @return Un oggetto User contenente i dati dell'utente appena autenticato
     * @throws SQLException 
     */
    public Utente authenticate(String userEmail, String password) throws SQLException {
        
        
        PreparedStatement statement = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
        
        try {
            
            statement.setString(1, userEmail); // il primo "?" nella query è la mail dell'user
            statement.setString(2, password); // il secondo "?" è la psw dell'user
            
            ResultSet results = statement.executeQuery();

            try {
                if (results.next()) {
                    Utente user = new Utente();
                    user.setId(results.getInt("id_utente"));
                    user.setEmail(userEmail);
                    user.setCredito(results.getDouble("credito"));
                    user.setId_ruolo(results.getInt("id_ruolo"));
                    
                    //Se è autenticato ritorna l'oggetto User
                    return user;
                    
                }
                
                // Altrimenti ritorno null. Questo vuol dire che l'utente non esiste, o che i dati inseriti nel form non sono corretti
                else {
                    return null;
                }
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            statement.close();
        }
    }
}