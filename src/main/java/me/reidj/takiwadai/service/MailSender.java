package me.reidj.takiwadai.service;

import me.reidj.takiwadai.util.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import static me.reidj.takiwadai.util.Utils.RESOURCES;

public class MailSender {

    private final Properties systemProperties = System.getProperties();
    private final Properties properties = Utils.loadPropertyFile(RESOURCES + "mail.properties");

    public String send(String to, String name) {
        systemProperties.setProperty("mail.smtp.host", properties.getProperty("mail.host"));
        systemProperties.setProperty("mail.smtp.port", properties.getProperty("mail.port"));
        systemProperties.setProperty("mail.smtp.ssl.enable", "true");
        systemProperties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(
                systemProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty("mail.username"), properties.getProperty("mail.password"));
                    }
                }
        );
        return generateMessage(to, session, name);
    }

    private String generateMessage(String to, Session session, String name) {
        String newPassword = passwordGenerator();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.username")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Takiwadai | Восстановление пароля");
            message.setText("Здравствуйте " + name + "." +
                    "\n\n" +
                    "Для Вашей учетной записи " + to + " в приложении «Takiwadai» было запрошено " +
                    "восстановление пароля. Новый пароль: " + newPassword
            );
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPassword;
    }

    private static String passwordGenerator() {
        return new Random()
                .ints(10, 33, 122)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
