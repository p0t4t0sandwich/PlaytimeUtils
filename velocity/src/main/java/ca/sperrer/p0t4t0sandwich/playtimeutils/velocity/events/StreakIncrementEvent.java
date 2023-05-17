package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.events;


import com.velocitypowered.api.proxy.Player;

public class StreakIncrementEvent {
    private final Player player;
    private final int streak;

    public StreakIncrementEvent(Player player, int streak) {
        this.player = player;
        this.streak = streak;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getStreak() {
        return this.streak;
    }
}