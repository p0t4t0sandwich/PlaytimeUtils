package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PlaytimeUtils {
    /**
     * Properties of the PlaytimeUtils class.
     * config: The config file
     * logger: The logger
     * singleton: The singleton instance of the PlaytimeUtils class
     * STARTED: Whether the PanelServerManager has been started
     */
    private static YamlDocument config;
    private final Object logger;
    private static PlaytimeUtils singleton = null;
    private boolean STARTED = false;
    public DataSource dataSource;

    /**
     * Constructor for the PlaytimeUtils class.
     * @param configPath The path to the config file
     * @param logger The logger
     */
    public PlaytimeUtils(String configPath, Object logger) {
        singleton = this;
        this.logger = logger;

        // Config
        try {
            config = YamlDocument.create(new File("./" + configPath + "/PlaytimeUtils", "config.yml"),
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("config.yml"))
            );
            config.reload();
        } catch (IOException e) {
            useLogger("Failed to load config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Getter for the singleton instance of the PanelServerManager class.
     * @return The singleton instance
     */
    public static PlaytimeUtils getInstance() {
        return singleton;
    }

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public void useLogger(String message) {
        if (logger instanceof java.util.logging.Logger) {
            ((java.util.logging.Logger) logger).info(message);
        } else if (logger instanceof org.slf4j.Logger) {
            ((org.slf4j.Logger) logger).info(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * Start PlaytimeUtils
     */
    public void start() {
        if (STARTED) {
            useLogger("PlaytimeUtils has already started!");
            return;
        }
        STARTED = true;
        String type = config.getString("storage.type");
        dataSource = DataSource.getDataSource(type, config);
        useLogger("PlaytimeUtils has been started!");
    }

    /**
     * Get the server name from the config
     * @return The server name
     */
    public String getServerName() {
        return config.getString("server.name");
    }
}
