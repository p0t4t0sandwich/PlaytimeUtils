package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

import java.util.ArrayList;
import java.util.Map;

public interface DataSource {
    static DataSource getDataSource(String type, Map<String, Object> config) {
        switch (type) {
            case "sql":
                return new SQLDataSource(config);
            case "mongo":
                return new MongoDBDataSource(config);
            default:
                return null;
        }
    }

    /**
     * Update the playtime of all players on the server
     * @param players The list of players to update
     */
    void updatePlaytime(ArrayList<PlayerInstance> players);

    /**
     * Update a player's last login time, and update their streak if necessary
     * @param player The player to update
     */
    int playerLoginData(PlayerInstance player);

    /**
     * Update a player's last logout time
     * @param player The player to update
     */
    void playerLogoutData(PlayerInstance player);
}