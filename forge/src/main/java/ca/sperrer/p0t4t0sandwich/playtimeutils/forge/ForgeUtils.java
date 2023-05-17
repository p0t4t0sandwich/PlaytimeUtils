package ca.sperrer.p0t4t0sandwich.playtimeutils.forge;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;

public class ForgeUtils {
    /**
     * Maps a Forge Player to a PlayerInstance.
     * @param player Forge Player
     * @param serverName Name of the server
     * @return PlayerInstance
     */
    public static PlayerInstance mapPlayer(ServerPlayer player, String serverName) {
        return new PlayerInstance(player.getName().toString(), player.getUUID().toString(), serverName);
    }

    /**
     * Maps an array of Forge Players to an ArrayList of PlayerInstances.
     * @param players Array of Forge Players
     * @param serverName Name of the server
     * @return ArrayList of PlayerInstances
     */
    public static ArrayList<PlayerInstance> mapPlayers(ServerPlayer[] players, String serverName) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (ServerPlayer player : players) {
            playerInstances.add(mapPlayer(player, serverName));
        }
        return playerInstances;
    }
}
