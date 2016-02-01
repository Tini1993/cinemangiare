/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Film;
import Bean.Prenotazione;
import Bean.Spettacolo;
import db.DBManager;
import java.io.IOException;
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
 *
 * @author jack
 */
public class AdminServlet extends HttpServlet {
    
    private DBManager manager;
       
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
       
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
           try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante login admin!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            HttpSession session = request.getSession(true);
            
            List <Spettacolo> spettacolo = manager.getVendorSeat();
            
            // guardo se la query ha dato dei risultati
            if (spettacolo == null) {
                
                request.setAttribute("errorMessage", "spettacoli non trovati !");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp"); 
                rd.forward(request, response);
                
            } else {
                session.setAttribute("ShowSel", spettacolo);
                RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
    
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    
    

   

