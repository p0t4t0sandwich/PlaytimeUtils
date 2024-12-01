package ca.sperrer.p0t4t0sandwich.playtimeutils.common.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.neuralnexus.taterlib.lib.hikari.HikariConfig;
import dev.neuralnexus.taterlib.lib.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class MySQLDatabase extends Database<Connection> {
    /**
     * Class used to abstract the SQL data source.
     * config: The configuration for the SQL data source.
     * ds: The data source.
     */
    private static final HikariConfig dbconfig = new HikariConfig();
    private static HikariDataSource ds;

    /**
     * Constructor for the MySQLDataSource class
     * @param config The configuration for the MySQL data source.
     */
    public MySQLDatabase(YamlDocument config) {
        super("mysql", null, null);

        String host = config.getString("storage.config.host");
        int port = Integer.parseInt(config.getString("storage.config.port"));
        String database = config.getString("storage.config.database");
        String username = config.getString("storage.config.username");
        String password = config.getString("storage.config.password");

        if (port == 0) {
            port = 3306;
        }
        String URI = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
        dbconfig.setJdbcUrl(URI);
        dbconfig.setUsername(username);
        dbconfig.setPassword(password);
        dbconfig.setDriverClassName("dev.neuralnexus.taterlib.lib.mysql.cj.jdbc.Driver");
        dbconfig.addDataSourceProperty("cachePrepStmts", "true");
        dbconfig.addDataSourceProperty("prepStmtCacheSize", "250");
        dbconfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(dbconfig);

        setConnection(getConnection());
        setDatabase(database);
    }

    @Override
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
