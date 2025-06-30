package org.example.eventsystem.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailService {

    private static final String FROM_EMAIL = "24mscs29@kristujayanti.com";       // Replace with your Gmail
    private static final String APP_PASSWORD = "ldrlnrwertezgpei";        // Replace with your Gmail app password

    public static void sendEmailWithPasswordAndQR(String to, String password, String qrPath) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM_EMAIL));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("Your Registration Password and QR Code");

            Multipart multipart = new MimeMultipart();

            // Text part with password info
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Thank you for registering.\nYour generated password is: " + password);

            // Attachment part with QR code image
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(qrPath));

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            Transport.send(msg);
            System.out.println("Email with QR sent to " + to);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email with QR to " + to);
        }
    }

    // Optionally, keep this method if you want a simpler email without QR
    public static void sendEmail(String to, String password) {
        // Your existing simple email send method here
    }
}
