package ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;
import dev.neuralnexus.taterlib.lib.bson.Document;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoClient;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoCollection;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoDatabase;

public class MongoDBUtilData extends UtilData {
    public MongoDBUtilData(Database<MongoClient> database) {
        super(database);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getPlaytime(PlayerInstance player) {
        String player_uuid = player.getUUID().toString();

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
                collection.insertOne(new_player_data);
                return 1;
            }

            // Get playtime for all servers
            int playtime = 0;
            Document playtime_data = (Document) player_data.get("playtime");
            if (playtime_data != null) {
                for (String server : playtime_data.keySet()) {
                    playtime += ((Document) player_data.get("playtime")).getInteger(server);
                }
            }

            return playtime;

        } catch (Exception e) {
            System.out.println("Error getting playtime for " + player.getName());
            return 0;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getStreak(PlayerInstance player) {
        String player_uuid = player.getUUID().toString();

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
                collection.insertOne(new_player_data);
                return 1;
            }

            return player_data.getInteger("streak");

        } catch (Exception e) {
            System.out.println("Error getting streak for " + player.getName());
            return 0;
        }
    }
}
