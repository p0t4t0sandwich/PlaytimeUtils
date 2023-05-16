package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class StreakIncrementEvent extends Event {
    private final ProxiedPlayer player;
    private final int streak;

    public StreakIncrementEvent(ProxiedPlayer player, int streak) {
        this.player = player;
        this.streak = streak;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public int getStreak() {
        return this.streak;
    }
}
