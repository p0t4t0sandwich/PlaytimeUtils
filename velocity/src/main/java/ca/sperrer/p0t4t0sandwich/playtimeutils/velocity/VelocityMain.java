package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.listeners.VelocityEventListener;
import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.test.VelocityTestListener;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.repeatTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityUtils.mapPlayers;

@Plugin(
        id = "playtimeutils",
        name = "PlaytimeUtils",
        version = "1.0.0"
)
public class VelocityMain {
    public PlaytimeUtils playtimeUtils;

    @Inject
    private ProxyServer server;

    @Inject
    private Logger logger;

    // Get logger
    public Logger getLogger() {
        return this.logger;
    }

    // Get server type
    public String getServerType() {
        return "Velocity";
    }

    // Singleton instance
    private static VelocityMain instance;
    public static VelocityMain getInstance() {
        return instance;
    }

    public ProxyServer getServer() {
        return this.server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Singleton instance
        instance = this;

        this.logger.info("PlaytimeUtils is running on " + getServerType() + ".");

        // Start PlaytimeUtils
        playtimeUtils = new PlaytimeUtils("plugins", this.logger);
        playtimeUtils.start();

        // Start Playtime Tracker
        repeatTaskAsync(() -> playtimeUtils.trackerData.updatePlaytime(
            mapPlayers(
                getServer().getAllPlayers().toArray(new Player[0])
            )),
        0L, 20*60L);

        // Register event listener
        server.getEventManager().register(this, new VelocityEventListener());

        // Test event listener (TODO: Remove later)
         server.getEventManager().register(this, new VelocityTestListener());

        // Plugin enable message
        this.logger.info("PlaytimeUtils has been enabled!");
    }
}
