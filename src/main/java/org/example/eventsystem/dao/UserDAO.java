package org.example.eventsystem.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.eventsystem.db.DBConnection;
import org.example.eventsystem.model.User;

public class UserDAO {
    private static final MongoCollection<Document> collection = DBConnection.getDatabase().getCollection("users");

    public static void saveUser(User user) {
        Document doc = new Document("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        collection.insertOne(doc);
    }

    public static User getUserByEmail(String email) {
        Document doc = collection.find(new Document("email", email)).first();
        if (doc != null) {
            return new User(doc.getString("name"), doc.getString("email"), doc.getString("password"));
        }
        return null;
    }
}
