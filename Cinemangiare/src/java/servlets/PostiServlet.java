/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Posto;
import Bean.Spettacolo;
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
import java.util.List;

/**
 * Questa servlet serve per comporre la visualizzazione dei posti di una sala
 *
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

            int id_spettacolo = Integer.parseInt(request.getParameter("idShow"));
            int id_sala = Integer.parseInt(request.getParameter("idHall"));

            List<Posto> posto = manager.getListPosti(id_spettacolo, id_sala);
            /*for (int i = 0; i < posto.size(); i++) {
                System.out.println(posto.get(i).getId() + " " + posto.get(i).isPrenotato());
            }*/

        // Bubble sort per sortare i posti per id
            for (int i = 0; i < posto.size(); i++) {
                for (int j = 0; j < posto.size(); j++) {
                    if (posto.get(i).getId() < posto.get(j).getId()) {
                        Posto k = new Posto();
                        k = posto.get(i);
                        posto.set(i, posto.get(j));
                        posto.set(j, k);
                    }
                }
            }
            String postiStringa = "['";
            //trasformo i posti in stringa
            int x=0;
            for (int j = 1; j <= 5; j++) {
                for (int i = 1; i <= 10; i++) {
                    if (posto.get(x).prenotato) {
                        postiStringa += "u";
                    } else {
                        postiStringa += "s";
                    }
                    x++;
                }
                if(j!=5)
                postiStringa += "','";
            }
            postiStringa += "']";
            
            System.out.println(postiStringa);

            //['lloolllool','oooolllool']
            /*for (int i = 0; i < posto.size(); i++) {
                System.out.println("POST SORT " + posto.get(i).getId() + " " + posto.get(i).isPrenotato());
            }*/
            if (posto == null) {

                request.setAttribute("errorMessage", "Errore durante il caricamento dei posti disponibili!");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
            } else {
                session.setAttribute("ListPosti", postiStringa);
                session.setAttribute("idShow", id_spettacolo);
                session.setAttribute("idHall", id_sala);
                RequestDispatcher rd = request.getRequestDispatcher("/posti.jsp");
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
            //processRequest(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }
    }
}
