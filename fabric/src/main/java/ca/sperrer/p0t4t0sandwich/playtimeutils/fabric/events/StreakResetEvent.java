package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface StreakResetEvent {
    Event<StreakResetEvent> EVENT = EventFactory.createArrayBacked(StreakResetEvent.class, (listeners) -> (player) -> {
        for (StreakResetEvent listener : listeners) {
            listener.onStreakReset(player);
        }
    });

    void onStreakReset(ServerPlayerEntity player);
}
