package org.example.eventsystem.model;

public class Event {
    private String id;
    private String name;
    private String date;

    public Event() {}  // default constructor required for JSON deserialization

    public Event(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
