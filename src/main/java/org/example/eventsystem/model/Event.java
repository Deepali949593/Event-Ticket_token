package org.example.eventsystem.model;

public class Event {
    private String id;
    private String name;
    private String date;
    private int availableTokens;      // existing field

    private String description;       // new field
    private String imageUrl;          // new field (URL or local image path)

    public Event() {} // Default constructor

    // Updated constructor including new fields
    public Event(String id, String name, String date, int availableTokens, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.availableTokens = availableTokens;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and setters for all fields

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getAvailableTokens() {
        return availableTokens;
    }
    public void setAvailableTokens(int availableTokens) {
        this.availableTokens = availableTokens;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
