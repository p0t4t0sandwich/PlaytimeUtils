package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBDataSource implements DataSource {
    /**
     * Class used to abstract the MongoDB data source.
     * mongoClient: The MongoDB client.
     */
    private final MongoClient mongoClient;
    private final String database;

    /**
     * Constructor for the MongoDBDataSource class
     * @param config The configuration for the MongoDB data source.
     */
    MongoDBDataSource(YamlDocument config) {
        String host = config.getString("storage.config.host");
        int port = Integer.parseInt(config.getString("storage.config.port"));
        String database = config.getString("storage.config.database");
        String username = config.getString("storage.config.username");
        String password = config.getString("storage.config.password");

        if (port == 0) {
            port = 27017;
        }
        String URI = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database + "?authSource=admin";
        mongoClient = MongoClients.create(URI);
        this.database = database;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updatePlaytime(ArrayList<PlayerInstance> players) {
        if (players.size() == 0) {
            return;
        }
        System.out.println("Updating playtime for " + players.size() + " players");
        for (PlayerInstance player : players) {
            String server_name = player.getCurrentServer();
            try {
                MongoDatabase db = mongoClient.getDatabase(database);
                MongoCollection<Document> collection = db.getCollection("player_data");
                Document query = new Document("player_uuid", player.getUUID());

                // Get player data
                Document player_data = collection.find(query).first();
                if (player_data == null) {
                    // Player data doesn't exist, create it
                    Document new_player_data = new Document();
                    new_player_data.append("player_name", player.getName());
                    new_player_data.append("player_uuid", player.getUUID());
                    new_player_data.append("playtime." + server_name, 0);
                    collection.insertOne(new_player_data);
                    continue;
                }

                // Get playtime for server
                int playtime = 0;
                Document playtime_data = (Document) player_data.get("playtime");
                if (playtime_data != null && playtime_data.get(server_name) != null) {
                    playtime = ((Document) player_data.get("playtime")).getInteger(server_name);
                }

                // Update playtime
                Document update = new Document("playtime." + server_name, playtime + 1);
                collection.updateOne(query, new Document("$set", update));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int playerLoginData(PlayerInstance player) {
        long unixTime = System.currentTimeMillis() / 1000L;
        String player_uuid = player.getUUID();
        int streak = 0;
        // `player_uuid`, `player_name`, first_login, last_online, last_streak, streak
        try {
            // Get player data
            MongoDatabase db = mongoClient.getDatabase(database);
            MongoCollection<Document> collection = db.getCollection("player_data");
            Document query = new Document("player_uuid", player_uuid);
            Document player_data = collection.find(query).first();

            // If player data doesn't exist, create it
            if (player_data == null) {
                Document new_player_data = new Document();
                new_player_data.append("player_name", player.getName());
                new_player_data.append("player_uuid", player_uuid);
                new_player_data.append("first_login", unixTime);
                new_player_data.append("last_online", unixTime);
                new_player_data.append("last_streak", unixTime);
                new_player_data.append("streak", 1);
                collection.insertOne(new_player_data);
                return 1;
            }

            // Get last streak
            long last_streak = Long.valueOf(player_data.getLong("last_streak"));
            long timeval = unixTime - last_streak - (unixTime % 86400);
            streak = player_data.getInteger("streak");

            // If player hasn't logged in for 24 hours, reset streak
            Document update = new Document("last_online", unixTime);
            if (timeval > 86400) {
                // Reset Streak
                streak = 1;
                update = update.append("last_streak", unixTime)
                        .append("streak", streak);

                // TODO: onStreakReset event
            } else if (timeval > 0 && timeval < 86400) {
                // Increment Streak
                streak++;
                update = update.append("last_streak", unixTime)
                        .append("streak", streak);

                // TODO: onStreakIncrement event
            }
            collection.updateOne(query, new Document("$set", update));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streak;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playerLogoutData(PlayerInstance player) {
        long unixTime = System.currentTimeMillis() / 1000L;
        String player_uuid = player.getUUID();
        try {
            // Update Last Login
            MongoDatabase db = mongoClient.getDatabase(database);
            MongoCollection<Document> collection = db.getCollection("player_data");
            Document query = new Document("player_uuid", player_uuid);
            Document update = new Document("last_online", unixTime);
            collection.updateOne(query, new Document("$set", update));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
