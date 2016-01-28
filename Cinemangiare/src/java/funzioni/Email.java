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
    public static void InvioEmail(String host, String port, final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException, MessagingException {

        // connessione server smtp
        Properties properties = new Properties();
        properties.put( "mail.smtp.host", host );
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put( "mail.debug", "true" );

        
        
        // Crea l'oggetto per autenticarsi
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
        
        // Creazione del messaggio
        Message msg = new MimeMessage(session);
        InternetAddress from = new InternetAddress(userName);
        InternetAddress to[] = InternetAddress.parse(toAddress);
        
        // Compilazione del messaggio
        msg.setFrom(from);
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // Spedisco il messaggio
        Transport.send(msg);
    }
    
}
