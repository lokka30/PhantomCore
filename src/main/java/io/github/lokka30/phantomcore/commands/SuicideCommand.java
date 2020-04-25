package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class SuicideCommand implements CommandExecutor {

    private PhantomCore instance;

    public SuicideCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.suicide")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                if (args.length == 0) {
                    player.setHealth(0.0);
                    player.sendMessage(instance.colorize(instance.messages.get("suicide-success", "You took your own life.")));
                } else {
                    player.sendMessage(instance.colorize(instance.messages.get("suicide-usage", "usage: /suicide")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("players-only", "only players can use this")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
