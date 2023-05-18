package ca.sperrer.p0t4t0sandwich.playtimeutils.common.playtime;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLPlaytimeData extends PlaytimeData {
    public MySQLPlaytimeData(Database<Connection> database) {
        super(database);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updatePlaytime(ArrayList<PlayerInstance> players) {
        if (players.size() == 0) {
            return;
        }
        System.out.println("Updating playtime for " + players.size() + " players");
        for (PlayerInstance player : players) {
            String server_name = player.getCurrentServer();
            try {
                String SQL_QUERY = "UPDATE playtime SET " + server_name + " = " + server_name + " + 1 WHERE player_id=(SELECT player_id FROM player_data WHERE player_uuid='" + player.getUUID() + "');";
                Connection con = (Connection) this.db.getConnection();
                PreparedStatement pst = con.prepareStatement(SQL_QUERY);
                pst.executeUpdate();
                con.close();
            } catch (SQLException e) {
                // TODO: Need a better way to handle this
                try {
                    String SQL_QUERY = "ALTER TABLE playtime ADD " + server_name + "  INT DEFAULT 0;";
                    Connection con = (Connection) this.db.getConnection();
                    PreparedStatement pst = con.prepareStatement(SQL_QUERY);
                    pst.executeUpdate();
                    con.close();
                } catch (SQLException f) {
                    f.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int playerLoginData(PlayerInstance player) {
        long unixTime = System.currentTimeMillis() / 1000L;
        String player_uuid = player.getUUID().toString();
        int streak = 0;
        try {
            Connection con = (Connection) this.db.getConnection();

            String SQL_QUERY = "INSERT INTO `player_data` (`player_uuid`, `player_name`, first_login, last_online, last_streak, streak) SELECT ?, ?, " + unixTime + ", " + unixTime + ", " + unixTime + ", 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM `player_data` WHERE `player_uuid`=? LIMIT 1)";
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player_uuid);
            pst.setString(2, player.getName());
            pst.setString(3, player_uuid);
            pst.executeUpdate();

            SQL_QUERY = "INSERT INTO `playtime` (player_id) SELECT (SELECT player_id FROM `player_data` WHERE `player_uuid`=?) FROM DUAL WHERE NOT EXISTS (SELECT * FROM `playtime` WHERE `player_id`=(SELECT player_id FROM `player_data` WHERE `player_uuid`=?) LIMIT 1)";
            pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player_uuid);
            pst.setString(2, player_uuid);
            pst.executeUpdate();

            SQL_QUERY = "SELECT * FROM player_data WHERE player_uuid = '" + player_uuid + "';";
            pst = con.prepareStatement(SQL_QUERY);
            ResultSet rs = pst.executeQuery(SQL_QUERY);
            rs.next();

            long last_streak = rs.getLong("last_streak");
            long timeValue = unixTime - last_streak - (unixTime % 86400);

            if (timeValue > 86400) {
                // Reset Streak
                SQL_QUERY = "UPDATE player_data SET streak = 1 WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();

                SQL_QUERY = "UPDATE player_data SET last_streak = " + unixTime + " WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();
            } else if (timeValue > 0 && timeValue < 86400) {
                // ADD 1 to streak and give rewards
                SQL_QUERY = "UPDATE player_data SET streak = streak + 1 WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();

                SQL_QUERY = "UPDATE player_data SET last_streak = " + unixTime + " WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();

                // Update return variable
                SQL_QUERY = "SELECT streak FROM player_data WHERE player_uuid = '" + player_uuid + "';";
                pst = con.prepareStatement(SQL_QUERY);
                rs = pst.executeQuery(SQL_QUERY);
                rs.next();
                streak = rs.getInt("streak");
            } else {
                // No change
                streak = -1;
            }

            // Update Last Login
            SQL_QUERY = "UPDATE player_data SET last_online = " + unixTime + " WHERE player_uuid = ?;";
            pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player_uuid);
            pst.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return streak;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playerLogoutData(PlayerInstance player) {
        long unixTime = System.currentTimeMillis() / 1000L;
        try {
            Connection con = (Connection) this.db.getConnection();

            // Update Last Login
            String SQL_QUERY = "UPDATE player_data SET last_online = " + unixTime + " WHERE player_uuid = ?;";
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player.getUUID().toString());
            pst.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
