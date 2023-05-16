package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface StreakIncrementEvent {
    Event<StreakIncrementEvent> EVENT = EventFactory.createArrayBacked(StreakIncrementEvent.class, (listeners) -> (player, streak) -> {
        for (StreakIncrementEvent listener : listeners) {
            listener.onStreakIncrement(player, streak);
        }
    });

    void onStreakIncrement(ServerPlayerEntity player, int streak);
}
