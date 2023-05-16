package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakIncrementEvent;
import ca.sperrer.p0t4t0sandwich.playtimeutils.fabric.events.StreakResetEvent;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricMain implements DedicatedServerModInitializer {
    PlaytimeUtils playtimeUtils;

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

        // Get the server instance
        ServerLifecycleEvents.SERVER_STARTED.register(new FabricEventListener());

        // Register events
        StreakIncrementEvent.EVENT.register((player) -> {});
        StreakResetEvent.EVENT.register((player) -> {});

        // Mod enable message
        logger.info("[PlaytimeUtils]: PlaytimeUtils has been enabled!");
    }
}
