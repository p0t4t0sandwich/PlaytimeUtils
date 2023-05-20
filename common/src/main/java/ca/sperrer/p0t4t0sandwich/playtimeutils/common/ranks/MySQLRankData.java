package ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

import java.sql.Connection;

public class MySQLRankData extends RankData{
    public MySQLRankData(Database<Connection> database) {
        super(database);
    }
}
