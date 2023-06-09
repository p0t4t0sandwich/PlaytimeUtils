package ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks.MongoDBRankData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks.MySQLRankData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks.RankData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.tracker.MongoDBTrackerData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.tracker.MySQLTrackerData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.tracker.TrackerData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils.MongoDBUtilData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils.MySQLUtilData;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils.UtilData;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.util.Arrays;


public class DataSource {
    /**
     * Get the database
     * @param type The type of database
     * @param config The config file
     * @return The database
     */
    public static Database getDataSource(String type, YamlDocument config) {
        try {
            switch (type) {
                case "mysql":
                    return new MySQLDatabase(config);
                case "mongodb":
                    return new MongoDBDatabase(config);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Get the playtime data class
     * @param type The type of database
     * @param database The database
     * @return The playtime data class
     */
    public static TrackerData getTrackerData(Database database) {
        try {
            switch (database.getType()) {
                case "mysql":
                    return new MySQLTrackerData(database);
                case "mongodb":
                    return new MongoDBTrackerData(database);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public static RankData getRankData(Database database) {
        try {
            switch (database.getType()) {
                case "mysql":
                    return new MySQLRankData(database);
                case "mongodb":
                    return new MongoDBRankData(database);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public static UtilData getUtilData(Database database) {
        try {
            switch (database.getType()) {
                case "mysql":
                    return new MySQLUtilData(database);
                case "mongodb":
                    return new MongoDBUtilData(database);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}