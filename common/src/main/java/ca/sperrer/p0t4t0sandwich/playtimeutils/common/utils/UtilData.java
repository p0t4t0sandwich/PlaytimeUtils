package ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

public abstract class UtilData {
    final Database db;

    public UtilData(Database database) {
        this.db = database;
    }

    public abstract int getPlaytime(PlayerInstance player);
}
