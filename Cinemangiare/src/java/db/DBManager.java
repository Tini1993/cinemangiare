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
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.RequestDispatcher;

/**
 * Classe che ingloba tutti i metodi necessari per interagire con il database
 * locale
 *
 * @author
 */
public class DBManager implements Serializable {

    // transient -> Non viene serializzato, quando viene caricato DBmanager torna al valore di default
    private transient Connection con;

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

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
            statement.setInt(3, 2); // setto utente come utente

            int i = statement.executeUpdate();

            if (i > 0) {
                System.out.println("success!!!");
            }

        } finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            statement.close();
        }
    }

    public List<Prenotazione> getListPrenotazione(String email) throws SQLException {
        List<Prenotazione> listPrenotazione = new ArrayList();

        PreparedStatement stm = null;
        //PreparedStatement statement = con.prepareStatement("SELECT titolo FROM film");

        try {

            //ResultSet results = statement.executeQuery();
            String sqlInsert = "SELECT * FROM prenotazione NATURAL JOIN spettacolo NATURAL JOIN film NATURAL JOIN prezzo WHERE email = ?";

            stm = con.prepareStatement(sqlInsert);
            stm.setString(1, email);
            ResultSet results = stm.executeQuery();

            try {

                while (results.next()) {
                    Prenotazione prenotazione = new Prenotazione();
                    prenotazione.setId_prenotazione(results.getInt("id_prenotazione"));
                    prenotazione.setId_spettacolo(results.getInt("id_spettacolo"));
                    prenotazione.setId_prezzo(results.getDouble("prezzo"));
                    prenotazione.setId_posto(results.getInt("id_posto"));
                    prenotazione.setData_ora_prenotazione(results.getTimestamp("data_ora_operazione"));
                    prenotazione.setTitolo(results.getString("titolo"));
                    prenotazione.setData_ora_spettacolo(results.getTimestamp("data_ora"));
                    prenotazione.setSala(results.getInt("id_sala"));
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

        java.util.Date date = new java.util.Date();
        Timestamp data_ora_corrente = new Timestamp(date.getTime());

        PreparedStatement stm = null;

        try {

            String sqlInsert = "SELECT * FROM spettacolo WHERE id_film=? AND data_ora > ?";
            stm = con.prepareStatement(sqlInsert);

            stm.setInt(1, id_film);
            stm.setTimestamp(2, data_ora_corrente);

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

        PreparedStatement stmLiberi = null;
        PreparedStatement stmOccupati = null;

        try {

            String sqlQueryPostiLiberi = "SELECT * FROM posto NATURAL JOIN sala NATURAL JOIN spettacolo WHERE id_sala=? AND id_spettacolo=? AND id_posto NOT IN (SELECT id_posto FROM posto NATURAL JOIN sala NATURAL JOIN prenotazione WHERE id_sala=? AND id_spettacolo=? ORDER BY riga, colonna ASC) ORDER BY riga, colonna ASC";
            String sqlQueryPostiOccupati = "SELECT * FROM posto NATURAL JOIN sala NATURAL JOIN prenotazione WHERE id_sala=? AND id_spettacolo=? ORDER BY riga, colonna ASC";
            stmLiberi = con.prepareStatement(sqlQueryPostiLiberi);
            stmOccupati = con.prepareStatement(sqlQueryPostiOccupati);

            stmLiberi.setInt(1, id_sala);
            stmLiberi.setInt(2, id_spettacolo);
            stmLiberi.setInt(3, id_sala);
            stmLiberi.setInt(4, id_spettacolo);
            stmOccupati.setInt(1, id_sala);
            stmOccupati.setInt(2, id_spettacolo);

            ResultSet resultsLiberi = stmLiberi.executeQuery();
            ResultSet resultsOccupati = stmOccupati.executeQuery();

            try {
                while (resultsLiberi.next()) {
                    Posto posto = new Posto();
                    posto.setId(resultsLiberi.getInt("id_posto"));
                    posto.setId_sala(resultsLiberi.getInt("id_sala"));
                    posto.setRiga(resultsLiberi.getInt("riga"));
                    posto.setColonna(resultsLiberi.getInt("colonna"));
                    posto.setEsiste(resultsLiberi.getBoolean("esiste"));
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
                resultsLiberi.close();
                resultsOccupati.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stmLiberi.close();
            stmOccupati.close();
        }

        return listPosti;
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

    public boolean getSeatAvailability(ArrayList<Integer> posti, int idSpettacolo) throws SQLException {

        String sqlQueryPostiOccupati = "SELECT * FROM spettacolo NATURAL JOIN posto NATURAL JOIN prenotazione WHERE id_spettacolo=?";
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement(sqlQueryPostiOccupati);

            stm.setInt(1, idSpettacolo);
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
                if (rs.next()) {

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

    public void setShow(int x) throws SQLException {
        //data odierna
        Calendar now = Calendar.getInstance();
        long time = now.getTimeInMillis();

        //QUERY
        PreparedStatement s = null;
        String sqlQ = "DELETE FROM prenotazione";
        try {
            s = con.prepareStatement(sqlQ);
            int j = s.executeUpdate();

            if (j > 0) {
                System.out.println("success!!!");
            }
        } finally {
            s.close();
        }

        PreparedStatement stm = null;
        String slqQuery = "DELETE FROM SPETTACOLO";

        try {
            stm = con.prepareStatement(slqQuery);
            int j = stm.executeUpdate();

            if (j > 0) {
                System.out.println("success!!!");
            }
        } finally {
            stm.close();
        }
        //QUERY update spettacolo
        int id = 1;
        int sala = 1;
        for (int k = 1; k < 11; k++) {
            //dta primo spettacolo
            Date new_date = new Date(time + (long) (random() * x * ONE_MINUTE_IN_MILLIS));
            long new_time = new_date.getTime();
            Timestamp t = new Timestamp(new_time);

            for (int j = 0; j < 20; j++) {
                PreparedStatement statement = con.prepareStatement("INSERT INTO spettacolo(id_spettacolo,id_film,data_ora,id_sala) VALUES (?, ?, ?, ?)");

                try {
                    statement.setInt(1, id);
                    statement.setInt(2, k);
                    statement.setTimestamp(3, t);
                    statement.setInt(4, sala);
                    int i = statement.executeUpdate();

                    if (i > 0) {
                        System.out.println("success!!!");
                    }

                    Date tmp = new Date(new_time + (long) (x * ONE_MINUTE_IN_MILLIS));
                    new_time = tmp.getTime();
                    t = new Timestamp(new_time);
                    id++;

                } finally {
                    // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
                    statement.close();
                }
            }
            sala++;
            if (sala > 5) {
                sala = 1;
            }
        }

    }

    public List<Spettacolo> getVendorSeat() throws SQLException {

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
                    spettacolo.setPrezzo(results.getDouble("prezzo"));
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

    public List<Film> getIncassi() throws SQLException {

        //QUERY
        List<Film> listFilm = new ArrayList();

        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT id_film, titolo, SUM(prezzo) AS incassi FROM prenotazione NATURAL JOIN prezzo NATURAL JOIN spettacolo NATURAL JOIN film GROUP BY id_film, titolo ORDER BY incassi DESC";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();

            try {
                while (results.next()) {
                    Film film = new Film();
                    film.setId(results.getInt("id_film"));
                    film.setTitolo(results.getString("titolo"));
                    film.setIncassi(results.getInt("incassi"));

                    film.setIncassi(results.getDouble("incassi"));

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

    public List<Utente> getTopClient() throws SQLException {

        //QUERY
        List<Utente> listUtente = new ArrayList();

        PreparedStatement stm = null;

        try {

            String slqQuery = "SELECT email, SUM(prezzo) AS paga FROM utente NATURAL JOIN prenotazione NATURAL JOIN prezzo GROUP BY email ORDER BY paga DESC";
            stm = con.prepareStatement(slqQuery);

            ResultSet results = stm.executeQuery();

            try {
                while (results.next()) {
                    Utente utente = new Utente();
                    utente.setEmail(results.getString("email"));
                    utente.setPaga(results.getDouble("paga"));

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

    public List<Price> getPrice() throws SQLException {

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
                    price.setPrezzo(results.getFloat("prezzo"));

                    price.setPrezzo(results.getDouble("prezzo"));

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

    public void deletePrenotazione(String email, int id_p) throws SQLException {

        PreparedStatement stat = null;
        String sql1 = "SELECT prezzo FROM prenotazione NATURAL JOIN utente NATURAL JOIN prezzo WHERE email=? AND id_prenotazione=?";
        stat = con.prepareStatement(sql1);
        double k = 0;

        try {

            stat.setString(1, email);
            stat.setInt(2, id_p);

            ResultSet r = stat.executeQuery();
            try {
                while (r.next()) {
                    k = r.getDouble("prezzo");
                }
            } finally {
                r.close();
            }
        } catch (Exception ex) {
            System.out.println("hello " + ex);
        } finally {

            stat.close();
        }

        k = (k * 80.0 / 100.0);

        PreparedStatement st = null;
        String sq = "SELECT credito FROM utente WHERE email=?";
        st = con.prepareStatement(sq);

        double credito = 0;

        try {
            st.setString(1, email);

            ResultSet r = st.executeQuery();
            try {
                while (r.next()) {
                    credito = r.getDouble("credito");
                }
            } finally {
                r.close();
            }
        } finally {
            st.close();
        }

        PreparedStatement statement = null;
        String sql = "UPDATE utente SET credito=? WHERE email=?";
        statement = con.prepareStatement(sql);

        try {
            statement.setDouble(1, credito + k);
            statement.setString(2, email);
            int i = statement.executeUpdate();

            if (i > 0) {
                System.out.println("success!!!");
            }

        } finally {
            statement.close();
        }

        PreparedStatement stm = null;
        String slqQuery = "DELETE FROM prenotazione WHERE id_prenotazione=? ";
        stm = con.prepareStatement(slqQuery);

        try {
            stm.setInt(1, id_p);

            int j = stm.executeUpdate();

            if (j > 0) {
                System.out.println("success!!!");
            }
        } finally {
            stm.close();
        }

    }

    public int getId_prezzo(String tipoSconto) throws SQLException {

        int id_prezzo;
        PreparedStatement stm = null;

        try {

            // SELECT SQL
            String sqlQuery = "SELECT id_prezzo FROM prezzo WHERE tipo=?";
            stm = con.prepareStatement(sqlQuery);

            // Compilazione dei valori mancanti della query
            stm.setString(1, tipoSconto);

            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {

                    id_prezzo = rs.getInt("id_prezzo");
                    return id_prezzo;

                }

            } finally {
                // Chiusura del ResultSet
                rs.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }

        return 0;
    }

    public double getPrezzo(String tipoSconto) throws SQLException {

        double prezzo;
        PreparedStatement stm = null;

        try {

            // SELECT SQL
            String sqlQuery = "SELECT prezzo FROM prezzo WHERE tipo=?";
            stm = con.prepareStatement(sqlQuery);

            // Compilazione dei valori mancanti della query
            stm.setString(1, tipoSconto);

            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {

                    prezzo = rs.getInt("prezzo");
                    return prezzo;
                }

            } finally {
                // Chiusura del ResultSet
                rs.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }

        return 0.0;
    }

    public int getLastId_prenotazione() throws SQLException {

        int last_id_prenotazione;
        PreparedStatement stm = null;

        try {

            // SELECT SQL
            String sqlQuery = "SELECT MAX(id_prenotazione) AS last_id_prenotazione FROM prenotazione";
            stm = con.prepareStatement(sqlQuery);

            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {

                    last_id_prenotazione = rs.getInt("last_id_prenotazione");
                    return last_id_prenotazione;

                }

            } finally {
                // Chiusura del ResultSet
                rs.close();
            }
        } finally {
            // Chiusura del PreparedStatement
            stm.close();
        }

        return 0;
    }

    public void setPrenotazione(int id_prenotazione, int id_spettacolo, int id_prezzo, int id_posto, String email) throws SQLException {

        PreparedStatement statement = con.prepareStatement("INSERT INTO prenotazione(id_prenotazione, id_spettacolo, id_prezzo, id_posto, data_ora_operazione, email) VALUES (?, ?, ?, ?, ?, ?)");

        java.util.Date date = new java.util.Date();
        Timestamp data_ora_operazione = new Timestamp(date.getTime());

            try {

        
            try{

                statement.setInt(1, id_prenotazione);
                statement.setInt(2, id_spettacolo);
                statement.setInt(3, id_prezzo);
                statement.setInt(4, id_posto);
                statement.setTimestamp(5, data_ora_operazione);
                statement.setString(6, email);

                int i = statement.executeUpdate();

                if (i > 0) {
                    System.out.println("success!!!");
                }


            } finally {
                // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
                statement.close();
            }

            }catch(SQLException ex){
                System.out.println("HELLOOO:" + ex);
                
            }finally {
            // Chiusura del PreparedStatement, da fare SEMPRE in un blocco finally
            statement.close();

        }
    }

    

    public boolean checkPrenotazione(int id_spettacolo, int id_posto) throws SQLException {

        PreparedStatement statement = con.prepareStatement("SELECT id_spettacolo, id_posto FROM prenotazione WHERE id_spettacolo=? AND id_posto=?");

        try {
            statement.setInt(1, id_spettacolo);
            statement.setInt(2, id_posto);

            ResultSet rs = statement.executeQuery();

            try {
                if (rs.next()) {
                    id_spettacolo = rs.getInt("id_spettacolo");
                    id_posto = rs.getInt("id_posto");
                    return false; // se ci sono risultati significa che quei posti sono già stati prenotati
                }
            } finally {
                // Chiusura del ResultSet
                rs.close();
            }

        } finally {
            // Chiusura del PreparedStatement
            statement.close();
        }
        return true;
    }
    
    

    public double checkCredito(String email) throws SQLException {

        PreparedStatement st = null;
        String sq = "SELECT credito FROM utente WHERE email=?";
        st = con.prepareStatement(sq);

        double credito = 0;

        try {
            st.setString(1, email);

            ResultSet r = st.executeQuery();
            try {
                if (r.next()) {
                    credito = r.getDouble("credito");
                    return credito;
                }
            } finally {
                r.close();
            }
        } finally {
            st.close();
        }

        return 0;
    }

    public void setCredito(double credito, String email) throws SQLException {

        PreparedStatement st = null;
        String sq = "UPDATE utente SET credito=? WHERE email=?";
        st = con.prepareStatement(sq);

        try {
            st.setDouble(1, credito);
            st.setString(2, email);

            int i = st.executeUpdate();

            if (i > 0) {
                System.out.println("success!!!");
            }

        } finally {
            st.close();
        }
    }

    public Prenotazione getPrenotazione(int id) throws SQLException {

        PreparedStatement stm = null;

        try {

            String sqlInsert = "SELECT * FROM prenotazione NATURAL JOIN film NATURAL JOIN spettacolo NATURAL JOIN prezzo WHERE id_prenotazione=?";
            stm = con.prepareStatement(sqlInsert);

            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {

                    Prenotazione s = new Prenotazione();
                    s.setData_ora_prenotazione(rs.getTimestamp("data_ora_operazione"));
                    s.setId_spettacolo(rs.getInt("id_spettacolo"));
                    s.setId_prezzo(rs.getInt("prezzo"));
                    s.setId_posto(rs.getInt("id_posto"));
                    s.setEmail(rs.getString("email"));
                    s.setData_ora_spettacolo(rs.getTimestamp("data_ora"));
                    s.setTitolo(rs.getString("titolo"));  
                    s.setId_prenotazione(id);
                    
                    return s;
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

    public Timestamp getData_ora(int id_spettacolo) throws SQLException {

        PreparedStatement stm = null;

        try {

            String sqlInsert = "SELECT data_ora FROM spettacolo WHERE id_spettacolo=?";
            stm = con.prepareStatement(sqlInsert);

            stm.setInt(1, id_spettacolo);

            ResultSet results = stm.executeQuery();

            try {
                if (results.next()) {
                    Timestamp data_ora = results.getTimestamp("data_ora");
                    return data_ora;
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
    
    public void blocca_sbloccaPosto(int id_sala, int id_posto) throws SQLException{
        PreparedStatement stm = null;
        PreparedStatement s = null;
        PreparedStatement s2 = null;
        
        try{
            String sql="SELECT esiste FROM posto WHERE id_posto=? AND id_sala=?";
            s = con.prepareStatement(sql);

            s.setInt(1, id_posto);
            s.setInt(2, id_sala);

            ResultSet results = s.executeQuery();
            Boolean check=true;
            if (results.next()) {
                    check=results.getBoolean("esiste");
                }
            if(check){
                try{
                String sqlInsert = "UPDATE posto SET esiste=false WHERE id_sala=? AND id_posto=?";
                
                stm = con.prepareStatement(sqlInsert);
                stm.setInt(1, id_sala);
                stm.setInt(2, id_posto);

                int i = stm.executeUpdate();

                if (i > 0) {
                    System.out.println("success!!!");
                }
                }catch(Exception ex){
                    System.out.println("HELL" + ex);
                    
                }
                stm.close();
            }else{
                try{
                    String sqlInsert = "UPDATE posto SET esiste=true WHERE id_sala=? AND id_posto=?";
            
                    s2 = con.prepareStatement(sqlInsert);
                    s2.setInt(1, id_sala);
                    s2.setInt(2, id_posto);

                    int i = s2.executeUpdate();

                    if (i > 0) {
                        //System.out.println("success!!!");
                    }
                }catch(Exception ex){
                    System.out.println("HELLllllllI" + ex);
                    
                }finally{
                    s2.close();
                }
            }
            
        }finally{
            s.close();
            
            
        }
        
        
    }

}
