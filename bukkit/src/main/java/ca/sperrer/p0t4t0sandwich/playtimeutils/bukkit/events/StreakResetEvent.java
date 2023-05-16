package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StreakResetEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private boolean isCancelled;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public StreakResetEvent(Player player) {
        super(true);
        this.player = player;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }
}
