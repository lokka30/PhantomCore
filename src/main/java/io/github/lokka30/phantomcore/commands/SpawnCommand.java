package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class SpawnCommand implements CommandExecutor {

    private PhantomCore instance;

    public SpawnCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.spawn")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;

                    final double x = instance.data.get("spawn.x", 0);
                    final double y = instance.data.get("spawn.y", 0);
                    final double z = instance.data.get("spawn.z", 0);
                    final float yaw = instance.data.get("spawn.yaw", 0);
                    final float pitch = instance.data.get("spawn.pitch", 0);
                    final String worldName = instance.data.get("spawn.world", null);

                    if (worldName == null || Bukkit.getWorld(worldName) == null) {
                        player.sendMessage(instance.colorize(instance.messages.get("spawn-bad-location", "Either the spawn isn't set, or the current spawn location is invalid.")));
                    } else {
                        player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
                        player.sendMessage(instance.colorize(instance.messages.get("spawn-teleported", "Welcome to spawn!")));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("spawn-usage-console", "Usage (console): /spawn <player>")));
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("phantomcore.spawn.others")) {
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% isn't online."))
                                .replace("%target%", args[0]));
                    } else {
                        final double x = instance.data.get("spawn.x", 0);
                        final double y = instance.data.get("spawn.y", 0);
                        final double z = instance.data.get("spawn.z", 0);
                        final float yaw = instance.data.get("spawn.yaw", 0);
                        final float pitch = instance.data.get("spawn.pitch", 0);
                        final String worldName = instance.data.get("spawn.world", null);

                        final World world = Bukkit.getWorld(worldName);

                        if (world == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("spawn-bad-location", "Either the spawn isn't set, or the current spawn location is invalid.")));
                        } else {
                            target.teleport(new Location(world, x, y, z, yaw, pitch));
                            sender.sendMessage(instance.colorize(instance.messages.get("spawn-teleported-others", "Teleported %target% to spawn."))
                                    .replace("%target%", target.getName()));
                            target.sendMessage(instance.colorize(instance.messages.get("spawn-teleported-by", "%sender% sent you to spawn."))
                                    .replace("%sender%", sender.getName()));
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("spawn-usage", "Usage: /spawn [player]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
