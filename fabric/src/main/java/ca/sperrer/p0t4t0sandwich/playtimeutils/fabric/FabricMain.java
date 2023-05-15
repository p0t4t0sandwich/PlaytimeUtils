package ca.sperrer.p0t4t0sandwich.playtimeutils.fabric;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricMain implements ModInitializer {
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
    public void onInitialize() {
        // Singleton instance
        instance = this;

        logger.info("[PlaytimeUtils]: PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("config", logger);
        playtimeUtils.start();

        // Mod enable message
        logger.info("[PlaytimeUtils]: PlaytimeUtils has been enabled!");
    }
}
