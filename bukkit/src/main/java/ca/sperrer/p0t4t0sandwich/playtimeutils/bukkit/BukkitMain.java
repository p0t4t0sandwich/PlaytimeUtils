package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.isFolia;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.isPaper;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.isSpigot;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.isCraftBukkit;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {
    PlaytimeUtils playtimeUtils;

    // Singleton instance
    private static BukkitMain instance;
    public static BukkitMain getInstance() {
        return instance;
    }

    public String getServerType() {
        if (isFolia()) {
            return "Folia";
        } else if (isPaper()) {
            return "Paper";
        } else if (isSpigot()) {
            return "Spigot";
        } else if (isCraftBukkit()) {
            return "CraftBukkit";
        } else {
            return "Unknown";
        }
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
