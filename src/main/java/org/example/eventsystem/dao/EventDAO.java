package org.example.eventsystem.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.eventsystem.db.DBConnection;
import org.example.eventsystem.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private static final MongoCollection<Document> collection = DBConnection.getDatabase().getCollection("events");

    // ✅ Get all events from MongoDB
    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        for (Document doc : collection.find()) {
            events.add(new Event(
                    doc.getObjectId("_id").toString(),
                    doc.getString("name"),
                    doc.getString("date"),
                    doc.getInteger("availableTokens", 0),
                    doc.getString("description"),
                    doc.getString("imageUrl")
            ));
        }
        return events;
    }

    // ✅ Get single event by ID
    public static Event getEventById(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc != null) {
            return new Event(
                    doc.getObjectId("_id").toString(),
                    doc.getString("name"),
                    doc.getString("date"),
                    doc.getInteger("availableTokens", 0),
                    doc.getString("description"),
                    doc.getString("imageUrl")
            );
        }
        return null;
    }

    // ✅ Reduce token count (book a ticket)
    public static boolean bookToken(String eventId) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(eventId))).first();
        if (doc != null && doc.getInteger("availableTokens", 0) > 0) {
            collection.updateOne(
                    Filters.eq("_id", new ObjectId(eventId)),
                    Updates.inc("availableTokens", -1)
            );
            return true;
        }
        return false;
    }

    // ✅ Insert sample events into the DB (Run only once)
    public static void insertSampleEvents() {
        List<Document> sampleEvents = List.of(
                new Document("name", "Music Concert")
                        .append("date", "2025-07-20")
                        .append("availableTokens", 10)
                        .append("description", "Enjoy a night of classical and modern music.")
                        .append("imageUrl", "/images/music_concert.jpg"),

                new Document("name", "Tech Conference")
                        .append("date", "2025-07-22")
                        .append("availableTokens", 5)
                        .append("description", "Join leading experts to discuss the latest in tech.")
                        .append("imageUrl", "/images/tech_conference.jpg"),

                new Document("name", "Art Workshop")
                        .append("date", "2025-07-25")
                        .append("availableTokens", 7)
                        .append("description", "Hands-on workshop with renowned artists.")
                        .append("imageUrl", "/images/art_workshop.jpg")
        );

        collection.insertMany(sampleEvents);
    }
}
