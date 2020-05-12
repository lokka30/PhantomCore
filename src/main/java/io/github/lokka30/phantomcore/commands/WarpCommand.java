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

public class WarpCommand implements CommandExecutor {

    private PhantomCore instance;

    public WarpCommand(PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.warp")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final String warp = args[0].toLowerCase();
                    final String path = "warps." + warp;

                    if (instance.data.get(path, null) == null) {
                        player.sendMessage(instance.colorize(instance.messages.get("warp-null", "A warp named %warp% doesn't exist."))
                                .replace("%warp%", warp));
                    } else {
                        final String worldName = instance.data.get(path + ".world", player.getWorld().getName());
                        final double x = instance.data.get(path + ".x", 0.0D);
                        final double y = instance.data.get(path + ".y", 0.0D);
                        final double z = instance.data.get(path + ".z", 0.0D);
                        final float yaw = instance.data.get(path + ".yaw", 0.0F);
                        final float pitch = instance.data.get(path + ".pitch", 0.0F);

                        if (Bukkit.getWorld(worldName) == null) {
                            player.sendMessage(instance.colorize(instance.messages.get("warp-world-null", "The world which the warp was set no longer exists.")));
                        } else {
                            //teleport timer goes here.
                            final Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                            player.teleport(location);
                            player.sendMessage(instance.colorize(instance.messages.get("warp", "Welcome to the warp %warp%."))
                                    .replace("%warp%", warp));
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("warp-console-usage", "Usage (console): /warp <warp name> <player>")));
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("phantomcore.warp.others")) {
                    final Player player = Bukkit.getPlayer(args[1]);
                    final String warp = args[0].toLowerCase();
                    final String path = "warps." + warp;

                    if (player == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% is not online."))
                                .replace("%player%", args[1]));
                    } else {
                        if (instance.data.get(path, null) == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("warp-null", "A warp named %warp% doesn't exist."))
                                    .replace("%warp%", warp));
                        } else {
                            final World world = Bukkit.getWorld(instance.data.get(path + ".world", player.getWorld().getName()));
                            final float x = instance.data.get(path + ".x", null);
                            final float y = instance.data.get(path + ".y", null);
                            final float z = instance.data.get(path + ".z", null);
                            final float yaw = instance.data.get(path + ".yaw", null);
                            final float pitch = instance.data.get(path + ".pitch", null);

                            if (world == null) {
                                sender.sendMessage(instance.colorize(instance.messages.get("warp-world-null", "The world which the warp was set no longer exists.")));
                            } else {
                                //teleport timer goes here.
                                final Location location = new Location(world, x, y, z, yaw, pitch);
                                player.teleport(location);
                                player.sendMessage(instance.colorize(instance.messages.get("warp", "Welcome to the warp %warp%."))
                                        .replace("%warp%", warp));
                                sender.sendMessage(instance.colorize(instance.messages.get("warp-other", "Sent %player% to the warp %warp%."))
                                        .replace("%warp%", warp)
                                        .replace("%player%", player.getName()));
                            }
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("warp-console-usage", "Usage (console): /warp <warp name> <player>")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
