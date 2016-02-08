/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mattia
 */
public class PrenotazioneServlet extends HttpServlet {

    ArrayList<Integer> posti = null;
    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession(true);

        int[] id_prezzo = new int[50];
        double[] prezzo = new double[50];
        String[] tipoSconto = new String[50];
        
        int id_spettacolo = Integer.parseInt(request.getParameter("idShow"));
        int id_sala = Integer.parseInt(request.getParameter("idHall"));
        String email = request.getParameter("email");
        String stringaPosti = request.getParameter("stringaPosti");
        
        System.out.println(id_sala);
        
        double credito = manager.checkCredito(email);
        
        for (int i = 0; i < 50; i++) {
            tipoSconto[i] = (request.getParameter("" + i + ""));
            if (tipoSconto[i] != null) {
                
                id_prezzo[i] = manager.getId_prezzo(tipoSconto[i]);
                prezzo[i] = manager.getPrezzo(tipoSconto[i]);
                if(credito - prezzo[i] > 0)
                    manager.setCredito(credito - prezzo[i], email);
                else
                    manager.setCredito(0, email);
            }
        }

        this.parseRequestedSeats(stringaPosti); 

        for (int i = 0; i < posti.size(); i++) {

            if (manager.checkPrenotazione(id_spettacolo, (id_sala*50)+posti.get(i))) {
                manager.setPrenotazione(manager.getLastId_prenotazione() + 1, id_spettacolo, id_prezzo[i], (id_sala*50)+posti.get(i), email);
            } else {
                request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti! Posto già prenotato!");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/prenotazione.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti! Posto già prenotato!");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti! Posto già prenotato!");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
        }
    }

    /**
     * Metodo per processare la stringa dei posti che viene mandata in input con
     * una POST
     *
     * @param stringaPosti La stringa su cui eseguire lo splitting dei valori
     * @return Un ArrayList<Integer> contenete i posti che l'utente sta cercando
     * di prenotare
     */
    private ArrayList<Integer> parseRequestedSeats(String stringaPosti) throws SQLException {

        posti = new ArrayList<>();

        String delim = " ";
        String[] tokens = stringaPosti.split(delim);
        int t = 0;

        for (int i = 0; i < tokens.length; i++) {
            t = Integer.parseInt(tokens[i]);
            posti.add(t);
        }

        /*Debug*/        System.out.println("STRING LENGTH: " + tokens.length);
        /*Debug*/
        System.out.println(posti.toString());

        return posti;
    }
}
