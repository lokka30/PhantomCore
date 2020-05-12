package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyspeedCommand implements CommandExecutor {

    private PhantomCore instance;

    public FlyspeedCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.flyspeed")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    float speed;

                    try {
                        speed = Float.parseFloat(args[0]);
                    } catch (NumberFormatException exception) {
                        player.sendMessage(instance.colorize(instance.messages.get("flyspeed-invalid-speed", "Invalid flyspeed %arg%, it must be between 0 and 10."))
                                .replace("%arg%", args[0]));
                        return true;
                    }

                    if (speed < 0 || speed > 10) {
                        player.sendMessage(instance.colorize(instance.messages.get("flyspeed-invalid-speed", "Invalid flyspeed %arg%, it must be between 0 and 10."))
                                .replace("%arg%", args[0]));
                    }

                    player.setFlySpeed(speed / 10);
                    player.sendMessage(instance.colorize(instance.messages.get("flyspeed-set", "Set your flyspeed to %speed%."))
                            .replace("%speed%", args[0]));
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("flyspeed-usage-console", "Usage (console): &a/flyspeed <0-10> <player>")));
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("phantomcore.flyspeed.others")) {
                    final Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% isn't online."))
                                .replace("%player%", args[1]));
                    } else {
                        float speed;

                        try {
                            speed = Float.parseFloat(args[0]);
                        } catch (NumberFormatException exception) {
                            sender.sendMessage(instance.colorize(instance.messages.get("flyspeed-invalid-speed", "Invalid flyspeed %arg%, it must be between 0 and 10."))
                                    .replace("%arg%", args[0]));
                            return true;
                        }

                        if (speed < 0 || speed > 10) {
                            sender.sendMessage(instance.colorize(instance.messages.get("flyspeed-invalid-speed", "Invalid speed %arg%, it must be between 0 and 10."))
                                    .replace("%arg%", args[0]));
                        }

                        target.setFlySpeed(speed / 10);
                        sender.sendMessage(instance.colorize(instance.messages.get("flyspeed-set-other", "Set %player%'s flyspeed to %speed%."))
                                .replace("%player%", args[1])
                                .replace("%speed%", args[0]));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("flyspeed-usage", "Usage: /flyspeed <0-10> [player]")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
