package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class KillCommand implements CommandExecutor {

    private PhantomCore instance;

    public KillCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.kill")) {
            if (args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                            .replaceAll("%player%", args[0]));
                } else {
                    target.setHealth(0.0);
                    target.sendMessage(instance.colorize(instance.messages.get("kill-by", "%sender% killed you"))
                            .replaceAll("%sender%", sender.getName()));
                    sender.sendMessage(instance.colorize(instance.messages.get("kill-success", "Killed %target%"))
                            .replaceAll("%target%", target.getName()));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("kill-usage", "usage /kill <player>")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
