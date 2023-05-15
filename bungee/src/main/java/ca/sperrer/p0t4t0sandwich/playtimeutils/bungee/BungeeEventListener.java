package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class BungeeEventListener implements Listener {
    BungeeMain plugin = BungeeMain.getInstance();

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLoginData(
                mapPlayer(event.getPlayer())
        ));
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        runTaskAsync(() -> plugin.playtimeUtils.dataSource.playerLogoutData(
                mapPlayer(event.getPlayer())
        ));
    }
}
