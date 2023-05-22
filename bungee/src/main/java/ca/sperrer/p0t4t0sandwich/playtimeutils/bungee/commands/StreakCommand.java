package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.commands;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class StreakCommand extends Command {
    private final BungeeMain plugin = BungeeMain.getInstance();

    public StreakCommand() {
        super("streak");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof ProxiedPlayer)) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    int streak = plugin.playtimeUtils.utilData.getStreak(mapPlayer(player));
                    String text = "Your streak is " + streak + "D! Keep up the good work!";

                    player.sendMessage(new ComponentBuilder(text).color(ChatColor.GREEN).create());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }
}
