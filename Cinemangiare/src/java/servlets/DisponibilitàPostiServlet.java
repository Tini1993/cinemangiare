/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Prenotazione;
import db.DBManager;
import db.Hall;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet che serve per verificare se il posto scelto è effettivamente disponibile
 * @author Mattia
 */
public class DisponibilitàPostiServlet extends HttpServlet {

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
        
        
        String seatsToVerify = request.getParameter("postiString");
        String idSpettacolo = request.getParameter("showId");
        int hallID;
        
        try{
            hallID = Integer.parseInt(request.getParameter("hallId"));
           
            this.parseRequestedSeats(seatsToVerify, hallID);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
        
        // Si guarda attraverso il DBManager se i posti sono liberi
        if(!posti.isEmpty() && posti!=null) {
            availability = this.manager.getSeatAvailability(result, idSpettacolo);
        }
        else {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
        
        if(!availability) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
        
        List<Prenotazione> biglietti = new ArrayList();
        for(int i=0; i<result.size(); i++) {
            Prenotazione t = new Prenotazione();
            t.setId_posto(result.get(i));
            t.setId_spettacolo(Integer.parseInt(idSpettacolo));
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
    }
    
    

    /**
     * Processo la lista dei posti
     * @param seats 
     * @return ArrayList<Integer> dei posti che si vogliono prenotare
     */
    private ArrayList<Integer> parseRequestedSeats(String seats, int hallID) throws SQLException {
        
        posti = new ArrayList<>();
        result = new ArrayList<>();
        
        String delimis = ",";
        String[] tokens = seats.split(delimis);
        
        for(int i=0;i<tokens.length;i++) {
            posti.add(tokens[i]);
        }
      
        for(int i=0;i<posti.size();i++) {
            int riga = Hall.getRowCoordinate(posti.get(i));
            int colonna = Hall.getColumnCoordinate(posti.get(i));
            
            result.add( manager.getTrueSeatID(riga,colonna,hallID) );
        }
       
        return result;
    }
}