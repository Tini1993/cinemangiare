
package servlets;

import Bean.Prenotazione;
import db.DBManager;import db.Hall;
;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet che si occupa di controllare se effettivamente i posti scelti nella
 * griglia della sala sono disponibili
 * Gli ID dei posti che l'utente sta tentando di prenotare sono disponibili
 * come valori interi separati da una virgola l'uno dall'altro
 * @author Simone
 */
public class CheckSeatAvailabilityServlet extends HttpServlet {

    private DBManager manager;
    ArrayList<String> posti = null;
    ArrayList<Integer> result = null;
    boolean availability = false;
       
    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        // Stringa da splittare
        String seatsToVerify = request.getParameter("posti");
        String idSpettacolo = request.getParameter("idShow");
        int hallID = -1;
        try{
            hallID = Integer.parseInt(request.getParameter("idHall"));
            // Splitto la stringa in base a degli splitter noti a priori e parso
            this.parseRequestedSeats(seatsToVerify, hallID);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!1");
            request.setAttribute("errorEx", ex);
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
        
        // Chiamo la funzione nel DBManager che controlla se i posti sono occupati oppure disponibili
        if(!posti.isEmpty() && posti!=null) {
            availability = this.manager.getSeatAvailability(result, idSpettacolo);
        }
        else {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!2");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
        
        if(!availability) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!3");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
        
        List<Prenotazione> biglietti = new ArrayList();
        for(int i=0; i<result.size(); i++) {
            Prenotazione t = new Prenotazione();
            t.setId_posto(result.get(i));       // t.setIdSeat(result.get(i));
            t.setId_spettacolo(Integer.parseInt(idSpettacolo));     // t.setIdShow(Integer.parseInt(idSpettacolo));
            biglietti.add(t);            
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("biglietti", biglietti);
        request.setAttribute("numBiglietti", result.size());
        request.setAttribute("prezzoBigliettoIntero", 10);
        request.setAttribute("percentualeDiSconto", 20);
        RequestDispatcher rd = request.getRequestDispatcher("/sconti.jsp");
        rd.forward(request, response);
       
        
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // NON FA NULLA
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!4");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
    
    

    /**
     * Metodo per processare la stringa dei posti che viene mandata in input con una POST
     * @param seats La stringa su cui eseguire lo splitting dei valori
     * @return Un ArrayList<Integer> contenete i posti che l'utente sta cercando di prenotare
     */
    private ArrayList<Integer> parseRequestedSeats(String seats, int hallID) throws SQLException {
        
        posti = new ArrayList<>();
        result = new ArrayList<>();
        
        String delimis = " ";
        String[] tokens = seats.split(delimis);
        
        for(int i=0;i<tokens.length;i++) {
            posti.add(tokens[i]);
        }
        // 2_3
        for(int i=0;i<posti.size();i++) {
            int riga = Hall.getRowCoordinate(posti.get(i));
            int colonna = Hall.getColumnCoordinate(posti.get(i));
            
            result.add( manager.getTrueSeatID(riga,colonna,hallID) );
        }
        
        /*Debug*/System.out.println("STRING LENGTH: " + tokens.length);
        /*Debug*/System.out.println(result.toString());
        
        return result;
    }
}
