package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PhantomCoreCommand implements CommandExecutor {

    private PhantomCore instance;

    public PhantomCoreCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    /*
    Ensure:
    - Class is registered in PhantomCore#registerCommands()
     */

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        sender.sendMessage(instance.colorize("&7This server is running &bPhantomCore v" + instance.getDescription().getVersion() + "&7."));
        sender.sendMessage(instance.colorize("&7Git Repo: &3github.com/lokka30/PhantomCore"));
        return true;
    }
}
