package db;

import Bean.Film;
import Bean.Posto;
import Bean.Prenotazione;
import Bean.Price;
import Bean.Spettacolo;
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
import static java.lang.Math.random;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe che ingloba tutti i metodi necessari per interagire con il database
 * locale
 *
 * @author
 */
public class DBManager implements Serializable {

    // transient -> Non viene serializzato, quando viene caricato DBmanager torna al valore di default
    private transient Connection con;
    
    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

    /**
     * Carica il driver ed inizializza la connessione con il database
     *
     * @param dburl L'URL del database locale
     * @throws SQLException
     */
    public DBManager(String dburl) throws SQLException {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());

        } catch (Exception e) {
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
     *
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
                } // Altrimenti ritorno null. Questo vuol dire che l'utente non esiste, o che i dati inseriti nel form non sono corretti
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
        PreparedStatement statement = con.prepareStatement("INSERT INTO utente(email, password, id_ruolo) VALUES (?, ?, ?)");

        try {
            statement.setString(1, userEmail); // il primo "?" nella query è la mail dell'user
            statement.setString(2, password); // il secondo "?" è la psw dell'user
            statement.setInt(3,2); // setto utente come utente

            int i = statement.executeUpdate();

            if (i > 0) {
                System.out.println("success!!!");
            }

        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            statement.close();
        }
    }
    
    public List<Prenotazione> getListPrenotazione (String email) throws SQLException {
        List<Prenotazione> listPrenotazione = new ArrayList();
        
        PreparedStatement stm = null;
        //PreparedStatement statement = con.prepareStatement("SELECT titolo FROM film");

        try {

            //ResultSet results = statement.executeQuery();
            String sqlInsert = "SELECT * FROM prenotazione NATURAL JOIN spettacolo NATURAL JOIN film WHERE email = ?";
            
            stm = con.prepareStatement(sqlInsert);
            stm.setString(1, email);
            ResultSet results = stm.executeQuery();

            try {

                while (results.next()) {
                    Prenotazione prenotazione = new Prenotazione();
                    prenotazione.setId_spettacolo(results.getInt("id_spettacolo"));
                    prenotazione.setId_prezzo(results.getInt("id_prezzo"));
                    prenotazione.setId_posto(results.getInt("id_posto"));
                    prenotazione.setData_ora_prenotazione(results.getTimestamp("data_ora_operazione"));
                    prenotazione.setTitolo(results.getString("titolo"));
                    listPrenotazione.add(prenotazione);
                }

            } finally {
                // Chiusura del ResultSet, da fare SEMPRE in un blocco finally
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            stm.close();
        }

        return listPrenotazione;
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

    public Film getFilm(String titolo) throws SQLException {

        PreparedStatement stm = null;

        try {

            String sqlInsert = "SELECT * FROM film WHERE titolo=?";
            stm = con.prepareStatement(sqlInsert);

            stm.setString(1, titolo);

            ResultSet results = stm.executeQuery();
            
            try {
                if (results.next()) {
                    Film film = new Film();
                    film.setId(results.getInt("id_film"));
                    film.setTitolo(results.getString("titolo"));
                    film.setTrama(results.getString("trama"));
                    film.setId_genere(results.getInt("id_genere"));
                    film.setDurata(results.getInt("durata"));
                    film.setUrl_locandina(results.getString("uri_locandina"));
                    film.setUrl_trailer(results.getString("url_trailer"));
                    return film;
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        return null;
    }
    
    public List<Spettacolo> getSpettacolo(int id_film) throws SQLException {

        List<Spettacolo> listSpettacolo = new ArrayList();
        
        PreparedStatement stm = null;

        try {

            String sqlInsert = "SELECT * FROM spettacolo WHERE id_film=?";
            stm = con.prepareStatement(sqlInsert);

            stm.setInt(1, id_film);

            ResultSet results = stm.executeQuery();
            
            try {
                while (results.next()) {
                    Spettacolo spettacolo = new Spettacolo();
                    spettacolo.setId_spettacolo(results.getInt("id_spettacolo"));
                    spettacolo.setId_film(results.getInt("id_film"));
                    spettacolo.setData_ora(results.getTimestamp("data_ora"));
                    spettacolo.setId_sala(results.getInt("id_sala"));
                    listSpettacolo.add(spettacolo);
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
 

        return listSpettacolo;
    }
    
    public List<Posto> getListPosti(int id_spettacolo, int id_sala) throws SQLException {
        List<Posto> listPosti = new ArrayList();
      
        PreparedStatement stmTotali = null;
        PreparedStatement stmOccupati = null;

        try {

            String sqlQueryPostiTotali = "SELECT * FROM posto NATURAL JOIN sala NATURAL JOIN spettacolo WHERE id_sala=? AND id_spettacolo=? AND id_posto NOT IN (SELECT id_posto FROM posto NATURAL JOIN sala NATURAL JOIN prenotazione WHERE id_sala=? AND id_spettacolo=? ORDER BY riga, colonna ASC) ORDER BY riga, colonna ASC";
            String sqlQueryPostiOccupati = "SELECT * FROM posto NATURAL JOIN sala NATURAL JOIN prenotazione WHERE id_sala=? AND id_spettacolo=? ORDER BY riga, colonna ASC";
            stmTotali = con.prepareStatement(sqlQueryPostiTotali);
            stmOccupati = con.prepareStatement(sqlQueryPostiOccupati);

            stmTotali.setInt(1, id_sala);
            stmTotali.setInt(2, id_spettacolo);
            stmTotali.setInt(3, id_sala);       
            stmTotali.setInt(4, id_spettacolo);
            stmOccupati.setInt(1, id_sala);
            stmOccupati.setInt(2, id_spettacolo);


            ResultSet resultsTotali = stmTotali.executeQuery();
            ResultSet resultsOccupati = stmOccupati.executeQuery();
            
            try {
                while (resultsTotali.next()) {
                    Posto posto = new Posto();
                    posto.setId(resultsTotali.getInt("id_posto"));
                    posto.setId_sala(resultsTotali.getInt("id_sala"));
                    posto.setRiga(resultsTotali.getInt("riga"));
                    posto.setColonna(resultsTotali.getInt("colonna"));
                    posto.setEsiste(resultsTotali.getBoolean("esiste"));
                    posto.setPrenotato(false);
                    listPosti.add(posto);
                }
                
                while (resultsOccupati.next()) {
                    Posto posto = new Posto();
                    posto.setId(resultsOccupati.getInt("id_posto"));
                    posto.setId_sala(resultsOccupati.getInt("id_sala"));
                    posto.setRiga(resultsOccupati.getInt("riga"));
                    posto.setColonna(resultsOccupati.getInt("colonna"));
                    posto.setEsiste(resultsOccupati.getBoolean("esiste"));
                    posto.setPrenotato(true);
                    listPosti.add(posto);
                }
                
            } finally {
                // Chiusura del ResultSet
                resultsTotali.close();
                resultsOccupati.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stmTotali.close();
            stmOccupati.close();
        }
 
        return listPosti;
    }

    /**
     * Prepara la classe che contiene la disposizione dei posti liberi e
     * occupati di una determinata sala
     *
     * @param id_spettacolo Il numero identificativo dello spettacolo
     * @param id_sala Il numero identificativo della sala
     * @return Un oggetto Hall, contenente tutti i dati relativi ai posti di una
     * sala
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
                while (rs.next()) {
                    int riga = rs.getInt("riga");
                    int colonna = rs.getInt("colonna");
                    hall.addSeat(riga, colonna, rs.getBoolean("esiste"));
                }

                while (rsOccupati.next()) {
                    int riga = rsOccupati.getInt("riga");
                    int colonna = rsOccupati.getInt("colonna");
                    hall.modifySeatBookedStatus(riga, colonna, true);
                }

            } finally {

                rs.close();
                rsOccupati.close();
            }
        } finally {

            stmOccupati.close();
            stmTotali.close();
            
        }
        return hall;
    }

    /**
     * Ritorna una lista di interi che rappresentano gli identificatori di tutte
     * le sale presenti nel database
     *
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

                while (rs.next()) {
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

                while (rs.next()) {
                    Integer extractSeat = rs.getInt("id_posto");
                    // Controlla per ogni posto se è già stato prenotato
                    for (int i = 0; i < posti.size(); i++) {
                        // Se il posto è già stato prenotato allora ritorna false
                        if (extractSeat.equals(posti.get(i))) {
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
                if (rs.next()) {
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
     *
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
            for (int i = 0; i < idTickets.size(); i++) {
                stm.setInt(1, idTickets.get(i));
                if (stm.executeUpdate() == 0) {
                    result = false;
                }
            }

            // Commit manuale, in quanto quello automatico e' stato disattivato
            if (result) {
                con.commit();
            }

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
    
    
    
    
    
     public String getPassword(String utenteEmail) throws SQLException {
        
        String utentePassword = null;
        PreparedStatement stm = null;
        
        try {
            
            // SELECT SQL
            String sqlQuery = "SELECT password FROM utente WHERE email=?";
            stm = con.prepareStatement(sqlQuery);
            
            // Compilazione dei valori mancanti della query
            stm.setString(1, utenteEmail);
            
            ResultSet rs = stm.executeQuery();
            
            try {
                if(rs.next()) {
                    
                    utentePassword = rs.getString("password");
                    return utentePassword;
                    
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
     
     public void setShow(int x) throws SQLException{
        //data odierna
        Calendar now = Calendar.getInstance();
        long time= now.getTimeInMillis();
        
        //QUERY
        PreparedStatement s = null;
        String sqlQ = "DELETE FROM prenotazione";
         try{
            s = con.prepareStatement(sqlQ);
            int j = s.executeUpdate();

                    if (j > 0) {
                        System.out.println("success!!!");
                    }
        } finally{
              s.close();
        }
        
        PreparedStatement stm = null;
        String slqQuery = "DELETE FROM SPETTACOLO";
        
        try{
            stm = con.prepareStatement(slqQuery);
            int j = stm.executeUpdate();

                    if (j > 0) {
                        System.out.println("success!!!");
                    }
        } finally{
              stm.close();
        }
        //QUERY update spettacolo
        int id=1;
        int sala=1;
        for(int k=1; k<11; k++){
            //dta primo spettacolo
            Date new_date=new Date(time + (long)(random()*x*ONE_MINUTE_IN_MILLIS));
            long new_time=new_date.getTime();
            Timestamp t=new Timestamp(new_time);
            
            for(int j=0; j<20; j++){
                PreparedStatement statement = con.prepareStatement("INSERT INTO spettacolo(id_spettacolo,id_film,data_ora,id_sala) VALUES (?, ?, ?, ?)");

                try {
                    statement.setInt(1,id );
                    statement.setInt(2,k);
                    statement.setTimestamp(3, t);
                    statement.setInt(4,sala);
                    int i = statement.executeUpdate();

                    if (i > 0) {
                        System.out.println("success!!!");
                    }
                    
                    Date tmp=new Date(new_time + (long)(x*ONE_MINUTE_IN_MILLIS));
                    new_time=tmp.getTime();
                    t=new Timestamp(new_time);
                    id++;
                    
                    

                } finally {
                // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
                statement.close();
                }
            }
            sala++;
            if(sala>5){
                sala=1;
            }
        }
        
      
        
     }
     
      public List<Spettacolo> getVendorSeat() throws SQLException{
          
          //QUERY
       List<Spettacolo> listSpettacolo = new ArrayList();
        
        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT COUNT (id_posto) AS posti, id_film, id_sala, id_spettacolo, SUM (prezzo) AS prezzo, titolo, data_ora FROM prenotazione NATURAL JOIN prezzo NATURAL JOIN spettacolo NATURAL JOIN film GROUP BY id_spettacolo, id_film, id_sala, titolo, data_ora";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();
            
            try {
                while (results.next()) {
                    Spettacolo spettacolo = new Spettacolo();
                    spettacolo.setId_spettacolo(results.getInt("id_spettacolo"));
                    spettacolo.setId_film(results.getInt("id_film"));
                    spettacolo.setPrezzo(results.getInt("prezzo"));
                    spettacolo.setPosti(results.getInt("posti"));
                    spettacolo.setId_sala(results.getInt("id_sala"));
                    spettacolo.setTitolo(results.getString("titolo"));
                    spettacolo.setData_ora(results.getTimestamp("data_ora"));
                    
                    listSpettacolo.add(spettacolo);
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        return listSpettacolo;
          
      }
      
      public List<Film> getIncassi() throws SQLException{
         
        //QUERY
       List<Film> listFilm = new ArrayList();
        
        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT id_film, titolo, SUM(prezzo) AS incassi FROM prenotazione NATURAL JOIN prezzo NATURAL JOIN spettacolo NATURAL JOIN film GROUP BY id_film, titolo";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();
            
            try {
                while (results.next()) {
                    Film film = new Film();
                    film.setId(results.getInt("id_film"));
                    film.setTitolo(results.getString("titolo"));
                    film.setIncassi(results.getInt("incassi"));
                    
                    listFilm.add(film);
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        return listFilm;
          
          
      }
      
      public List<Utente> getTopClient() throws SQLException{

      
      //QUERY
       List<Utente> listUtente = new ArrayList();
        
        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT email, SUM(prezzo) AS paga FROM utente NATURAL JOIN prenotazione NATURAL JOIN prezzo GROUP BY email ORDER BY paga";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();
            
            try {
                while (results.next()) {
                    Utente utente = new Utente();
                    utente.setEmail(results.getString("email"));
                    utente.setPaga(results.getInt("paga"));
                    
                    listUtente.add(utente);
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        return listUtente;
      }
     
      public List<Price> getPrice() throws SQLException{

      
      //QUERY
       List<Price> listPrice = new ArrayList();
        
        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT id_prezzo, tipo, prezzo FROM prezzo";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();
            
            try {
                while (results.next()) {
                    Price price = new Price();
                    price.setId(results.getInt("id_prezzo"));
                    price.setTipo(results.getString("tipo"));
                    price.setPrezzo(results.getInt("prezzo"));
                    
                    listPrice.add(price);
                }
                
            } finally {
                // Chiusura del ResultSet
                results.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }
        return listPrice;
      }
    

}
