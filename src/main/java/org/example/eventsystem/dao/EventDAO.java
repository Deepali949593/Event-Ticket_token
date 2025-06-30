package org.example.eventsystem.dao;

import org.example.eventsystem.model.Event;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("1", "Music Concert", "2023-12-01"));
        events.add(new Event("2", "Art Exhibition", "2023-12-05"));
        events.add(new Event("3", "Tech Talk", "2023-12-10"));
        return events;
    }
}