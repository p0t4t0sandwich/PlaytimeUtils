package ca.sperrer.p0t4t0sandwich.playtimeutils.common.ranks;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;
import com.mongodb.client.MongoClient;

public class MongoDBRankData extends RankData{
    public MongoDBRankData(Database<MongoClient> database) {
        super(database);
    }
}
