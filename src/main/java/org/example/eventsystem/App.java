package org.example.eventsystem;

import io.javalin.Javalin;
import org.example.eventsystem.controller.AuthController;
import org.example.eventsystem.controller.EventController;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        }).start(7070);

        app.post("/register", AuthController::register);
        app.post("/login", AuthController::login);
        app.get("/events", EventController::listEvents);
        app.post("/book/{eventId}", EventController::bookTicket);

        System.out.println("✅ Server running at http://localhost:7070/register.html");

        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:7070/register.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
