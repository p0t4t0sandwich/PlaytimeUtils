package ca.sperrer.p0t4t0sandwich.playtimeutils.common.utils;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

import java.sql.*;
import java.util.Objects;

public class MySQLUtilData extends UtilData {
    public MySQLUtilData(Database<Connection> database) {
        super(database);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getPlaytime(PlayerInstance player) {
        String player_uuid = player.getUUID().toString();
        Connection con;
        int playtime = 0;
        try {
            con = (Connection) db.getConnection();

            // Get playtime from database
            String SQL_QUERY = "SELECT * FROM playtime WHERE `player_id`=(SELECT player_id FROM `player_data` WHERE `player_uuid`='" + player_uuid + "')";
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player_uuid);
            ResultSet rs = pst.executeQuery(SQL_QUERY);
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();

                // Add all playtime columns together -- except playtime_id and player_id
                for (int i = 1; i <= count; i++) {
                    String column = metaData.getColumnLabel(i);
                    if (!Objects.equals(column, "playtime_id") && !Objects.equals(column, "player_id") && column != null) {
                        playtime += rs.getInt(column);
                    }
                }
            }
            // Close connections
            rs.close();
            con.close();
            return playtime;

        } catch (SQLException e) {
            e.printStackTrace();
            return playtime;
        }
    }
}
