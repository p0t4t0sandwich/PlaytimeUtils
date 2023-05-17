package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BukkitUtils {
    /**
     * Maps a Bukkit Player to a PlayerInstance.
     * @param player Bukkit Player
     * @param serverName Name of the server
     * @return PlayerInstance
     */
    public static PlayerInstance mapPlayer(Player player, String serverName) {
        return new PlayerInstance(player.getUniqueId().toString(), player.getName(), serverName);
    }

    /**
     * Maps an array of Bukkit Players to an ArrayList of PlayerInstances.
     * @param players Array of Bukkit Players
     * @param serverName Name of the server
     * @return ArrayList of PlayerInstances
     */
    public static ArrayList<PlayerInstance> mapPlayers(Player[] players, String serverName) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (Player player : players) {
            playerInstances.add(mapPlayer(player, serverName));
        }
        return playerInstances;
    }
}
