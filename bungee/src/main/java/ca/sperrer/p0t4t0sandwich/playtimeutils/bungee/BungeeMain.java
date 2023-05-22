package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.commands.PlaytimeCommand;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.commands.StreakCommand;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.listeners.BungeeEventListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.test.BungeeTestListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeUtils.mapPlayers;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.repeatTaskAsync;

public class BungeeMain extends Plugin {
    public PlaytimeUtils playtimeUtils;

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

        // Start Playtime Tracker
        repeatTaskAsync(() -> playtimeUtils.trackerData.updatePlaytime(
                        mapPlayers(
                                getProxy().getPlayers().toArray(new ProxiedPlayer[0])
                        )),
        0L, 20*60L);

        // Register event listener
        getProxy().getPluginManager().registerListener(this, new BungeeEventListener());

        // Register commands
        getProxy().getPluginManager().registerCommand(this, new PlaytimeCommand());
        getProxy().getPluginManager().registerCommand(this, new StreakCommand());

        // Test event listener (TODO: remove later)
        getProxy().getPluginManager().registerListener(this, new BungeeTestListener());

        // Plugin enable message
        getLogger().info("PlaytimeUtils has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("PlaytimeUtils has been disabled!");
    }
}
