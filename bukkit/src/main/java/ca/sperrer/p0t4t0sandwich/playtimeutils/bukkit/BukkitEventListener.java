package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.events.StreakResetEvent;
import org.bukkit.Bukkit;
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
        StreakIncrementEvent streakIncrementEvent = new StreakIncrementEvent(event.getPlayer());
        StreakResetEvent streakResetEvent = new StreakResetEvent(event.getPlayer());
        runTaskAsync(() -> {
            int streak = plugin.playtimeUtils.dataSource.playerLoginData(
                    mapPlayer(
                            event.getPlayer(),
                            plugin.playtimeUtils.getServerName()
                    )
            );
            if (streak == 1) {
                Bukkit.getPluginManager().callEvent(streakResetEvent);
                event.getPlayer().sendMessage("§cYour streak has been reset!");
            } else if (streak != -1) {
                Bukkit.getPluginManager().callEvent(streakIncrementEvent);
                event.getPlayer().sendMessage("§aYour streak is now " + streak + "!" + " Keep up the good work!");
            }
        });
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
