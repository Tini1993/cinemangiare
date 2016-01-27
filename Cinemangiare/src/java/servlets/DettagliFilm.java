/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import Bean.Film;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author colom
 */
public class DettagliFilm extends HttpServlet {

   private DBManager manager;
    
    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
        
    }
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
        
        try {
            
            // Prendo l'oggetto sessione dalla request
            HttpSession session = request.getSession(false);
            
            // Prendo l'id del film che viene messo in GET request
            int id_film = Integer.parseInt(request.getParameter("idMovie"));

            // Tento di prendere il film desiderato dal database
            Film film = this.manager.getFilm(id_film);
            


            // Il film non è stato trovato! Mando l'utente alla pagina di errore errorPageMovieNotFound.jsp!
            if(film==null) {
                    // Metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
                    session.setAttribute("messageMovieShowNotFoundError", "Il film o i suoi show non sono stati trovati!");
                    RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                    rd.forward(request, response);
            }

            
            // Il film è stato trovato nel database e i dati sono stati memorizzati nell'oggetto Movie
            else {
                System.out.println(film+ " impostato e show inseriti");
                session.setAttribute("Movie", film);
                RequestDispatcher rd = request.getRequestDispatcher("/prenota.jsp");
                rd.forward(request, response);
            }



            } catch (Exception ex) {
                request.setAttribute("errorMessage", "Errore durante il caricamento dei dati");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
            }
    }

}