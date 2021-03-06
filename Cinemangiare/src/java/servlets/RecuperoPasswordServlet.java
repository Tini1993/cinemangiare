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
import java.util.logging.Level;
import java.util.logging.Logger;
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
       // String utenteEmail="anvion07@gmail.com";
        if(utenteEmail!=null && utenteEmail!="" && utenteEmail.contains("@")) {
            // Prendo la password dell'utente dal database
            String utentePassword = this.manager.getPassword(utenteEmail);
//utentePassword="ciao";
            // Invio della mail con la password
            try {
                 Email.InvioEmail("smtp.gmail.com", "587", "cinemangiare@gmail.com", "Cinemangiaredb",utenteEmail, "Recupero password cinema", "Gentile cliente," + "\n"
                                + "le confermiamo che la password del suo account " + utenteEmail + "\n"
                                + "è stata ripristinata in seguito ad una richiesta" + "\n"
                                + "Potrà continuare ad utilizzare il servizio utilizzando la seguente password: " + utentePassword + "\n"
                                + "Cordiali saluti.");

            } catch (MessagingException ex) {

                // Gestione dell'errore
                request.setAttribute("errorMessage", "Errore durante l'invio della mail!");
                RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                rd.forward(request, response);

            }
            // qui il problema
            // L'email contenente la password dell'utente è stata inviata
            request.setAttribute("mailSentMessage", "La mail contenente la sua password è stata inviata");
            RequestDispatcher rd = request.getRequestDispatcher("/index.html");
            rd.forward(request, response);
        }
        
        else {
            // Gestione dell'errore
            request.setAttribute("errorMessage", "L'email inserita non è una mail valida!");
            RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
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
            RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
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
            RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
            rd.forward(request, response);
        }
    }
}
