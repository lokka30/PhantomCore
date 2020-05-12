package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NearCommand implements CommandExecutor {

    private PhantomCore instance;

    public NearCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.near")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final HashMap<Player, Integer> nearbyPlayers = instance.manager.getNearbyPlayers(player);

                    if (nearbyPlayers.size() == 0) {
                        player.sendMessage(instance.colorize(instance.messages.get("no-nearby-players", "There are no nearby players to display.")));
                    } else {
                        player.sendMessage(instance.colorize(instance.messages.get("nearby-players-header", "Nearby players:")));
                        Iterator<Map.Entry<Player, Integer>> iterator = nearbyPlayers.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<Player, Integer> pair = iterator.next();
                            final Player nearbyPlayer = pair.getKey();
                            final int distance = pair.getValue();

                            player.sendMessage(instance.colorize(instance.messages.get("nearby-players-iterator", "- %player% - %distance% blocks away"))
                                    .replace("%player%", nearbyPlayer.getName())
                                    .replace("%distance%", String.valueOf(distance)));
                            iterator.remove();
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("near-console-usage", "Usage (console): /near <player>")));
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("phantomcore.near.others")) {
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% is not online."))
                                .replace("%player%", args[0]));
                    } else {
                        final HashMap<Player, Integer> nearbyPlayers = instance.manager.getNearbyPlayers(target);

                        if (nearbyPlayers.size() == 0) {
                            sender.sendMessage(instance.colorize(instance.messages.get("no-nearby-players", "There are no nearby players to display.")));
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("nearby-players-others-header", "Players nearby %player%:")));
                            Iterator<Map.Entry<Player, Integer>> iterator = nearbyPlayers.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<Player, Integer> pair = iterator.next();
                                final Player nearbyPlayer = pair.getKey();
                                final int distance = pair.getValue();

                                sender.sendMessage(instance.colorize(instance.messages.get("nearby-players-iterator", "- %player% - %distance% blocks away"))
                                        .replace("%player%", nearbyPlayer.getName())
                                        .replace("%distance%", String.valueOf(distance)));
                                iterator.remove();
                            }
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("near-usage", "Usage: /near [player]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
