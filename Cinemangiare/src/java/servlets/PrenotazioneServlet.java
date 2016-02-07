/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(true);

        String[] tipoSconto = new String[50];
        for (int i = 1; i < 50; i++) {
            tipoSconto[i] = (request.getParameter("" + i + ""));
            if(tipoSconto[i] != null)
                System.out.println(tipoSconto[i]);
        }
        
        int id_spettacolo = Integer.parseInt(request.getParameter("idShow"));
        System.out.println(id_spettacolo);
        
        String email = request.getParameter("email");
        System.out.println(email);
        
        String stringaPosti = request.getParameter("stringaPosti");
        this.parseRequestedSeats(stringaPosti);
        for(int i=0; i<posti.size(); i++){
            System.out.println(posti.get(i));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PrenotazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PrenotazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo per processare la stringa dei posti che viene mandata in input con una POST
     * @param stringaPosti La stringa su cui eseguire lo splitting dei valori
     * @return Un ArrayList<Integer> contenete i posti che l'utente sta cercando di prenotare
     */
    private ArrayList<Integer> parseRequestedSeats(String stringaPosti) throws SQLException {
        
        posti = new ArrayList<>();
        
        String delim = " ";
        String[] tokens = stringaPosti.split(delim);       
        int t = 0;
               
        for(int i=0;i<tokens.length;i++) {
            t = Integer.parseInt(tokens[i]);
            posti.add(t);
        }
        
        /*Debug*/System.out.println("STRING LENGTH: " + tokens.length);
        /*Debug*/System.out.println(posti.toString());
        
        return posti;
    }
}


