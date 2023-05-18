package ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.playtime.MongoDBPlaytimeData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.playtime.MySQLPlaytimeData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.playtime.PlaytimeData;
import dev.dejvokep.boostedyaml.YamlDocument;


public interface DataSource {
    /**
     * Get the database
     * @param type The type of database
     * @param config The config file
     * @return The database
     */
    static Database getDataSource(String type, YamlDocument config) {
        switch (type) {
            case "mysql":
                return new MySQLDatabase(config);
            case "mongodb":
                return new MongoDBDatabase(config);
            default:
                return null;
        }
    }

    /**
     * Get the playtime data class
     * @param type The type of database
     * @param database The database
     * @return The playtime data class
     */
    static PlaytimeData getPlaytimeData(String type, Database database) {
        switch (type) {
            case "mysql":
                return new MySQLPlaytimeData(database);
            case "mongodb":
                return new MongoDBPlaytimeData(database);
            default:
                return null;
        }
    }
}