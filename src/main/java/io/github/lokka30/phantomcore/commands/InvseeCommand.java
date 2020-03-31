package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {

    private PhantomCore instance;

    public InvseeCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (player.hasPermission("phantomcore.invsee")) {
                if (args.length == 1) {
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% isn't online."))
                            .replaceAll("%player%", args[0]));
                    } else {
                        player.sendMessage(instance.colorize(instance.messages.get("opened-inventory", "Opened inventory of %player%."))
                            .replaceAll("%player%", target.getName()));
                        player.openInventory(target.getInventory());
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("invsee-usage", "Usage: /invsee <player>")));
                }
            } else {
                player.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("players-only", "Only players can use this command.")));
        }
        return true;
    }
}
