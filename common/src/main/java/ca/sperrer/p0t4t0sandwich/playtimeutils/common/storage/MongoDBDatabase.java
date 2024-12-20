package ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoClient;
import dev.neuralnexus.taterlib.lib.mongodb.client.MongoClients;

public class MongoDBDatabase extends Database<MongoClient> {
    /**
     * Constructor for the MongoDBDataSource class
     * @param config The configuration for the MongoDB data source.
     */
    public MongoDBDatabase(YamlDocument config) {
        super("mongodb", null, null);
        String host = config.getString("storage.config.host");
        int port = Integer.parseInt(config.getString("storage.config.port"));
        String database = config.getString("storage.config.database");
        String username = config.getString("storage.config.username");
        String password = config.getString("storage.config.password");

        if (port == 0) {
            port = 27017;
        }
        String URI = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database + "?authSource=admin";
        MongoClient mongoClient = MongoClients.create(URI);

        setConnection(mongoClient);
        setDatabase(database);
    }
}
