package ca.sperrer.p0t4t0sandwich.playtimeutils.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.repeatTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.forge.ForgeUtils.mapPlayer;

public class ForgeEventListener {
    ForgeMain mod = ForgeMain.getInstance();

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        // Start Playtime Tracker
        repeatTaskAsync(() -> mod.playtimeUtils.dataSource.updatePlaytime(
                        ForgeUtils.mapPlayers(
                                event.getServer().getPlayerList().getPlayers().toArray(new ServerPlayer[0]),
                                mod.playtimeUtils.getServerName()
                        )),
        0L, 20*60L);
    }

    @SubscribeEvent
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        runTaskAsync(() -> {
            int streak = mod.playtimeUtils.dataSource.playerLoginData(
                    mapPlayer(
                            player,
                            mod.playtimeUtils.getServerName()
                    )
            );
            if (streak == 1) {
                // TODO: Forge streakResetEvent
                player.displayClientMessage(Component.empty().append("§cYour streak has been reset!"), false);
            } else if (streak != -1) {
                // TODO: Forge streakIncrementEvent
                player.displayClientMessage(Component.empty().append("§aYour streak is now " + streak + "!" + " Keep up the good work!"), false);
            }
        });
    }

    @SubscribeEvent
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        runTaskAsync(() -> mod.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(
                        (ServerPlayer) event.getEntity(),
                        mod.playtimeUtils.getServerName()
                )
        ));
    }
}