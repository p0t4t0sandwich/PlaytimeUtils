package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class StreakResetEvent extends Event {
    private final ProxiedPlayer player;

    public StreakResetEvent(ProxiedPlayer player) {
        this.player = player;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }
}
