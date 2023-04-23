package me.reidj.takiwadai.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class MailSender {

    // TODO Переехать в properties
    private static final String FROM = "reidjjava@gmail.com";
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "465";

    private final Properties properties = System.getProperties();

    public void send(String to) {
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT);
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM, "auwwmgbrsksgrrdb");
                    }
                }
        );
        generateMessage(to, session);
    }

    private static void generateMessage(String to, Session session) {
        try {
            String newPassword = passwordGenerator();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Takiwadai | Восстановление пароля");
            message.setText("Здравствуйте." +
                    "\n\n" +
                    "Для Вашей учетной записи " + to + " в приложении «Takiwadai» было запрошено " +
                    "восстановление пароля. Новый пароль: " + newPassword
            );
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String passwordGenerator() {
        return new Random()
                .ints(10, 33, 122)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
