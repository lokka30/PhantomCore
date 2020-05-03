package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("unused")
public class GodCommand implements CommandExecutor {

    private PhantomCore instance;

    public GodCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.god")) {
            switch (args.length) {
                case 0:
                    if (sender instanceof Player) {
                        final Player player = (Player) sender;
                        final UUID uuid = player.getUniqueId();

                        if (instance.manager.getGodModePlayers().contains(uuid)) {
                            instance.manager.removeGodModePlayer(uuid);
                            player.sendMessage(instance.colorize(instance.messages.get("god-self-disabled", "God disabled")));
                        } else {
                            instance.manager.addGodModePlayer(uuid);
                            player.sendMessage(instance.colorize(instance.messages.get("god-self-enabled", "God enabled")));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("god-usage-console", "usage (console): /god <on/off> <player>")));
                    }
                    break;
                case 1:
                    if (sender instanceof Player) {
                        final Player player = (Player) sender;
                        final UUID uuid = player.getUniqueId();

                        switch (args[0].toLowerCase()) {
                            case "off":
                                if (instance.manager.getGodModePlayers().contains(uuid)) {
                                    instance.manager.removeGodModePlayer(uuid);
                                    player.sendMessage(instance.colorize(instance.messages.get("god-self-disabled", "God disabled")));
                                } else {
                                    player.sendMessage(instance.colorize(instance.messages.get("god-self-disabled-already", "God disabled already")));
                                }
                                break;
                            case "on":
                                if (instance.manager.getGodModePlayers().contains(uuid)) {
                                    player.sendMessage(instance.colorize(instance.messages.get("god-self-enabled-already", "God enabled already")));
                                } else {
                                    player.sendMessage(instance.colorize(instance.messages.get("god-self-enabled", "God enabled")));
                                    instance.manager.addGodModePlayer(uuid);
                                }
                                break;
                            default:
                                player.sendMessage(instance.colorize(instance.messages.get("god-usage", "usage /god [on/off] [player]")));
                                break;
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("god-usage-console", "usage (console): /god <on/off> <player>")));
                    }
                    break;
                case 2:
                    if (sender.hasPermission("phantomcore.god.others")) {
                        final Player target = Bukkit.getPlayer(args[1]);

                        if (target == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% is offline"))
                                    .replaceAll("%player%", args[1]));
                        } else {
                            final UUID uuid = target.getUniqueId();

                            switch (args[0].toLowerCase()) {
                                case "off":
                                    if (instance.manager.getGodModePlayers().contains(uuid)) {
                                        instance.manager.getGodModePlayers().remove(uuid);
                                        sender.sendMessage(instance.colorize(instance.messages.get("god-others-disabled", "disabled god for %player%"))
                                                .replaceAll("%player%", target.getName()));
                                        target.sendMessage(instance.colorize(instance.messages.get("god-others-disabled-by", "god disabled by %sender%"))
                                                .replaceAll("%sender%", sender.getName()));
                                    } else {
                                        sender.sendMessage(instance.colorize(instance.messages.get("god-others-disabled-already", "%player% already has god disabled"))
                                                .replaceAll("%player%", target.getName()));
                                    }
                                    break;
                                case "on":
                                    if (instance.manager.getGodModePlayers().contains(uuid)) {
                                        sender.sendMessage(instance.colorize(instance.messages.get("god-others-enabled-already", "%player% already has god enabled"))
                                                .replaceAll("%player%", target.getName()));
                                    } else {
                                        instance.manager.addGodModePlayer(uuid);
                                        sender.sendMessage(instance.colorize(instance.messages.get("god-others-enabled", "enabled god for %player%"))
                                                .replaceAll("%player%", target.getName()));
                                        target.sendMessage(instance.colorize(instance.messages.get("god-others-enabled-by", "god enabled by %sender%"))
                                                .replaceAll("%sender%", sender.getName()));
                                    }
                                    break;
                                default:
                                    sender.sendMessage(instance.colorize(instance.messages.get("god-usage", "usage /god [on/off] [player]")));
                                    break;
                            }
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                    }
                    break;
                default:
                    sender.sendMessage(instance.colorize(instance.messages.get("god-usage", "usage /god [on/off] [player]")));
                    break;
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
