package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    private PhantomCore instance;

    public FlyCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.fly")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;

                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.sendMessage(instance.colorize(instance.messages.get("fly-mode-disabled", "Flight mode disabled.")));
                    } else {
                        player.setAllowFlight(true);
                        player.sendMessage(instance.colorize(instance.messages.get("fly-mode-enabled", "Flight mode enabled.")));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("fly-usage-console", "Usage (console): /fly <on/off> <player>")));
                }
            } else if (args.length == 1) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;

                    switch (args[0].toLowerCase()) {
                        case "on":
                            player.setAllowFlight(true);
                            player.sendMessage(instance.colorize(instance.messages.get("fly-mode-enabled", "Flight mode enabled.")));
                            break;
                        case "off":
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            player.sendMessage(instance.colorize(instance.messages.get("fly-mode-disabled", "Flight mode disabled.")));
                            break;
                        default:
                            player.sendMessage(instance.colorize(instance.messages.get("fly-usage", "Usage: /fly [on/off] [player]")));
                            break;
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("fly-usage-console", "Usage (console): /fly <on/off> <player>")));
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("phantomcore.fly.others")) {
                    final Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% isn't online."))
                                .replace("%player%", args[1]));
                    } else {
                        switch (args[0].toLowerCase()) {
                            case "on":
                                target.setAllowFlight(true);
                                sender.sendMessage(instance.colorize(instance.messages.get("fly-mode-enabled-others", "Flight mode enabled for %player%."))
                                        .replace("%player%", target.getName()));
                                target.sendMessage(instance.colorize(instance.messages.get("fly-mode-enabled-by", "Flight mode enabled by %sender%."))
                                        .replace("%sender%", sender.getName()));
                                break;
                            case "off":
                                target.setAllowFlight(false);
                                target.setFlying(false);
                                sender.sendMessage(instance.colorize(instance.messages.get("fly-mode-disabled-others", "Flight mode disabled for %player%."))
                                        .replace("%player%", target.getName()));
                                target.sendMessage(instance.colorize(instance.messages.get("fly-mode-disabled-by", "Flight mode disabled by %sender%."))
                                        .replace("%sender%", sender.getName()));
                                break;
                            default:
                                sender.sendMessage(instance.colorize(instance.messages.get("fly-usage", "Usage: /fly [on/off] [player]")));
                                break;
                        }
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("fly-usage", "Usage: /fly [on/off] [player]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
