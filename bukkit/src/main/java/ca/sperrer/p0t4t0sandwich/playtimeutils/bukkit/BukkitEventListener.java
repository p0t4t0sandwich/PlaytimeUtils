package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.BukkitUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class BukkitEventListener implements Listener {
    BukkitMain plugin = BukkitMain.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLoginData(
                mapPlayer(
                        event.getPlayer(),
                        plugin.playtimeUtils.getServerName()
                )
        ));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(
                        event.getPlayer(),
                        plugin.playtimeUtils.getServerName()
                )
        ));
    }
}
