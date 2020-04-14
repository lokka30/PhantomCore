package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetWarpCommand implements CommandExecutor {

    private PhantomCore instance;

    public SetWarpCommand(PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("phantomcore.setwarp")) {
                if (args.length == 1) {
                    final String warp = args[0].toLowerCase();
                    instance.data.set("warps." + warp + ".x", player.getLocation().getBlockX() + 0.5);
                    instance.data.set("warps." + warp + ".y", player.getLocation().getBlockY());
                    instance.data.set("warps." + warp + ".z", player.getLocation().getBlockZ() + 0.5);
                    instance.data.set("warps." + warp + ".pitch", player.getLocation().getPitch());
                    instance.data.set("warps." + warp + ".yaw", player.getLocation().getYaw());
                    instance.data.set("warps." + warp + ".world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
                    player.sendMessage(instance.colorize(instance.messages.get("setwarp", "Set warp %warp%."))
                            .replaceAll("%warp%", warp));
                } else {
                    player.sendMessage(instance.colorize(instance.messages.get("setwarp-usage", "Usage: /setwarp <warp name>")));
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
