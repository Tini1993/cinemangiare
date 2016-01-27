package db;
import Bean.Film;
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
import java.lang.Exception;


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
        // usare SEMPRE i PreparedStatement, anche per query banali. 
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!! ***
        
        PreparedStatement statement = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
        
        try {
            
            statement.setString(1, userEmail); // il primo "?" nella query è la mail dell'user
            statement.setString(2, password); // il secondo "?" è la psw dell'user
            
            ResultSet results = statement.executeQuery();

            try {
                if (results.next()) {
                    Utente user = new Utente();                   
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
                // Chiusura del ResultSet, da fare SEMPRE in un blocco finally
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            statement.close();
        }
    }
    
    public void register(String userEmail, String password) throws SQLException {
        // usare SEMPRE i PreparedStatement, anche per query banali. 
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!! ***
        
        // l'id_utente bisogna mettere che si autoinvremente
        PreparedStatement statement = con.prepareStatement("INSERT INTO utente(email, password) VALUES (?, ?)");
        
        try {
            statement.setString(1, userEmail); // il primo "?" nella query è la mail dell'user
            statement.setString(2, password); // il secondo "?" è la psw dell'user
            
            int i = statement.executeUpdate();

            if(i>0)
            {
                System.out.println("success!!!");
            }
          
        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            statement.close();
        }
    }
    
    public List<Film> getListFilm() throws SQLException {
        
        List<Film> listFilm = new ArrayList();
        
        PreparedStatement stm = null;
        //PreparedStatement statement = con.prepareStatement("SELECT titolo FROM film");
        
        try {
            
            //ResultSet results = statement.executeQuery();
            String sqlInsert = "SELECT id_film, titolo,id_genere,url_trailer,durata,uri_locandina FROM film";
            stm = con.prepareStatement(sqlInsert);
            ResultSet results = stm.executeQuery();

            try {
                
                while (results.next()) {
                    Film film = new Film();
                    film.setId(results.getInt("id_film"));
                    film.setTitolo(results.getString("titolo"));
                    film.setId_genere(results.getInt("id_genere"));
                    film.setDurata(results.getInt("durata"));
                    film.setUrl_locandina(results.getString("uri_locandina"));
                    film.setUrl_trailer(results.getString("url_trailer"));
                    listFilm.add(film);
                }              
                
            } finally {
                // Chiusura del ResultSet, da fare SEMPRE in un blocco finally
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            stm.close();
        }    
        
        return listFilm;
    }
    
    public Film getFilm() throws SQLException {
        Film films = new Film();
        
        PreparedStatement stm = null;
        //PreparedStatement statement = con.prepareStatement("SELECT titolo FROM film");
        
        try {
            
            //ResultSet results = statement.executeQuery();
            String sqlInsert = "SELECT titolo FROM film WHERE id_film=1";
            stm = con.prepareStatement(sqlInsert);
            ResultSet results = stm.executeQuery();
                 try {    
                    films.setTitolo(results.getString("titolo"));
                               
                
            } finally {
                // Chiusura del ResultSet, da fare SEMPRE in un blocco finally
                results.close();
            }}
        finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            stm.close();
        }    
        
        return films;
    }
    
    public Film getFilm(int id_film) throws SQLException {
        
        
        PreparedStatement stm = null;
        
        try {
            
            
            String sqlInsert = "SELECT * FROM film NATURAL JOIN genere WHERE ID_FILM=?";
            stm = con.prepareStatement(sqlInsert);
            
            stm.setInt(1, id_film);
            
            ResultSet rs = stm.executeQuery();
            
            try {
                if(rs.next()) {
                    
                    Film movie = new Film();
                    
                    movie.setId(rs.getInt("id_film"));
                    movie.setTitolo(rs.getString("titolo"));
                    movie.setId_genere(rs.getInt("id_genere"));
                  
                    
                    return movie;
                }
                
            } finally {
                // Chiusura del ResultSet
                rs.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        
        return null;
        
    }
    
    /**
      * Prepara la classe che contiene la disposizione dei posti liberi e occupati di una determinata sala
      * @param id_spettacolo Il numero identificativo dello spettacolo
      * @param id_sala Il numero identificativo della sala
      * @return Un oggetto Hall, contenente tutti i dati relativi ai posti di una sala
      * @throws SQLException 
      */
     public Hall getSeatMatrix(int id_spettacolo, int id_sala) throws SQLException {
         
        
        String sqlQueryPostiTotali = "SELECT * FROM posto NATURAL JOIN sala WHERE id_sala=? ORDER BY riga, colonna ASC";
        String sqlQueryPostiOccupati = "SELECT * FROM spettacolo NATURAL JOIN posto NATURAL JOIN prenotazione WHERE id_spettacolo=?";
        PreparedStatement stmTotali = null;
        PreparedStatement stmOccupati = null;
        Hall hall = new Hall();
        
        try {
            
            stmTotali = con.prepareStatement(sqlQueryPostiTotali);
            stmOccupati = con.prepareStatement(sqlQueryPostiOccupati);
            stmTotali.setInt(1, id_sala);
            stmOccupati.setInt(1, id_spettacolo);
            
            ResultSet rs = stmTotali.executeQuery();
            ResultSet rsOccupati = stmOccupati.executeQuery();
            try {
                while(rs.next()) {
                    int riga = rs.getInt("riga");
                    int colonna = rs.getInt("colonna");
                    hall.addSeat(riga, colonna, rs.getBoolean("esiste"));
                }
                
                while(rsOccupati.next()) {
                    int riga = rsOccupati.getInt("riga");
                    int colonna = rsOccupati.getInt("colonna");
                    hall.modifySeatBookedStatus(riga, colonna, true);
                }
                
            } finally {

                rs.close();
                rsOccupati.close();
            }
        } finally {

            stmTotali.close();
            stmOccupati.close();
        }
        
        return hall;
     }
     
     /**
      * Ritorna una lista di interi che rappresentano gli identificatori di tutte le sale presenti nel database
      * @return Una lista di interi contenente gli identificatori delle sale
      * @throws SQLException 
      */
     public List<Integer> getHallID() throws SQLException {
        
        List<Integer> hallID = new ArrayList();
        PreparedStatement stm = null;
        
        try {
            
            String sqlQuery = "SELECT id_sala FROM sala";
            stm = con.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();
            
            try {
                
                while(rs.next()) {
                    Integer extract = rs.getInt("id_sala");
                    hallID.add(extract);
                }
   
            } finally {

                rs.close();
            }
        } finally {

            stm.close();
        }
        
        return hallID;
    }
     
     public boolean getSeatAvailability(ArrayList<Integer> posti, String idSpettacolo) throws SQLException {
        
        String sqlQueryPostiOccupati = "SELECT * FROM spettacolo NATURAL JOIN posto NATURAL JOIN prenotazione WHERE id_spettacolo=?";
        PreparedStatement stm = null;
        
        try {
            stm = con.prepareStatement(sqlQueryPostiOccupati);
            
            stm.setString(1, idSpettacolo);
            ResultSet rs = stm.executeQuery();
            
            try {
                
                while(rs.next()) {
                    Integer extractSeat = rs.getInt("id_posto");
                    // Controlla per ogni posto se è già stato prenotato
                    for(int i=0;i<posti.size();i++) {
                        // Se il posto è già stato prenotato allora ritorna false
                        if(extractSeat.equals(posti.get(i))) {
                            return false;
                        }
                    }             
                }
        
            } finally {
             
                rs.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        
        // Se non sono stati prenotati i posti scelti allora ritorna true
        return true;
    }
    
     public Integer getTrueSeatID(int riga, int colonna, int hallID) throws SQLException {
        PreparedStatement stm = null;
        
        try {
            String sqlInsert = "SELECT * FROM posto WHERE riga=? and colonna=? and id_sala=?";
            stm = con.prepareStatement(sqlInsert);
            
            stm.setInt(1, riga);
            stm.setInt(2, colonna);
            stm.setInt(3, hallID);
            
            ResultSet rs = stm.executeQuery();
            
            try {
                if(rs.next()) {
                    return rs.getInt("id_posto");
                }
                
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return null;
    }
    
    /**
     * Setta i posti in inesistenti
     * @param idTickets
     * @param hallID
     * @return risultato operazione
     * @throws SQLException 
     */
    public boolean deleteSeats(ArrayList<Integer> idTickets, int hallID) throws SQLException {
        
        PreparedStatement stm = null;
        boolean result = true;
        try {
            // Disabilito il commit automatico
            con.setAutoCommit(false);
            String sqlDelete = "UPDATE posto SET esiste=false WHERE id_posto=?";
            stm = con.prepareStatement(sqlDelete);
            for(int i=0; i<idTickets.size(); i++) {
                stm.setInt(1, idTickets.get(i));
                if(stm.executeUpdate()==0) {
                    result = false;
                }
            }
            

            // Commit manuale, in quanto quello automatico e' stato disattivato
            if(result) con.commit();


        } catch (SQLException se) {
            // Rollback nel caso generi un'eccezione
            if (!con.getAutoCommit()) {
                con.rollback();
            }
            // Dopo il rollback, rilancio l'eccezione in alto per farla gestire al layer di applicazione
            throw se;
        } finally {
            
            // Chiudo lo statement
            if (stm != null) {
                stm.close();
            }
            // Riabilito il commit automatico
            if (!con.getAutoCommit()) {
                con.setAutoCommit(true);
            }
        }
        
        return result;
    }  

}