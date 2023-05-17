package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.listeners;

import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityMain;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events.StreakResetEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import net.kyori.adventure.text.Component;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityUtils.mapPlayer;

public class VelocityEventListener {
    VelocityMain plugin = VelocityMain.getInstance();

    @Subscribe
    public void onPlayerLogin(LoginEvent event) {
        runTaskAsync(() -> {
            int streak = plugin.playtimeUtils.dataSource.playerLoginData(
                    mapPlayer(event.getPlayer())
            );
            if (streak == 1) {
                plugin.getServer().getEventManager().fire(new StreakResetEvent(event.getPlayer()));
                event.getPlayer().sendMessage(Component.text("§cYour streak has been reset!"));
            } else if (streak != -1) {
                plugin.getServer().getEventManager().fire(new StreakIncrementEvent(event.getPlayer(), streak));
                event.getPlayer().sendMessage(Component.text("§aYour streak is now " + streak + "!" + " Keep up the good work!"));
            }

            // Test event listener (TODO: Remove later)
            plugin.getServer().getEventManager().fire(new StreakIncrementEvent(event.getPlayer(), streak));
            plugin.getServer().getEventManager().fire(new StreakResetEvent(event.getPlayer()));
        });
    }

    @Subscribe
    public void onPlayerQuit(DisconnectEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(event.getPlayer())
        ));
    }
}
