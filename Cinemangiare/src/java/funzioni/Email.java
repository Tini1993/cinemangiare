/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funzioni;

import java.util.Date;
import java.util.Properties;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author colom
 */
public class Email {

    public static void InvioEmail(String host, String port, final String utente, final String password, String destinatario,
            String oggetto, String messaggio) throws AddressException, MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(utente, password);
            }
        });
// Creazione del messaggio
        try {
            Message msg = new MimeMessage(session);

// Compilazione del messaggio
            msg.setFrom(new InternetAddress(utente));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject(oggetto);
            msg.setSentDate(new Date());
            msg.setText(messaggio);
// Spedisco il messaggio
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ticketEmail(String host, String port, final String utente, final String password, String destinatario,
            String oggetto, String messaggio, String biglietto) throws AddressException, MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(utente, password);
            }
        });
// Creazione del messaggio
        try {
            Message msg = new MimeMessage(session);

            BodyPart corpomessaggio = new MimeBodyPart();
            corpomessaggio.setText(messaggio);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpomessaggio);

//unisco l'allegato al messaggio
            corpomessaggio = new MimeBodyPart();
            DataSource source = new FileDataSource(biglietto);
            corpomessaggio.setDataHandler(new DataHandler(source));
            corpomessaggio.setFileName("ticket.pdf");
            multipart.addBodyPart(corpomessaggio);

            msg.setContent(multipart);

            msg.setFrom(new InternetAddress(utente));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject(oggetto);
            msg.setSentDate(new Date());
// Spedisco il messaggio
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
