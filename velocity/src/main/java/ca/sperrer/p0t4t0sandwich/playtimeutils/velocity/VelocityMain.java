package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity;

import ca.sperrer.p0t4t0sandwich.playtimeutils.common.PlaytimeUtils;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "VelocityTest",
        name = "VelocityTest",
        version = "1.0.0"
)
public class VelocityMain {
    PlaytimeUtils playtimeUtils;
    private final ProxyServer server;
    private final Logger logger;

    // Singleton instance
    private static VelocityMain instance;
    public static VelocityMain getInstance() {
        return instance;
    }

    public ProxyServer getServer() {
        return this.server;
    }

    @Inject
    public VelocityMain(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
