package org.example.eventsystem.controller;

import io.javalin.http.Context;
import org.example.eventsystem.dao.UserDAO;
import org.example.eventsystem.model.User;
import org.example.eventsystem.service.EmailService;
import org.example.eventsystem.service.QRCodeService;

import java.security.SecureRandom;

public class AuthController {

    // Random password generator
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    private static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // ✅ Registration logic
    public static void register(Context ctx) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");

        if (name == null || email == null) {
            ctx.status(400).result("Missing required fields");
            return;
        }

        // Generate random password
        String password = generateRandomPassword(8);

        // Save user
        User user = new User(name, email, password);
        UserDAO.saveUser(user);

        // Generate QR code image with the password (or you can encode any info you want)
        String qrPath = QRCodeService.generateQRCode(password, email);

        // Send email with password + QR code attached
        EmailService.sendEmailWithPasswordAndQR(email, password, qrPath);

        // Redirect to login page after registration
        ctx.redirect("/login.html");
    }



    // ✅ Login logic (form-based)
    public static void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (email == null || password == null) {
            ctx.status(400).result("Email and password required");
            return;
        }

        User user = UserDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Successful login: redirect to events page
            ctx.redirect("/events.html");
        } else {
            ctx.status(401).result("Invalid credentials");
        }
    }

}