package ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.commands;

import ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityMain;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.velocity.VelocityUtils.mapPlayer;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public class StreakCommand implements SimpleCommand {
    private final VelocityMain plugin = VelocityMain.getInstance();

    @Override
    public void execute(Invocation invocation) {
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((invocation.source() instanceof Player)) {
                    Player player = (Player) invocation.source();

                    int streak = plugin.playtimeUtils.utilData.getStreak(mapPlayer(player));
                    String text = "Your streak is " + streak + "D! Keep up the good work!";

                    player.sendMessage(Component.text(text).color(GREEN));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
