package io.github.lokka30.phantomcore.commands;

import com.cryptomorin.xseries.XEnchantment;
import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EnchantCommand implements CommandExecutor {

    private PhantomCore instance;

    public EnchantCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("phantomcore.enchant")) {
                Enchantment enchantment;
                switch (args.length) {
                    case 1:
                        enchantment = XEnchantment.matchXEnchantment(args[0].toUpperCase()).get().parseEnchantment();

                        if (enchantment == null) {
                            player.sendMessage(instance.colorize(instance.messages.get("enchant-unknown", "%enchantment% is not an enchantment"))
                                    .replaceAll("%enchantment%", args[0]));
                        } else {
                            ItemStack mainHand = player.getInventory().getItemInMainHand();
                            ItemStack offHand = player.getInventory().getItemInOffHand();
                            int startLevel = enchantment.getStartLevel();
                            if (mainHand.getType() != Material.AIR) {
                                if (enchantment.canEnchantItem(mainHand) || player.hasPermission("phantomcore.enchant.unsafe")) {
                                    player.getInventory().setItemInMainHand(enchant(mainHand, enchantment, startLevel));
                                    player.sendMessage(instance.colorize(instance.messages.get("enchant-success", "enchanted %item% with %enchantment% lvl %level%"))
                                            .replaceAll("%item%", mainHand.getType().name())
                                            .replaceAll("%enchantment%", enchantment.getName())
                                            .replaceAll("%level%", String.valueOf(startLevel)));
                                } else {
                                    player.sendMessage(instance.colorize(instance.messages.get("enchant-unsafe", "you can't enchant %item% with %enchantment% lvl %level% because it is unsafe"))
                                            .replaceAll("%item%", mainHand.getType().name())
                                            .replaceAll("%enchantment%", enchantment.getName())
                                            .replaceAll("%level%", String.valueOf(startLevel)));
                                }
                            } else if (offHand.getType() != Material.AIR) {
                                if (enchantment.canEnchantItem(offHand) || player.hasPermission("phantomcore.enchant.unsafe")) {
                                    player.getInventory().setItemInOffHand(enchant(offHand, enchantment, startLevel));
                                    player.sendMessage(instance.colorize(instance.messages.get("enchant-success", "enchanted %item% with %enchantment% lvl %level%"))
                                            .replaceAll("%item%", offHand.getType().name())
                                            .replaceAll("%enchantment%", enchantment.getName())
                                            .replaceAll("%level%", String.valueOf(startLevel)));
                                } else {
                                    player.sendMessage(instance.colorize(instance.messages.get("enchant-unsafe", "you can't enchant %item% with %enchantment% lvl %level% because it is unsafe"))
                                            .replaceAll("%item%", offHand.getType().name())
                                            .replaceAll("%enchantment%", enchantment.getName())
                                            .replaceAll("%level%", String.valueOf(startLevel)));
                                }
                            } else {
                                player.sendMessage(instance.colorize(instance.messages.get("enchant-no-item-in-hands", "no item in either of your hands to enchant")));
                            }
                        }
                        break;
                    case 2:
                        enchantment = XEnchantment.matchXEnchantment(args[0].toUpperCase()).get().parseEnchantment();
                        int level;

                        try {
                            level = Integer.parseInt(args[1]);
                        } catch (NumberFormatException exception) {
                            player.sendMessage(instance.colorize(instance.messages.get("enchant-invalid-level", "%level% is not a valid level"))
                                    .replaceAll("%level%", args[1]));
                            return true;
                        }

                        if (enchantment == null) {
                            player.sendMessage(instance.colorize(instance.messages.get("enchant-unknown", "%enchantment% is not an enchantment"))
                                    .replaceAll("%enchantment%", args[0]));
                        } else {
                            ItemStack mainHand = player.getInventory().getItemInMainHand();
                            ItemStack offHand = player.getInventory().getItemInOffHand();
                            int startLevel = enchantment.getStartLevel();

                            if (startLevel > level) {
                                player.sendMessage(instance.colorize(instance.messages.get("enchant-level-too-low", "enchant level too low"))
                                        .replaceAll("%level%", String.valueOf(level))
                                        .replaceAll("%enchantment%", enchantment.getName())
                                        .replaceAll("%minLevel%", String.valueOf(startLevel)));
                            } else {
                                if (mainHand.getType() != Material.AIR) {
                                    if (player.hasPermission("phantomcore.enchant.unsafe") || enchantment.canEnchantItem(mainHand) || enchantment.getMaxLevel() >= level) {
                                        player.getInventory().setItemInMainHand(enchant(mainHand, enchantment, level));
                                        player.sendMessage(instance.colorize(instance.messages.get("enchant-success", "enchanted %item% with %enchantment% lvl %level%"))
                                                .replaceAll("%item%", mainHand.getType().name())
                                                .replaceAll("%enchantment%", enchantment.getName())
                                                .replaceAll("%level%", args[1]));
                                    } else {
                                        player.sendMessage(instance.colorize(instance.messages.get("enchant-unsafe", "you can't enchant %item% with %enchantment% lvl %level% because it is unsafe"))
                                                .replaceAll("%item%", mainHand.getType().name())
                                                .replaceAll("%enchantment%", enchantment.getName())
                                                .replaceAll("%level%", args[1]));
                                    }
                                } else if (offHand.getType() != Material.AIR) {
                                    if (player.hasPermission("phantomcore.enchant.unsafe") || enchantment.canEnchantItem(mainHand) || enchantment.getMaxLevel() >= level) {
                                        player.getInventory().setItemInOffHand(enchant(offHand, enchantment, level));
                                        player.sendMessage(instance.colorize(instance.messages.get("enchant-success", "enchanted %item% with %enchantment% lvl %level%"))
                                                .replaceAll("%item%", offHand.getType().name())
                                                .replaceAll("%enchantment%", enchantment.getName())
                                                .replaceAll("%level%", args[1]));
                                    } else {
                                        player.sendMessage(instance.colorize(instance.messages.get("enchant-unsafe", "you can't enchant %item% with %enchantment% lvl %level% because it is unsafe"))
                                                .replaceAll("%item%", offHand.getType().name())
                                                .replaceAll("%enchantment%", enchantment.getName())
                                                .replaceAll("%level%", args[1]));
                                    }
                                } else {
                                    player.sendMessage(instance.colorize(instance.messages.get("enchant-no-item-in-hands", "no item in either of your hands to enchant")));
                                }
                            }
                        }
                        break;
                    default:
                        player.sendMessage(instance.colorize(instance.messages.get("enchant-usage", "usage /enchant <enchantment> [level]")));
                        break;
                }
            } else {
                player.sendMessage(instance.colorize(instance.messages.get("no-permission", "You don't have access to that.")));
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("players-only", "only players can use this command")));
        }
        return true;
    }

    private ItemStack enchant(ItemStack itemStack, Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return itemStack;
    }
}
