package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.listeners;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeMain;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events.StreakResetEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class BungeeEventListener implements Listener {
    BungeeMain plugin = BungeeMain.getInstance();

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        runTaskAsync(() -> {
            int streak = plugin.playtimeUtils.dataSource.playerLoginData(
                    mapPlayer(event.getPlayer())
            );
            if (streak == 1) {
                plugin.getProxy().getPluginManager().callEvent(new StreakResetEvent(event.getPlayer()));
                event.getPlayer().sendMessage(
                        new ComponentBuilder("§cYour streak has been reset!").create());
            } else if (streak != -1) {
                plugin.getProxy().getPluginManager().callEvent(new StreakIncrementEvent(event.getPlayer(), streak));
                event.getPlayer().sendMessage(
                        new ComponentBuilder("§aYour streak is now " + streak + "!" + " Keep up the good work!").create());
            }

            // Test event listener (TODO: remove later)
//            plugin.getProxy().getPluginManager().callEvent(new StreakIncrementEvent(event.getPlayer(), streak));
//            plugin.getProxy().getPluginManager().callEvent(new StreakResetEvent(event.getPlayer()));
        });
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(event.getPlayer())
        ));
    }
}
