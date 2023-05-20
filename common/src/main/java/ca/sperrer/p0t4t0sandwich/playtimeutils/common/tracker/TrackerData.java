package ca.sperrer.p0t4t0sandwich.playtimeutils.common.tracker;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

import java.util.ArrayList;

public abstract class TrackerData {
    final Database db;

    public TrackerData(Database database) {
        this.db = database;
    }

    /**
     * Update the playtime of all players on the server
     * @param players The list of players to update
     */
    public abstract void updatePlaytime(ArrayList<PlayerInstance> players);

    /**
     * Update a player's last login time, and update their streak if necessary
     * @param player The player to update
     * @return The player's current streak
     */
    public abstract int playerLoginData(PlayerInstance player);

    /**
     * Update a player's last logout time
     * @param player The player to update
     */
    public abstract void playerLogoutData(PlayerInstance player);
}
