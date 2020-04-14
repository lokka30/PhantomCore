package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearInventoryCommand implements CommandExecutor {

    private PhantomCore instance;

    public ClearInventoryCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.clearinventory")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;

                    player.getInventory().clear();
                    player.sendMessage(instance.colorize(instance.messages.get("clear-inventory-self", "Cleared your inventory.")));
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("clear-inventory-usage-console", "Usage (console): /clearinventory <player>")));
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("phantomcore.clearinventory.others")) {
                    //TODO Clear target's inventory.
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% isn't online."))
                                .replaceAll("%player%", args[0]));

                    } else {
                        target.getInventory().clear();

                        sender.sendMessage(instance.colorize(instance.messages.get("clear-inventory-other", "Cleared %player%'s inventory."))
                                .replaceAll("%player%", target.getName()));

                        target.sendMessage(instance.colorize(instance.messages.get("clear-inventory-by-other", "Your inventory was cleared by %sender%."))
                                .replaceAll("%sender%", sender.getName()));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("clear-inventory-usage", "Usage: /clearinventory [player]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
