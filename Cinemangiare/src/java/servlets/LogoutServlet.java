package servlets;



import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 
 * @version 1.0
 */

public class LogoutServlet extends HttpServlet {
    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession sessione = req.getSession(false);

        if (sessione != null) {
            sessione.removeAttribute("user");
            sessione.invalidate();
        }

        resp.sendRedirect(req.getContextPath() + "/ListaFilmServlet");
    }
}