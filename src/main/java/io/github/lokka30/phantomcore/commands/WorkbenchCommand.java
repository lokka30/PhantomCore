package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class WorkbenchCommand implements CommandExecutor {

    private PhantomCore instance;

    public WorkbenchCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.workbench")) {
            switch (args.length) {
                case 0:
                    if (sender instanceof Player) {
                        final Player player = (Player) sender;
                        player.openWorkbench(player.getLocation(), true);
                        player.sendMessage(instance.colorize(instance.messages.get("workbench-self", "Poof!")));
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("workbench-usage-console", "Usage console: /workbench <player>")));
                    }
                    break;
                case 1:
                    if (sender.hasPermission("phantomcore.workbench.others")) {
                        final Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                                    .replaceAll("%player%", args[0]));
                        } else {
                            target.openWorkbench(target.getLocation(), true);
                            sender.sendMessage(instance.colorize(instance.messages.get("workbench-others", "made %player% open a workbench"))
                                    .replaceAll("%player%", target.getName()));
                            target.sendMessage(instance.colorize(instance.messages.get("workbench-by", "%sender% made you open a workbench"))
                                    .replaceAll("%sender%", sender.getName()));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                    }
                    break;
                default:
                    sender.sendMessage(instance.colorize(instance.messages.get("workbench-usage", "usage /workbench [player]")));
                    break;
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }
}
