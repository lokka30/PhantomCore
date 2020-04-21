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
public class SetSpawnCommand implements CommandExecutor {

    private PhantomCore instance;

    public SetSpawnCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.setspawn")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    final Player player = (Player) sender;
                    final Location location = player.getLocation();

                    double x, y, z;
                    String worldName = Objects.requireNonNull(location.getWorld()).getName();

                    if (instance.settings.get("teleport-to-centre", true)) {
                        // gets 'block' part of location, as it is an integer
                        // add 0.5 to x and z to teleport player to centre
                        // no rounding required as there can only be up to 1dp.
                        x = location.getBlockX() + 0.5;
                        y = location.getBlockY();
                        z = location.getBlockZ() + 0.5;
                    } else {
                        // don't touch the coordinates (except for rounding)
                        x = instance.utils.round(location.getX());
                        y = instance.utils.round(location.getY());
                        z = instance.utils.round(location.getZ());
                    }

                    instance.data.set("spawn.x", x);
                    instance.data.set("spawn.y", y);
                    instance.data.set("spawn.z", z);
                    instance.data.set("spawn.yaw", instance.utils.round(location.getYaw()));
                    instance.data.set("spawn.pitch", instance.utils.round(location.getPitch()));
                    instance.data.set("spawn.world", worldName);

                    player.sendMessage(instance.colorize(instance.messages.get("spawn-set", "Spawn set at %x%, %y%, %z% in world %world%."))
                            .replaceAll("%x%", String.valueOf(x))
                            .replaceAll("%y%", String.valueOf(y))
                            .replaceAll("%z%", String.valueOf(z))
                            .replaceAll("%world%", worldName));
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("spawn-set-usage", "Usage: /setspawn")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("players-only", "Only players can use this command.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
