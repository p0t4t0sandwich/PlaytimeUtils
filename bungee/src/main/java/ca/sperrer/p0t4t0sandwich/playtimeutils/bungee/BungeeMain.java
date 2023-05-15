package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
    PlaytimeUtils playtimeUtils;

    // Get server type
    public String getServerType() {
        return "BungeeCord";
    }

    // Singleton instance
    private static BungeeMain instance;
    public static BungeeMain getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("plugins", getLogger());
        playtimeUtils.start();

        // Plugin enable message
        getLogger().info("PlaytimeUtils has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("PlaytimeUtils has been disabled!");
    }
}
