package ca.sperrer.p0t4t0sandwich.playtimeutils.forge;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeMain.MODID)
public class ForgeMain {
    PlaytimeUtils playtimeUtils;

    // Define mod id in a common place for everything to reference
    public static final String MODID = "playtimeutils";

    // Directly reference a slf4j logger
    public static final Logger logger = LogUtils.getLogger();

    // Get server type
    public String getServerType() {
        return "Forge";
    }

    // Singleton instance
    private static ForgeMain instance;

    public static ForgeMain getInstance() {
        return instance;
    }


    public ForgeMain() {
        // Singleton instance
        instance = this;

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        logger.info("[PlaytimeUtils]: PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("config", logger);
        playtimeUtils.start();

        // Mod enable message
        logger.info("[PlaytimeUtils]: PlaytimeUtils has been enabled!");
    }
}
