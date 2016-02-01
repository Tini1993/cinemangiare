/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Film;
import Bean.Spettacolo;
import java.lang.Exception;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.DBManager;
import Bean.Utente;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrea
 */
public class FilmServlet extends HttpServlet {

    private DBManager manager;
    public static ServletContext servletContext;

    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession(true);
            String titolo = request.getParameter("titolo");
            // Prendo il titolo del film dall'url, perchè è un GET

            Film film = manager.getFilm(titolo);
            List <Spettacolo> spettacolo = manager.getSpettacolo(film.id_film);
            //Spettacolo spettacolo = manager.getSpettacolo(film.id_film);
            
            // guardo se la query ha dato dei risultati
            if (film == null) {
                
                request.setAttribute("errorMessage", "Film non esistente !");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp"); 
                rd.forward(request, response);
                
            } else {
                session.setAttribute("FilmSel", film);
                session.setAttribute("ShowSel", spettacolo);
                RequestDispatcher rd = request.getRequestDispatcher("/film.jsp");
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            request.setAttribute("errorEx", ex);
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        } catch (Exception ex) {
        }
    }
}
