/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Utente;
import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Gabry147
 */
public class InfoUtenteServlet extends HttpServlet {

    private DBManager manager;
    
    
    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");
        if (user == null) {

                // Metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);    
        }
        //List<Ticket> p = null;
        //List<TicketInfo> prenotazioni = new ArrayList();
        
        /*try {
            p =  manager.getUserTickets(user.getUserID());
            for(Ticket t : p) {
                TicketInfo info = new TicketInfo();
                info.setIdTicket(t.getIdTicket());
                Show s = manager.getShow(t.getIdShow());
                Movie m = manager.getMovie(s.getIdMovie());
                info.setFilm(m.getTitle());
                Seat seat = manager.getSeat(t.getIdSeat());
                int row = seat.getRow()+1;
                int col = seat.getCol()+1;
                info.setPosto("Fila: " + row + " Posto: " + col);
                int price = 0;
                switch(t.getIdPrice()) {
                    case 1: price=10; break;
                    case 2: price=8; break;
                }
                info.setPrice(price);
                info.setData(s.getDateTime());
                info.setSala(s.getIdHall());
                prenotazioni.add(info);
            }
        } catch(Exception e) {
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response); 
        }*/
        //request.setAttribute("prenotazioni", prenotazioni);
        RequestDispatcher rd = request.getRequestDispatcher("/utente.jsp");
        rd.forward(request, response); 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
