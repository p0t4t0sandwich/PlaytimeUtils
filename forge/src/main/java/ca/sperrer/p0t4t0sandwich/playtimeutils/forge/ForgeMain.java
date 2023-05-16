package ca.sperrer.p0t4t0sandwich.playtimeutils.forge;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ForgeMain.MODID)
public class ForgeMain {
    PlaytimeUtils playtimeUtils;
    public static final String MODID = "playtimeutils";
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

        logger.info("[PlaytimeUtils]: PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("config", logger);
        playtimeUtils.start();

        // Register event listener
        MinecraftForge.EVENT_BUS.register(ForgeEventListener.class);

        // Mod enable message
        logger.info("[PlaytimeUtils]: PlaytimeUtils has been enabled!");
    }
}
