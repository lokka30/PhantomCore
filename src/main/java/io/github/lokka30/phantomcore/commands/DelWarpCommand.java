package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DelWarpCommand implements CommandExecutor {

    private PhantomCore instance;

    public DelWarpCommand(PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
            if (sender.hasPermission("phantomcore.delwarp")) {
                if (args.length == 1) {
                    final String warp = args[0].toLowerCase();

                    if (instance.data.get("warps." + warp, null) == null) {
                        sender.sendMessage(instance.colorize(instance.messages.get("warp-null", "A warp named %warp% doesn't exist."))
                                .replace("%warp%", warp));
                    } else {
                        instance.data.set("warps." + warp, null);

                        sender.sendMessage(instance.colorize(instance.messages.get("delwarp", "Deleted warp %warp%."))
                                .replace("%warp%", warp));
                    }
                } else {
                    sender.sendMessage(instance.colorize(instance.messages.get("delwarp-usage", "Usage: /delwarp <warp name>")));
                }
            } else {
                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        return true;
    }
}
