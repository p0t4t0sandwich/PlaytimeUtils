package ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public abstract class RankData {
    final Database db;
    private LuckPerms luckPerms;

    public RankData(Database database) {
        this.db = database;
        this.luckPerms = LuckPermsProvider.get();
    }


}
