/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import db.Hall;
import java.sql.SQLException;

/**
 * Questa servlet serve per comporre la visualizzazione dei posti di una sala
 * @author Mattia
 */

/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

public class PostiServlet extends HttpServlet {
    
    private DBManager manager;
    public static ServletContext servletContext;    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(true);
        
        int id_spettacolo = Integer.parseInt(request.getParameter("idShow"));
        int id_sala = Integer.parseInt(request.getParameter("idHall"));
        
        Hall hall = manager.getSeatMatrix(id_spettacolo, id_sala);
        
        if(hall==null) {
            // Metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
            request.setAttribute("errorMessage", "Errore durante il caricamento dei posti disponibili!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
        else {
            System.out.println(hall.getMapString());
            session.setAttribute("SeatString", hall.getMapString());
            RequestDispatcher rd = request.getRequestDispatcher("/posti.jsp");
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/errorLogna.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/errorLogna.jsp");
            rd.forward(request, response);
        }
    }
}
