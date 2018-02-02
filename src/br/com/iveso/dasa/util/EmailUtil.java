package br.com.iveso.dasa.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	private Properties props;
	
	public EmailUtil() {
		this.props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", "465");
	}

	public void enviar() {
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("email@email.com.br", "password");
			}
		});
		
		session.setDebug(true);
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("email@email.com.br"));
			
			Address[] toUser = InternetAddress.parse("email@email.com.br");
			
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Enviando email com JavaMail");
			message.setContent("<html>"
					+ "<h1 style=textAlign:center>Recibos Emitidos</h1>"
					+ "<p style=textAlign:justificad>Email envaido com sucesso!</p>"
					+ "</html>", "text/html");
			
			Transport.send(message);
			
		} catch(MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
