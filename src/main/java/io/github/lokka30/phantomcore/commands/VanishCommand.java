package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VanishCommand implements CommandExecutor {

    private PhantomCore instance;

    public VanishCommand(PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.vanish")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final String path = "players." + player.getUniqueId().toString() + ".vanished";

                    if (instance.data.get(path, false)) {
                        instance.data.set(path, false);
                        player.sendMessage(instance.colorize(instance.messages.get("vanish-self-off", "Vanish disabled.")));
                        instance.manager.updateVanished();
                    } else {
                        instance.data.set(path, true);
                        player.sendMessage(instance.colorize(instance.messages.get("vanish-self-on", "Vanish enabled")));
                        instance.manager.updateVanished();
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("vanish-console-usage", "Usage (console): /vanish <player> [on/off]")));
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("phantomcore.vanish.others")) {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(instance.colorize(instance.messages.get("not-played-before", "%player% hasn't played before."))
                                .replaceAll("%player%", args[0]));
                    } else {
                        final String path = "players." + offlinePlayer.getUniqueId().toString() + ".vanished";
                        if (instance.data.get(path, false)) {
                            instance.data.set(path, false);
                            sender.sendMessage(instance.colorize(instance.messages.get("vanish-other-off", "Vanish disabled for %player%.")
                                    .replaceAll("%player%", Objects.requireNonNull(offlinePlayer.getName()))));
                            instance.manager.updateVanished();
                        } else {
                            instance.data.set(path, true);
                            sender.sendMessage(instance.colorize(instance.messages.get("vanish-other-on", "Vanish enabled for %player%.")
                                    .replaceAll("%player%", Objects.requireNonNull(offlinePlayer.getName()))));
                            instance.manager.updateVanished();
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("phantomcore.vanish.others")) {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (!offlinePlayer.hasPlayedBefore()) {
                        sender.sendMessage(instance.colorize(instance.messages.get("not-played-before", "%player% hasn't played before."))
                                .replaceAll("%player%", args[0]));
                    } else {
                        final String path = "players." + offlinePlayer.getUniqueId().toString() + ".vanished";
                        if (args[1].equalsIgnoreCase("on")) {
                            instance.data.set(path, true);
                            sender.sendMessage(instance.colorize(instance.messages.get("vanish-other-on", "Vanish enabled for %player%.")
                                    .replaceAll("%player%", Objects.requireNonNull(offlinePlayer.getName()))));
                            instance.manager.updateVanished();
                        } else if (args[1].equalsIgnoreCase("off")) {
                            instance.data.set(path, false);
                            sender.sendMessage(instance.colorize(instance.messages.get("vanish-other-off", "Vanish disabled for %player%.")
                                    .replaceAll("%player%", Objects.requireNonNull(offlinePlayer.getName()))));
                            instance.manager.updateVanished();
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("vanish-usage", "Usage: /vanish [player] [on/off]")));
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("vanish-usage", "Usage: /vanish [player] [on/off]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            return true;
        }
        return true;
    }
}
