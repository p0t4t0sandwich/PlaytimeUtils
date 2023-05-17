package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.ArrayList;
import java.util.Optional;

public class VelocityUtils {
    /**
     * Maps a Bukkit Player to a PlayerInstance.
     * @param player Bukkit Player
     * @return PlayerInstance
     */
    public static PlayerInstance mapPlayer(Player player) {
        Optional<ServerConnection> option = player.getCurrentServer();
        String serverName = option.map(serverConnection -> serverConnection.getServerInfo().getName()).orElse("null");
        return new PlayerInstance(player.getUsername(), player.getUniqueId().toString(), serverName);
    }

    /**
     * Maps an array of Bukkit Players to an ArrayList of PlayerInstances.
     * @param players Array of Bukkit Players
     * @return ArrayList of PlayerInstances
     */
    public static ArrayList<PlayerInstance> mapPlayers(Player[] players) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (Player player : players) {
            playerInstances.add(mapPlayer(player));
        }
        return playerInstances;
    }
}
