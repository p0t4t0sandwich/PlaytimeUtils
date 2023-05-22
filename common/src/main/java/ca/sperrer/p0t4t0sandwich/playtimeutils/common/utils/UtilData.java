package ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

public abstract class UtilData {
    final Database db;

    public UtilData(Database database) {
        this.db = database;
    }

    /**
     * Get the playtime of a player
     * @param player The player to get the playtime of
     * @return The playtime of the player
     */
    public abstract int getPlaytime(PlayerInstance player);

    /**
     * Get the streak of a player
     * @param player The player to get the streak of
     * @return The streak of the player
     */
    public abstract int getStreak(PlayerInstance player);
}
