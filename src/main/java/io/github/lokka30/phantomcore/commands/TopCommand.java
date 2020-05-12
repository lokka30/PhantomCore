package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class TopCommand implements CommandExecutor {

    private PhantomCore instance;

    public TopCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (player.hasPermission("phantomcore.top")) {
                if (args.length == 0) {
                    player.teleport(player.getWorld()
                            .getHighestBlockAt(player.getLocation()) //Get the highest Block at the player's current x and z position
                            .getLocation() //Get the location of that block
                            .add(0, 1, 0)); //Add +1 to the Y value so it teleports on top of that block
                    player.sendMessage(instance.colorize(instance.messages.get("top-success", "Poof!")));
                } else {
                    player.sendMessage(instance.colorize(instance.messages.get("top-usage", "Invalid usage")));
                }
            } else {
                player.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("players-only", "Only players can access this command.")));
        }
        return true;
    }
}
