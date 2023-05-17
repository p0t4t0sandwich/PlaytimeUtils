package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.events.StreakResetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitTestListener implements Listener {
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
