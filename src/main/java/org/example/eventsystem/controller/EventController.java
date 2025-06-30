package org.example.eventsystem.controller;

import io.javalin.http.Context;
import org.example.eventsystem.dao.EventDAO;
import org.example.eventsystem.model.Event;

import java.util.List;

public class EventController {

    // Returns JSON list of all events
    public static void listEvents(Context ctx) {
        List<Event> events = EventDAO.getAllEvents();
        ctx.json(events);
    }

    // Book ticket: expects form param "email"
    public static void bookTicket(Context ctx) {
        String eventId = ctx.pathParam("eventId");
        String email = ctx.formParam("email");

        if (email == null || email.isEmpty() || eventId == null || eventId.isEmpty()) {
            ctx.status(400).result("Missing event ID or email");
            return;
        }

        // TODO: add your booking logic here (e.g., save to DB, send email, etc.)
        ctx.result("Ticket booked for event " + eventId + " by " + email);
    }
}
