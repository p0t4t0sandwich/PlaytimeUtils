package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class SQLDataSource implements DataSource {
    /**
     * Class used to abstract the SQL data source.
     * config: The configuration for the SQL data source.
     * ds: The data source.
     */
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    /**
     * Constructor for the SQLDataSource class
     * @param sql_config The configuration for the SQL data source.
     */
    SQLDataSource(YamlDocument sql_config) {
        String host = sql_config.getString("storage.config.host");
        int port = Integer.parseInt(sql_config.getString("storage.config.port"));
        String database = sql_config.getString("storage.config.database");
        String username = sql_config.getString("storage.config.username");
        String password = sql_config.getString("storage.config.password");

        if (port == 0) {
            port = 3306;
        }
        String URI = "jdbc:mysql://" + host + ":" + port + "/" + database;
        config.setJdbcUrl(URI);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    /**
     * gets a connection from the data source
     * @return a connection to the database
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
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
                Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(SQL_QUERY);
                pst.executeUpdate();
                con.close();
            } catch (SQLException e) {
                // TODO: Need a better way to handle this
                try {
                    String SQL_QUERY = "ALTER TABLE playtime ADD " + server_name + "  INT DEFAULT 0;";
                    Connection con = getConnection();
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
        String player_uuid = player.getUUID();
        int streak = 0;
        try {
            Connection con = getConnection();

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
            long timeval = unixTime - last_streak - (unixTime % 86400);

            if (timeval > 86400) {
                // Reset Streak
                SQL_QUERY = "UPDATE player_data SET streak = 1 WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();

                // TODO: onStreakReset event

                SQL_QUERY = "UPDATE player_data SET last_streak = " + unixTime + " WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();
            } else if (timeval > 0 && timeval < 86400) {
                // ADD 1 to streak and give rewards
                SQL_QUERY = "UPDATE player_data SET streak = streak + 1 WHERE player_uuid = ?;";
                pst = con.prepareStatement(SQL_QUERY);
                pst.setString(1, player_uuid);
                pst.executeUpdate();

                // TODO: onStreakIncrement event

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
            Connection con = getConnection();

            // Update Last Login
            String SQL_QUERY = "UPDATE player_data SET last_online = " + unixTime + " WHERE player_uuid = ?;";
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, player.getUUID());
            pst.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
