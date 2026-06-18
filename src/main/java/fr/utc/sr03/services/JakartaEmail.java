package fr.utc.sr03.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


public class JakartaEmail {
    private final String login;
    private final String password;

    public JakartaEmail(String login, String password) {
        this.login=login;
        this.password=password;
    }

    public void sendMailTest(){
        String to = "cedric.martinet@utc.fr";
        String from = "nepasrepondre@utc.fr";
        String host = "smtp1.utc.fr";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, null);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Sujet");
            message.setContent("Contenu du mail", "text/html");
            Transport.send(message);
            System.out.println("Mail envoyé");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }

    public void sendMail(String to,String from,String host,String port,String subject, String content){
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        PasswordAuthentication pwAuth=new PasswordAuthentication(login,password);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }



}
