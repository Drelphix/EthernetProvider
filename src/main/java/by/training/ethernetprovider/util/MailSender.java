package by.training.ethernetprovider.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class MailSender {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String RESOURCE = "properties/mail.properties";
    private static final Properties MAIL_PROPERTIES = PropertyLoader.getInstance().load(RESOURCE);
    private final Session session;
    private final String from;
    private static final MailSender INSTANCE = new MailSender();

    public static MailSender getInstance(){
        return INSTANCE;
    }

    private MailSender(){
        String username = MAIL_PROPERTIES.getProperty("mail.user.name");
        String password = MAIL_PROPERTIES.getProperty("mail.user.password");
        from = MAIL_PROPERTIES.getProperty("from");
        session = Session.getInstance(MAIL_PROPERTIES,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
    public void sendMessage(String to,String text, String title){
        try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(title);
                message.setContent(text, "text/html");
                Transport.send(message);
            } catch (MessagingException e) {
                LOGGER.error("Can't send mail: ", e);
            }
    }

}
