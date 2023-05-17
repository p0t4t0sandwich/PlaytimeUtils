package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityMain;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events.StreakResetEvent;
import com.velocitypowered.api.event.Subscribe;
import net.kyori.adventure.text.Component;

public class VelocityTestListener {
    VelocityMain plugin = VelocityMain.getInstance();
    @Subscribe
    public void onStreakIncrement(StreakIncrementEvent event) {
        event.getPlayer().sendMessage(Component.text("§aYour streak is now " + event.getStreak() + "!" + " Keep up the good work!"));
        plugin.getLogger().info("StreakIncrementEvent");
    }

    @Subscribe
    public void onStreakReset(StreakResetEvent event) {
        event.getPlayer().sendMessage(Component.text("§cYour streak has been reset!"));
        plugin.getLogger().info("StreakResetEvent");
    }
}
