package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelWarpCommand implements CommandExecutor {

    private PhantomCore instance;

    public DelWarpCommand(PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("phantomcore.delwarp")) {
                if (args.length == 1) {
                    final String warp = args[0].toLowerCase();

                    instance.data.set("warps." + warp, null);

                    player.sendMessage(instance.colorize(instance.messages.get("delwarp", "Deleted warp %warp%."))
                            .replaceAll("%warp%", warp));
                } else {
                    player.sendMessage(instance.colorize(instance.messages.get("delwarp-usage", "Usage: /delwarp <warp name>")));
                }
            } else {
                player.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("players-only", "Only players may use this command.")));
        }
        return true;
    }
}
