package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakResetEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FabricTestListener implements StreakIncrementEvent, StreakResetEvent {
    @Override
    public void onStreakIncrement(ServerPlayerEntity player, int streak) {
        player.sendMessage(Text.of("§aYour streak is now " + streak + "!" + " Keep up the good work!"));
        System.out.println("StreakIncrementEvent");
    }

    @Override
    public void onStreakReset(ServerPlayerEntity player) {
        player.sendMessage(Text.of("§cYour streak has been reset!"));
        System.out.println("StreakResetEvent");
    }
}
