package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {

    private PhantomCore instance;

    public BroadcastCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.broadcast")) {
            if (args.length == 0) {
                sender.sendMessage(instance.colorize(instance.messages.get("broadcast-usage", "Usage: /broadcast <message>")));
            } else {
                final String prefix = instance.messages.get("broadcast-prefix", "[Broadcast] ");
                final String message = instance.colorize(prefix + StringUtils.join(args, ' ', 1, args.length));
                Bukkit.broadcastMessage(message);
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
