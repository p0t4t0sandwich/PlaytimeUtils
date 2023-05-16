package ca.sperrer.p0t4t0sandwich.playtimeutils.forge.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class StreakIncrementEvent extends Event {
    private final ServerPlayer player;
    private final int streak;

    public StreakIncrementEvent(ServerPlayer player, int streak) {
        this.player = player;
        this.streak = streak;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public int getStreak() {
        return streak;
    }
}
