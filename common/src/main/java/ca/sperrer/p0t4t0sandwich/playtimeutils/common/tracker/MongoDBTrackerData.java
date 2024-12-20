package ca.sperrer.p0t4t0sandwich.playtimeutils.common.tracker;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;
import dev.neuralnexus.taterlib.lib.bson.Document;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoClient;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoCollection;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MongoDBTrackerData extends TrackerData {
    public MongoDBTrackerData(Database<MongoClient> database) {
        super(database);
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
            String player_uuid = player.getUUID().toString();
            try {
                MongoClient mongoClient = (MongoClient) this.db.getConnection();
                String database = this.db.getDatabase();

                MongoDatabase db = mongoClient.getDatabase(database);
                MongoCollection<Document> collection = db.getCollection("player_data");
                Document query = new Document("player_uuid", player_uuid);

                // Get player data
                Document player_data = collection.find(query).first();
                if (player_data == null) {
                    // Player data doesn't exist, create it
                    Document new_player_data = new Document();
                    new_player_data.append("player_name", player.getName());
                    new_player_data.append("player_uuid", player_uuid);
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
        String player_uuid = player.getUUID().toString();
        int streak = 0;

        try {
            MongoClient mongoClient = (MongoClient) this.db.getConnection();
            String database = this.db.getDatabase();

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
            long last_streak;
            try {
                last_streak = Long.valueOf(player_data.getInteger("last_streak"));
            } catch (Exception e) {
                last_streak = player_data.getLong("last_streak");
            }

            long timeValue = unixTime - last_streak - (unixTime % 86400);
            streak = player_data.getInteger("streak");

            // If player hasn't logged in for 24 hours, reset streak
            Document update = new Document("last_online", unixTime);
            if (timeValue > 86400) {
                // Reset Streak
                streak = 1;
                update = update.append("last_streak", unixTime)
                        .append("streak", streak);

            } else if (timeValue > 0 && timeValue < 86400) {
                // Increment Streak
                streak++;
                update = update.append("last_streak", unixTime)
                        .append("streak", streak);
            } else {
                // No change
                streak = -1;
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
        String player_uuid = player.getUUID().toString();
        try {
            MongoClient mongoClient = (MongoClient) this.db.getConnection();
            String database = this.db.getDatabase();

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
