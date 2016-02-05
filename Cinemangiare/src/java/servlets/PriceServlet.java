/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Price;
import Bean.Spettacolo;
import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jack
 */
@WebServlet(name = "PriceServlet", urlPatterns = {"/PriceServlet"})
public class PriceServlet extends HttpServlet {
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
       
        HttpSession session = request.getSession(true);
            
            List <Price> listPrice = manager.getPrice();
            
            // guardo se la query ha dato dei risultati
            if (listPrice == null) {
                
                request.setAttribute("errorMessage", "spettacoli non trovati !");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp"); 
                rd.forward(request, response);
                
            } else {
                for(int i=0; i<listPrice.size(); i++)
                     System.out.println(listPrice.get(i).getId());
                
                session.setAttribute("ShowPrice", listPrice);
                RequestDispatcher rd = request.getRequestDispatcher("/prices.jsp");
                rd.forward(request, response);
            }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
           try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore durante login admin!");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        try {
            processRequest(request,response);

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
    
}
