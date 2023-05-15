package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.util.ArrayList;

public interface DataSource {
    static DataSource getDataSource(String type, YamlDocument config) {
        switch (type) {
            case "mysql":
                return new SQLDataSource(config);
            case "mongodb":
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