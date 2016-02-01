package servlets;

import db.DBManager;
import Bean.Utente;
import java.io.IOException;
import java.sql.SQLException;
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

public class LoginServlet extends HttpServlet {
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

        // controllo nel DB se esiste un utente con lo stesso username + password

        Utente user;
        try {

            user = manager.authenticate(username, password);

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        // se non esiste, ridirigo verso pagina di login con messaggio di errore
        if (user == null) {
            // metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
            req.setAttribute("errorMessage", "Username/password non esistente !");
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);

        } else if( user.id_ruolo == 1 ){
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