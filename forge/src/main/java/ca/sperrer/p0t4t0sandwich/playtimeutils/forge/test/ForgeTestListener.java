package ca.sperrer.p0t4t0sandwich.playtimeutils.forge.test;

import ca.sperrer.p0t4t0sandwich.playtimeutils.forge.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.forge.events.StreakResetEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeTestListener {
    @SubscribeEvent
    public void onStreakIncrement(StreakIncrementEvent event) {
        event.getPlayer().displayClientMessage(Component.empty().append("§aYour streak is now " + event.getStreak() + "!" + " Keep up the good work!"), false);
        System.out.println("StreakIncrementEvent");
    }

    @SubscribeEvent
    public void onStreakReset(StreakResetEvent event) {
        event.getPlayer().displayClientMessage(Component.empty().append("§cYour streak has been reset!"), false);
        System.out.println("StreakResetEvent");
    }
}
