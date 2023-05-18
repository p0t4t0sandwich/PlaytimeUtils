package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class BungeeUtils {
    /**
     * Maps a Bungee Player to a PlayerInstance.
     * @param player Bungee Player
     * @return PlayerInstance
     */
    public static PlayerInstance mapPlayer(ProxiedPlayer player) {
        String serverName = player.getServer() == null ? "null" : player.getServer().getInfo().getName();
        return new PlayerInstance(player.getName(), player.getUniqueId(), serverName);
    }

    /**
     * Maps an array of Bungee Players to an ArrayList of PlayerInstances.
     * @param players Array of Bungee Players
     * @return ArrayList of PlayerInstances
     */
    public static ArrayList<PlayerInstance> mapPlayers(ProxiedPlayer[] players) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (ProxiedPlayer player : players) {
            playerInstances.add(mapPlayer(player));
        }
        return playerInstances;
    }
}
