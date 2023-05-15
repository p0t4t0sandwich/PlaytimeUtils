package ca.sperrer.p0t4t0sandwich.playtimeutils.common;

public class PlayerInstance {
    /**
     * Class used to abstract the player data to be used in the DataSource class.
     * playerName: The player's name.
     * playerUUID: The player's UUID.
     * currentServer: The server the player is currently on.
     */
    private final String playerName;
    private final String playerUUID;
    private final String currentServer;

    /**
     * Constructor for the PlayerInstance class.
     * @param playerName The player's name.
     * @param playerUUID The player's UUID.
     * @param currentServer The server the player is currently on.
     */
    public PlayerInstance(String playerName, String playerUUID, String currentServer) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.currentServer = currentServer;
    }

    /**
     * Get the player's name.
     * @return The player's name.
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Get the player's UUID.
     * @return The player's UUID.
     */
    public String getUUID() {
        return this.playerUUID;
    }

    /**
     * Get the server the player is currently on.
     * @return The server the player is currently on.
     */
    public String getCurrentServer() {
        return this.currentServer;
    }
}
