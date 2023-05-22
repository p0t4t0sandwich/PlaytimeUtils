package ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.commands;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bungee.BungeeUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class PlaytimeCommand extends Command {
    private final BungeeMain plugin = BungeeMain.getInstance();

    public PlaytimeCommand() {
        super("playtime");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof ProxiedPlayer)) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    int playtime = plugin.playtimeUtils.utilData.getPlaytime(mapPlayer(player));
                    String text = "Your playtime is " + playtime + " minutes.";

                    player.sendMessage(new ComponentBuilder(text).color(ChatColor.GREEN).create());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
