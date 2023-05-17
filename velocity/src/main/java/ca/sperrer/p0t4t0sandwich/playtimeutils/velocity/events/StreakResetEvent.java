package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events;


import com.velocitypowered.api.proxy.Player;

public class StreakResetEvent {
    private final Player player;

    public StreakResetEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
