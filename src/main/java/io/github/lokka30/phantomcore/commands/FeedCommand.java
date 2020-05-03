package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class FeedCommand implements CommandExecutor {

    private PhantomCore instance;

    public FeedCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.feed")) {
            switch (args.length) {
                case 0:
                    if (sender instanceof Player) {
                        final Player player = (Player) sender;
                        feed(player);
                        player.sendMessage(instance.colorize(instance.messages.get("feed-self", "You're not hungry anymore.")));
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("feed-usage-console", "usage console /feed <player>")));
                    }
                    break;
                case 1:
                    if (sender.hasPermission("phantomcore.feed.others")) {
                        final Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% is offline"))
                                    .replaceAll("%player%", args[0]));
                        } else {
                            feed(target);
                            sender.sendMessage(instance.colorize(instance.messages.get("feed-others", "you fed %player%"))
                                    .replaceAll("%player%", target.getName()));
                            target.sendMessage(instance.colorize(instance.messages.get("feed-by", "you were fed by %sender%"))
                                    .replaceAll("%sender%", sender.getName()));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                    }
                    break;
                default:
                    sender.sendMessage(instance.colorize(instance.messages.get("feed-usage", "usage /feed [player]")));
                    break;
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }

    private void feed(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
    }
}
