package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakResetEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.listeners.FabricEventListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.test.FabricTestListener;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricMain implements DedicatedServerModInitializer {
    public PlaytimeUtils playtimeUtils;

    // Logger
    public final Logger logger = LoggerFactory.getLogger("playtimeutils");

    // Get server type
    public String getServerType() {
        return "Fabric";
    }

    // Singleton instance
    private static FabricMain instance;
    public static FabricMain getInstance() {
        return instance;
    }

    @Override
    public void onInitializeServer() {
        // Singleton instance
        instance = this;

        logger.info("[PlaytimeUtils]: PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("config", logger);
        playtimeUtils.start();

        // Register event listeners
        ServerLifecycleEvents.SERVER_STARTED.register(new FabricEventListener());
        ServerPlayConnectionEvents.JOIN.register(new FabricEventListener());
        ServerPlayConnectionEvents.DISCONNECT.register(new FabricEventListener());

        // Register events TODO: Test this
        StreakIncrementEvent.EVENT.register((player, streak) -> {});
        StreakResetEvent.EVENT.register((player) -> {});

        // Test event listener (TODO: Remove later)
        StreakIncrementEvent.EVENT.register(new FabricTestListener());
        StreakResetEvent.EVENT.register(new FabricTestListener());

        // Mod enable message
        logger.info("[PlaytimeUtils]: PlaytimeUtils has been enabled!");
    }
}
