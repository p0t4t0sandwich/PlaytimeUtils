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

    /**
     * @inheritDoc
     */
    @Override
    public int getStreak(PlayerInstance player) {
        String player_uuid = player.getUUID().toString();
        Connection con;
        int streak = 0;

        try {
            con = (Connection) db.getConnection();

            // Get streak from database
            String SQL_QUERY = "SELECT * FROM streak WHERE `player_id`=(SELECT player_id FROM `player_data` WHERE `player_uuid`='" + player_uuid + "')";
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player_uuid);
            ResultSet rs = pst.executeQuery(SQL_QUERY);
            if (rs.next()) {
                streak = rs.getInt("streak");
            } else {
                // If streak doesn't exist, create it
                SQL_QUERY = "INSERT INTO streak (player_id, streak) VALUES ((SELECT player_id FROM `player_data` WHERE `player_uuid`='" + player_uuid + "'), 0)";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();
            }

            // Close connections
            rs.close();
            con.close();
            return streak;

        } catch (SQLException e) {
            System.out.println(e);
            return streak;
        }

    }
}
