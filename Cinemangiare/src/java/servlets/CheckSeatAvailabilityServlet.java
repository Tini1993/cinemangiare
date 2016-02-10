
package servlets;

import Bean.Prenotazione;
import Bean.Price;
import db.DBManager;
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
    ArrayList<Integer> posti = null;
    boolean availability = false;
       
    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        // Stringa da splittare
        String stringaPosti = request.getParameter("posti");
        int idShow = Integer.parseInt(request.getParameter("idShow"));
        int idHall = 0;
        
        try{
            idHall = Integer.parseInt(request.getParameter("idHall"));
            
            // Splitto la stringa in base a degli splitter noti a priori e parso
            this.parseRequestedSeats(stringaPosti);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!1");
            request.setAttribute("errorEx", ex);
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
        
        // Chiamo la funzione nel DBManager che controlla se i posti sono occupati oppure disponibili
        if(!posti.isEmpty() && posti!=null) {
            availability = this.manager.getSeatAvailability(posti, idShow);
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
        for(int i=0; i<posti.size(); i++) {
            Prenotazione p = new Prenotazione();
            p.setId_posto(posti.get(i));       
            p.setId_spettacolo(idShow);    
            biglietti.add(p);            
        }
        List<Price> price = new ArrayList();
        price = manager.getPrice();
        HttpSession session = request.getSession(true);
        session.setAttribute("idHall", idHall);
        session.setAttribute("stringaPosti", stringaPosti);
        session.setAttribute("idShow", idShow);
        request.setAttribute("prezzi", price);
        session.setAttribute("biglietti", biglietti);
        request.setAttribute("numBiglietti", posti.size());
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
            request.setAttribute("errorEx", ex);
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
    
    

    /**
     * Metodo per processare la stringa dei posti che viene mandata in input con una POST
     * @param stringaPosti La stringa su cui eseguire lo splitting dei valori
     * @return Un ArrayList<Integer> contenete i posti che l'utente sta cercando di prenotare
     */
    private ArrayList<Integer> parseRequestedSeats(String stringaPosti) throws SQLException {
        
        posti = new ArrayList<>();
        
        String delim = " ";
        String[] tokens = stringaPosti.split(delim);       
        int t = 0;
               
        for(int i=0;i<tokens.length;i++) {
            t = Integer.parseInt(tokens[i]);
            if (!posti.contains(t))
                posti.add(t);
        }
        
        /*Debug*/System.out.println("STRING LENGTH: " + tokens.length);
        /*Debug*/System.out.println(posti.toString());
        
        return posti;
    }
}
