package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import com.velocitypowered.api.proxy.Player;

import java.util.ArrayList;

public class VelocityUtils {
    /**
     * Maps a Bukkit Player to a PlayerInstance.
     * @param player Bukkit Player
     * @return PlayerInstance
     */
    static PlayerInstance mapPlayer(Player player) {
        return new PlayerInstance(player.getUniqueId().toString(), player.getUsername(), player.getCurrentServer().toString());
    }

    /**
     * Maps an array of Bukkit Players to an ArrayList of PlayerInstances.
     * @param players Array of Bukkit Players
     * @return ArrayList of PlayerInstances
     */
    static ArrayList<PlayerInstance> mapPlayers(Player[] players) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (Player player : players) {
            playerInstances.add(mapPlayer(player));
        }
        return playerInstances;
    }
}
