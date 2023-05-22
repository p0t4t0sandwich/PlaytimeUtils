package ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.commands;

import ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.BukkitMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static ca.sperrer.p0t4t0sandwich.playtimeutils.bukkit.BukkitUtils.mapPlayer;
import static ca.sperrer.p0t4t0sandwich.playtimeutils.common.Utils.runTaskAsync;

public class StreakCommand implements CommandExecutor {
    private final BukkitMain plugin = BukkitMain.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AtomicBoolean success = new AtomicBoolean(false);
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof Player)) {
                    Player player = (Player) sender;

                    int streak = plugin.playtimeUtils.utilData.getStreak(mapPlayer(player, plugin.playtimeUtils.getServerName()));
                    String text = "Your streak is " + streak + "D! Keep up the good work!";

                    player.sendMessage(Arrays.toString(new ComponentBuilder(text).color(ChatColor.GREEN).create()));
                    success.set(true);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        return success.get();
    }
}
