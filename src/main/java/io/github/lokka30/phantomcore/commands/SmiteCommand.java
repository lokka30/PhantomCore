package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class SmiteCommand implements CommandExecutor {

    private PhantomCore instance;

    public SmiteCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.smite")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final int range = instance.settings.get("target-block-range", 100);
                    final Block block = player.getTargetBlock(null, range); //null = so it doesn't scan for air

                    if (block.getType() == Material.AIR) { //make sure there is a target block
                        player.sendMessage(instance.colorize(instance.messages.get("smite-no-target-block", "You aren't looking at a block within a range of %amount%."))
                                .replace("%amount%", Integer.toString(range)));
                    } else {
                        block.getWorld().strikeLightning(block.getLocation());
                        player.sendMessage(instance.colorize(instance.messages.get("smite-success", "Poof!")));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("smite-usage-console", "Invalid usage")));
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("phantomcore.smite.others")) {
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% isn't online"))
                                .replace("%target%", args[0]));
                    } else {
                        target.getWorld().strikeLightning(target.getLocation());
                        sender.sendMessage(instance.colorize(instance.messages.get("smite-success-others", "You stroke lightning upon %target%!"))
                                .replace("%target%", target.getName()));
                        target.sendMessage(instance.colorize(instance.messages.get("smite-success-by", "Lightning was struck upon you by %sender%!"))
                                .replace("%sender%", sender.getName()));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("smite-usage", "Invalid usage")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
