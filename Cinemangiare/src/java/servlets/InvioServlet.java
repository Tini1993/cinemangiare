/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import Bean.Prenotazione;
import Bean.Utente;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import db.DBManager;
import funzioni.Email;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author colom
 */
public class InvioServlet extends HttpServlet {

    private DBManager manager;
    public static ServletContext servletContext;
    
    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        int idpre=Integer.parseInt(request.getParameter("prenota"));
        Prenotazione preno = null;
        try {
            preno=manager.getPrenotazione(idpre);
        } catch (SQLException ex) {
            Logger.getLogger(InvioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int id=preno.getId_prenotazione();
        int prezzo_pagato = preno.getId_prezzo();
        int posto = preno.getId_posto();
        int spettacolo = preno.id_spettacolo;
        Date data =preno.getData_ora_prenotazione();
        String email;
        email = preno.getEmail();
        
        
        
        //creo il pdf per la prenotazione
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(servletContext.getRealPath("/") + "ticket.pdf"));
            System.out.println("Creazione file riuscita");
        } catch (DocumentException ex) {
            request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti. "
                        + "I tuoi biglietti sono comunque stati salvati, è necessario contattare l'admin");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
        }
        document.open();
        
    
            //creo il paragrafo per il tiolo della pagina
            Paragraph p = new Paragraph("Biglietto n° " + id);
            p.setAlignment(Element.ALIGN_CENTER);
            try {
                document.add(p);
                System.out.println("Scrittura file riuscita");
            } catch (DocumentException ex) {
                request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti.");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
            }
            
            //creo il paragrafo per i dati del biglietto
            p = new Paragraph("");
            p.setAlignment(Element.ALIGN_LEFT);
            p.add("Biglietto: " + id + "\n");
            p.add("Utente: " + email + "\n");
            p.add("Prezzo pagato: " + prezzo_pagato + "€\n");
            p.add("Numero posto: " + posto+ "\n");
            p.add("Spettacolo: " + spettacolo + "\n");
            p.add("Data: " + data  + "\n");
            
            try {
                document.add(p);
                System.out.println("Scrittura file riuscita");
            } catch (DocumentException ex) {
                request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti. "
                        + "I tuoi biglietti sono comunque stati salvati, è necessario contattare l'admin");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
            }
            
            //creo il qrcode e lo aggiungo alla pagina
            Image image = null;
            File file;
            p = new Paragraph("");
            file = QRCode.from("u:" + email + 
                    "p:" + prezzo_pagato +
                    "np:" + posto + 
                    "s:" + spettacolo + 
                    "d:" + data).to(ImageType.JPG).withSize(100, 100).file();
            try {
                image = Image.getInstance(file.getAbsolutePath());
            } catch (BadElementException | MalformedURLException ex) {
               // Logger.getLogger(SendTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(file.toString());
            try {
                document.add(image);
            } catch (DocumentException ex) {
                request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti. "
                        + "I tuoi biglietti sono comunque stati salvati, è necessario contattare l'admin");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
            }
            
            try {
                document.add(p);
                document.newPage();
                System.out.println("Scrittura file riuscita");
            } catch (DocumentException ex) {
                request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti. "
                        + "I tuoi biglietti sono comunque stati salvati, è necessario contattare l'admin");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
            }
        
        document.close();
        
        try {
            //invio la mail per la conferma della prenotazione con allegato il pdf
             Email.ticketEmail("smtp.gmail.com", "587", "ccinemangiare@gmail.com", "Cinemangiaredb",
                    email, "Conferma acquisto biglietti", "Gentile cliente," + "\n"
                            + "le confermiamo la prenotazione presso il nostro cinema." + "\n"
                            + "In allegato troverà il file pdf da stampare contenente i biglietti",
                    servletContext.getRealPath("/") + "ticket.pdf");
        } catch (MessagingException ex) {
            request.setAttribute("errorMessage", "Errore nella generazione del pdf dei biglietti. "
                        + "I tuoi biglietti sono comunque stati salvati, è necessario contattare l'admin");
                RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                rd.forward(request, response);
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/final.jsp");
        dispatcher.forward(request, response);
    }

}