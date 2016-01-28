/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import funzioni.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author colom
 */
public class RecuperoPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private DBManager manager;
    
    @Override
    public void init() throws ServletException 
    {
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
        
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
    {
        
        // Prendo email utente dalla request
        String utenteEmail = request.getParameter("utenteEmail");
        
        if(utenteEmail!=null && utenteEmail!="" && utenteEmail.contains("@")) {
            // Prendo la password dell'utente dal database
            String userPassword = this.manager.getPassword(utenteEmail);


            // Invio della mail con la password
            try {
                 Email.InvioEmail("smtp.gmail.com", "587", "anvion07@gmail.com", "andrea",
                        utenteEmail, "Password recovery service", "Gentile cliente," + "\n"
                                + "le confermiamo che la password del suo account " + utenteEmail + "\n"
                                + "è stata ripristinata in seguito ad una richiesta" + "\n"
                                + "da lei effettuata." + "\n"
                                + "Potrà continuare ad utilizzare i servizi disponibili " + "\n"
                                + "in cinema.kogna@gmail.com, utilizzando la seguente password: " + userPassword + "\n"
                                + "Cordiali saluti.");

            } catch (MessagingException ex) {

                // Gestione dell'errore
                request.setAttribute("errorMessage", "Errore durante l'invio della mail!");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);

            }
            // TODO: Inserire pagina per confermare l'invio della mail
            // L'email contenente la password dell'utente è stata inviata
            request.setAttribute("mailSentMessage", "La mail contenente la sua password è stata inviata");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        
        else {
            // Gestione dell'errore
            request.setAttribute("errorMessage", "L'email inserita non è una mail valida!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            
            // Gestione dell'errore
            request.setAttribute("errorMessage", "Errore durante il caricamento dei dati utente!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            
            // Gestione dell'errore
            request.setAttribute("errorMessage", "Errore durante il caricamento dei dati utente!");
            RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
            rd.forward(request, response);
        }
    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
