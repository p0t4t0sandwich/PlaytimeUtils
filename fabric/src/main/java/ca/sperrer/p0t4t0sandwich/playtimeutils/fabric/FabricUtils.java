package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlayerInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class FabricUtils {
    /**
     * Maps a Fabric Player to a PlayerInstance.
     * @param player Fabric Player
     * @param serverName Name of the server
     * @return PlayerInstance
     */
    static PlayerInstance mapPlayer(ServerPlayerEntity player, String serverName) {
        return new PlayerInstance(String.valueOf(player.getUuid()), player.getName().toString(), serverName);
    }

    /**
     * Maps an array of Fabric Players to an ArrayList of PlayerInstances.
     * @param players Array of Fabric Players
     * @param serverName Name of the server
     * @return ArrayList of PlayerInstances
     */
    static ArrayList<PlayerInstance> mapPlayers(ServerPlayerEntity[] players, String serverName) {
        ArrayList<PlayerInstance> playerInstances = new ArrayList<>();
        for (ServerPlayerEntity player : players) {
            playerInstances.add(mapPlayer(player, serverName));
        }
        return playerInstances;
    }
}
