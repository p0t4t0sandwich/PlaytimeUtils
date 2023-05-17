package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events.StreakResetEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeTestListener implements Listener {
    @EventHandler
    public void onStreakIncrement(StreakIncrementEvent event) {
        event.getPlayer().sendMessage("§aYour streak is now " + event.getStreak() + "!" + " Keep up the good work!");
        System.out.println("StreakIncrementEvent");
    }

    @EventHandler
    public void onStreakReset(StreakResetEvent event) {
        event.getPlayer().sendMessage("§cYour streak has been reset!");
        System.out.println("StreakResetEvent");
    }
}
