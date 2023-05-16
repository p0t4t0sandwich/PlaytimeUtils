package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric;

import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakResetEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.repeatTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.FabricUtils.mapPlayer;

public class FabricEventListener implements ServerLifecycleEvents.ServerStarted, ServerPlayConnectionEvents.Join, ServerPlayConnectionEvents.Disconnect {
    FabricMain mod = FabricMain.getInstance();
    @Override
    public void onServerStarted(MinecraftServer server) {
        // Start Playtime Tracker
        repeatTaskAsync(() -> mod.playtimeUtils.dataSource.updatePlaytime(
                        FabricUtils.mapPlayers(
                                server.getPlayerManager().getPlayerList().toArray(new ServerPlayerEntity[0]),
                                mod.playtimeUtils.getServerName()
                        )),
        0L, 20*60L);
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        runTaskAsync(() -> {
            int streak = mod.playtimeUtils.dataSource.playerLoginData(
                    mapPlayer(
                            handler.player,
                            mod.playtimeUtils.getServerName()
                    )
            );
            if (streak == 1) {
                StreakResetEvent.EVENT.invoker().onStreakReset(handler.player);
                handler.player.sendMessage(Text.of("§cYour streak has been reset!"));
            } else if (streak != -1) {
                StreakIncrementEvent.EVENT.invoker().onStreakIncrement(handler.player, streak);
                handler.player.sendMessage(Text.of("§aYour streak is now " + streak + "!" + " Keep up the good work!"));
            }
        });
    }

    @Override
    public void onPlayDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        runTaskAsync(() -> mod.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(
                        handler.player,
                        mod.playtimeUtils.getServerName()
                )
        ));
    }
}
