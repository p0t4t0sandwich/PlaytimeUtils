package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakResetEvent;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricTestListener implements StreakIncrementEvent, StreakResetEvent {
    @Override
    public void onStreakIncrement(ServerPlayerEntity player, int streak) {

    }

    @Override
    public void onStreakReset(ServerPlayerEntity player) {

    }
}
