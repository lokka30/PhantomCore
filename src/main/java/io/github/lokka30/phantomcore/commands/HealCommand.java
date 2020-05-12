package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class HealCommand implements CommandExecutor {

    private PhantomCore instance;

    public HealCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.heal")) {
            switch (args.length) {
                case 0:
                    if (sender instanceof Player) {
                        final Player player = (Player) sender;
                        heal(player);
                        player.sendMessage(instance.colorize(instance.messages.get("heal-self", "You're feeling better now.")));
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("heal-usage-console", "usage console /heal <player>")));
                    }
                    break;
                case 1:
                    if (sender.hasPermission("phantomcore.heal.others")) {
                        final Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% is offline"))
                                    .replace("%player%", args[0]));
                        } else {
                            heal(target);
                            sender.sendMessage(instance.colorize(instance.messages.get("heal-others", "you healed %player%"))
                                    .replace("%player%", target.getName()));
                            target.sendMessage(instance.colorize(instance.messages.get("heal-by", "you were healed by %sender%"))
                                    .replace("%sender%", sender.getName()));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
                    }
                    break;
                default:
                    sender.sendMessage(instance.colorize(instance.messages.get("heal-usage", "usage /heal [player]")));
                    break;
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
        }
        return true;
    }

    private void heal(Player player) {
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        player.setFireTicks(0);
        player.setRemainingAir(player.getMaximumAir());
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        if (instance.settings.get("heal-removes-all-potion-effects", true)) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }
}
