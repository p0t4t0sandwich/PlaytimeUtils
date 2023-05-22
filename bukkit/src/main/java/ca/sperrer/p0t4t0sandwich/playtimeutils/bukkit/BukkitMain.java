package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.commands.PlaytimeCommand;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.listeners.BukkitEventListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.test.BukkitTestListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.*;

public class BukkitMain extends JavaPlugin {
    public PlaytimeUtils playtimeUtils;

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

        // Start Playtime Tracker
        repeatTaskAsync(() -> playtimeUtils.trackerData.updatePlaytime(
                BukkitUtils.mapPlayers(
                        getServer().getOnlinePlayers().toArray(new Player[0]),
                        playtimeUtils.getServerName()
                )),
        0L, 20*60L);

        // Register event listener
        getServer().getPluginManager().registerEvents(new BukkitEventListener(), this);

        // Register commands
        getCommand("playtime").setExecutor(new PlaytimeCommand());

        // Test event listener (TODO: remove later)
         getServer().getPluginManager().registerEvents(new BukkitTestListener(), this);

        // Plugin enable message
        getLogger().info("PlaytimeUtils has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("PlaytimeUtils has been disabled!");
    }
}
