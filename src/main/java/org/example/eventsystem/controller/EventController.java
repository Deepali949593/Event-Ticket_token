package org.example.eventsystem.controller;

import io.javalin.http.Context;
import org.example.eventsystem.dao.EventDAO;
import org.example.eventsystem.model.Event;
import org.example.eventsystem.service.EmailService;
import org.example.eventsystem.service.QRCodeService;

import java.util.List;
import java.util.UUID;

public class EventController {

    // List all events as JSON
    public static void listEvents(Context ctx) {
        List<Event> events = EventDAO.getAllEvents();
        ctx.json(events);
    }

    // Book a ticket for an event
    public static void bookTicket(Context ctx) {
        String eventId = ctx.pathParam("eventId");
        String email = ctx.formParam("email");

        if (eventId == null || email == null) {
            ctx.status(400).result("Missing event ID or email");
            return;
        }

        // Fetch the event by ID
        Event event = EventDAO.getEventById(eventId);
        if (event == null) {
            ctx.status(404).result("Event not found");
            return;
        }

        // Attempt to reduce available tokens by 1
        boolean tokenReduced = EventDAO.bookToken(eventId);
        if (!tokenReduced) {
            ctx.status(400).result("Sorry, no tokens available for this event");
            return;
        }

        // Generate a unique booking token (8 chars uppercase)
        String bookingToken = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Generate QR code image for booking token, saved with user's email for uniqueness
        String qrPath = QRCodeService.generateQRCode(bookingToken, email);

        if (qrPath == null) {
            ctx.status(500).result("Error generating QR code");
            return;
        }

        // Send email with QR code and event info
        EmailService.sendEmailWithQR(email, event.getName(), qrPath);

        ctx.result("✅ Ticket booked successfully! Your token: " + bookingToken + ". QR code sent to email: " + email);
    }
}
