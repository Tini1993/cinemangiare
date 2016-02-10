/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Bean.Prenotazione;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import static servlets.InvioServlet.servletContext;

/**
 *
 * @author Mattia
 */
public class PrenotazioneServlet extends HttpServlet {
    String email;
    ArrayList<Integer> posti = null;
    ArrayList<Integer> id_prenotazione = new ArrayList<>();
    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // Inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, DocumentException {

        HttpSession session = request.getSession(true);

        int[] id_prezzo = new int[50];
        double[] prezzo = new double[50];
        String[] tipoSconto = new String[50];

        int id_spettacolo = Integer.parseInt(request.getParameter("idShow"));
        int id_sala = Integer.parseInt(request.getParameter("idHall"));
        email = request.getParameter("email");
        String stringaPosti = request.getParameter("stringaPosti");

        double credito = manager.checkCredito(email);

        for (int i = 0; i < 50; i++) {
            tipoSconto[i] = (request.getParameter("" + i + ""));
            if (tipoSconto[i] != null) {

                id_prezzo[i] = manager.getId_prezzo(tipoSconto[i]);
                prezzo[i] = manager.getPrezzo(tipoSconto[i]);
                if (credito - prezzo[i] > 0) {
                    manager.setCredito(credito - prezzo[i], email);
                } else {
                    manager.setCredito(0, email);
                }
            }
        }

        this.parseRequestedSeats(stringaPosti);

        java.util.Date date = new java.util.Date();
        Timestamp data_ora_operazione = new Timestamp(date.getTime());

        for (int i = 0; i < posti.size(); i++) {

            if (manager.checkPrenotazione(id_spettacolo, ((id_sala - 1) * 50) + posti.get(i)) && manager.getData_ora(id_spettacolo).after(data_ora_operazione)) {
                manager.setPrenotazione(manager.getLastId_prenotazione() + 1, id_spettacolo, id_prezzo[i], ((id_sala - 1) * 50) + posti.get(i), email);
                id_prenotazione.add(manager.getLastId_prenotazione());
            } else {
                request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti! Posto già prenotato o spettacolo già iniziato!");
                RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
            }
        }

        
        invioBiglietto(id_prenotazione);
        
        RequestDispatcher rdjsp = request.getRequestDispatcher("/prenotazione.jsp");
        rdjsp.forward(request, response);
         
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Errore durante la prenotazione dei posti! Posto già prenotato o spettacolo già iniziato!");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(PrenotazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (DocumentException ex) {
            Logger.getLogger(PrenotazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            if (!posti.contains(t)) {
                posti.add(t);
            }
        }

        /*Debug*/ System.out.println("STRING LENGTH: " + tokens.length);
        /*Debug*/
        System.out.println(posti.toString());

        return posti;
    }

    private void invioBiglietto(ArrayList<Integer> id_prenotazione) throws DocumentException, FileNotFoundException {

        servletContext = getServletContext();

        ArrayList<Double> prezzo_pagato = new ArrayList<>();
        ArrayList<Integer> posto = new ArrayList<>();
        ArrayList<Integer> spettacolo = new ArrayList<>();
        ArrayList<Date> data = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();

        Prenotazione preno = null;

        try {
            for (int i = 0; i < id_prenotazione.size(); i++) {
                preno = manager.getPrenotazione(id_prenotazione.get(i));
                System.out.println(id_prenotazione.get(i));
                

                prezzo_pagato.add(preno.getId_prezzo());
                posto.add(preno.getId_posto());
                spettacolo.add(preno.getId_spettacolo());
                data.add(preno.getData_ora_prenotazione());
                emails.add(preno.getEmail());
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //int id=preno.getId_prenotazione();
        //creo il pdf per la prenotazione
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(servletContext.getRealPath("/") + "ticket.pdf"));
        System.out.println("Creazione file riuscita");

        document.open();

        //creo il paragrafo per il tiolo della pagina      
        for (int i = 0; i < id_prenotazione.size(); i++) {
            Paragraph p = new Paragraph("Biglietto n° " + id_prenotazione.get(i));

            p.setAlignment(Element.ALIGN_CENTER);

            try {
                document.add(p);
                System.out.println("Scrittura file riuscita");
            } catch (DocumentException ex) {

            }

            //creo il paragrafo per i dati del biglietto
            p = new Paragraph("");
            p.setAlignment(Element.ALIGN_LEFT);
            p.add("Biglietto: " + id_prenotazione.get(i) + "\n");
            p.add("Utente: " + emails.get(i) + "\n");
            p.add("Prezzo pagato: " + prezzo_pagato.get(i) + "€\n");
            p.add("Numero posto: " + posto.get(i) + "\n");
            p.add("Spettacolo: " + spettacolo.get(i) + "\n");
            p.add("Data: " + data.get(i) + "\n");
            
            System.out.println("INFO INFO INFO" + id_prenotazione.get(i) + " " + emails.get(i) + " " + prezzo_pagato.get(i) + " "+ posto.get(i) + " "+ spettacolo.get(i) + " "+ data.get(i) + " "+ "FINE FINE FINE");

            try {
                document.add(p);
                System.out.println("Scrittura file riuscita");
            } catch (DocumentException ex) {

            }
        }
        //creo il qrcode e lo aggiungo alla pagina
        Image image = null;
        File file;
        Paragraph p = new Paragraph("");
        file = QRCode.from("u:" + email
                + "p:" + prezzo_pagato
                + "np:" + posto
                + "s:" + spettacolo
                + "d:" + data).to(ImageType.JPG).withSize(100, 100).file();
        try {
            image = Image.getInstance(file.getAbsolutePath());
        } catch (BadElementException | MalformedURLException ex) {
            // Logger.getLogger(SendTicket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrenotazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(file.toString());
        try {
            document.add(image);
        } catch (DocumentException ex) {

        }

        try {
            document.add(p);
            document.newPage();
            System.out.println("Scrittura file riuscita");
        } catch (DocumentException ex) {

        }

        document.close();

        try {
           
            //invio la mail per la conferma della prenotazione con allegato il pdf
            Email.ticketEmail("smtp.gmail.com", "587", "cinemangiare@gmail.com", "Cinemangiaredb",
                    email, "Conferma acquisto biglietti", "Gentile cliente," + "\n"
                            + "le confermiamo la prenotazione presso il nostro cinema." + "\n"
                            + "In allegato troverà il file pdf da stampare contenente i biglietti",
                    servletContext.getRealPath("/") + "ticket.pdf");} catch (Exception ex) {
            System.out.println("logan");
        }
    }
}
