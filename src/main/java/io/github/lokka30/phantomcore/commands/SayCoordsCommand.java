package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class SayCoordsCommand implements CommandExecutor {

    private PhantomCore instance;

    public SayCoordsCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("phantomcore.saycoords")) {
                if (args.length == 0) {
                    final Location location = player.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    String world = Objects.requireNonNull(location.getWorld()).getName();
                    player.chat(instance.colorize(instance.messages.get("saycoords-success", "my coords are at %x% %y% %z% in world %world%"))
                            .replace("%x%", String.valueOf(x))
                            .replace("%y%", String.valueOf(y))
                            .replace("%z%", String.valueOf(z))
                            .replace("%world%", world));
                } else {
                    player.sendMessage(instance.colorize(instance.messages.get("saycoords-usage", "usage: /saycoords")));
                }
            } else {
                player.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("players-only", "console cant use this command")));
        }
        return true;
    }
}
