package servlets;

import db.DBManager;
import Bean.Utente;
import java.io.IOException;
import java.sql.SQLException;
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
 *
 * @author
 * @version 1.0
 */

public class RegistrazioneServlet extends HttpServlet {
    private DBManager manager;
    
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {

            manager.register(username, password);

        } catch (SQLException ex) {
            req.setAttribute("errorEx", ex);
            req.setAttribute("errorMessage", "Email già usata!");
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
        }
        
        Utente user = new Utente() ;
        try {
            user = manager.authenticate(username, password);
        } catch (SQLException ex) {
            req.setAttribute("errorEx", ex);
            req.setAttribute("errorMessage", "Errore SQL: errore durante il caricamento dei dati");
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
        }
        
        if( user.id_ruolo == 1 ){
            //se è admim parte admin servlet
            // imposto l'utente connesso come attributo di sessione
            // per adesso e' solo un oggetto String con il nome dell'utente, ma posso metterci anche un oggetto User
            // con, ad esempio, il timestamp di login

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // mando un redirect a un'altra servlet
            resp.sendRedirect(req.getContextPath() + "/AdminServlet");
      
        }else{

            // imposto l'utente connesso come attributo di sessione
            // per adesso e' solo un oggetto String con il nome dell'utente, ma posso metterci anche un oggetto User
            // con, ad esempio, il timestamp di login

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // mando un redirect a un'altra servlet
            resp.sendRedirect(req.getContextPath() + "/ListaFilmServlet");
        }
    }
}