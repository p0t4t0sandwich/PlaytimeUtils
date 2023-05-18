package ca.sperrer.p0t4t0sandwich.playtimeutils.api.events;

public interface StreakIncrementEvent<T> {
    T getPlayer();
    int getStreak();
}
