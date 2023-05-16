package ca.sperrer.p0t4t0sandwich.playtimeutils.forge.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class StreakResetEvent extends Event {
    private ServerPlayer player;

    public StreakResetEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
