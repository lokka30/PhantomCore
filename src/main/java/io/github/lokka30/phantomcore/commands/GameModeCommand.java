package io.github.lokka30.phantomcore.commands;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModeCommand implements CommandExecutor {

    private PhantomCore instance;

    public GameModeCommand(final PhantomCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender.hasPermission("phantomcore.gamemode")) {
            switch (cmd.getLabel().toLowerCase()) {
                case "gms":
                    if (sender.hasPermission("phantomcore.gamemode") && sender.hasPermission("phantomcore.gamemode.survival")) {
                        if (args.length == 0) {
                            if (sender instanceof Player) {
                                final Player player = (Player) sender;
                                player.setGameMode(GameMode.SURVIVAL);
                                player.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-self", "Gamemode set to survival")));
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-usage-short-console", "/gms <player>")));
                            }
                        } else if (args.length == 1) {
                            if (sender.hasPermission("phantomcore.gamemode.others")) {
                                final Player target = Bukkit.getPlayer(args[0]);
                                if (target == null) {
                                    sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% is not online"))
                                            .replaceAll("%target%", args[0]));
                                } else {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    target.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-by", "%sender% set your gamemode to survival"))
                                            .replaceAll("%sender%", sender.getName()));
                                    sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-others", "Set %target%s gamemdoe to survival"))
                                            .replaceAll("%target%", target.getName()));
                                }
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                            }
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-usage-short", "/gms [player]")));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                    }
                    break;
                case "gmc":
                    if (sender.hasPermission("phantomcore.gamemode") && sender.hasPermission("phantomcore.gamemode.creative")) {
                        if (args.length == 0) {
                            if (sender instanceof Player) {
                                final Player player = (Player) sender;
                                player.setGameMode(GameMode.CREATIVE);
                                player.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-self", "Gamemode set to creative")));
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-usage-short-console", "/gmc <player>")));
                            }
                        } else if (args.length == 1) {
                            if (sender.hasPermission("phantomcore.gamemode.others")) {
                                final Player target = Bukkit.getPlayer(args[0]);
                                if (target == null) {
                                    sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% is not online"))
                                            .replaceAll("%target%", args[0]));
                                } else {
                                    target.setGameMode(GameMode.CREATIVE);
                                    target.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-by", "%sender% set your gamemode to creative"))
                                            .replaceAll("%sender%", sender.getName()));
                                    sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-others", "Set %target%s gamemdoe to creative"))
                                            .replaceAll("%target%", target.getName()));
                                }
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                            }
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-usage-short", "/gmc [player]")));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                    }
                    break;
                case "gma":
                    if (sender.hasPermission("phantomcore.gamemode") && sender.hasPermission("phantomcore.gamemode.adventure")) {
                        if (args.length == 0) {
                            if (sender instanceof Player) {
                                final Player player = (Player) sender;
                                player.setGameMode(GameMode.ADVENTURE);
                                player.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-self", "Gamemode set to adventure")));
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-usage-short-console", "/gma <player>")));
                            }
                        } else if (args.length == 1) {
                            if (sender.hasPermission("phantomcore.gamemode.others")) {
                                final Player target = Bukkit.getPlayer(args[0]);
                                if (target == null) {
                                    sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% is not online"))
                                            .replaceAll("%target%", args[0]));
                                } else {
                                    target.setGameMode(GameMode.ADVENTURE);
                                    target.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-by", "%sender% set your gamemode to adventure"))
                                            .replaceAll("%sender%", sender.getName()));
                                    sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-others", "Set %target%s gamemdoe to adventure"))
                                            .replaceAll("%target%", target.getName()));
                                }
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                            }
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-usage-short", "/gma [player]")));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                    }
                    break;
                case "gmsp":
                    if (sender.hasPermission("phantomcore.gamemode") && sender.hasPermission("phantomcore.gamemode.spectator")) {
                        if (args.length == 0) {
                            if (sender instanceof Player) {
                                final Player player = (Player) sender;
                                player.setGameMode(GameMode.SPECTATOR);
                                player.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-self", "Gamemode set to spectator")));
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-usage-short-console", "/gmsp <player>")));
                            }
                        } else if (args.length == 1) {
                            if (sender.hasPermission("phantomcore.gamemode.others")) {
                                final Player target = Bukkit.getPlayer(args[0]);
                                if (target == null) {
                                    sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%target% is not online"))
                                            .replaceAll("%target%", args[0]));
                                } else {
                                    target.setGameMode(GameMode.SPECTATOR);
                                    target.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-by", "%sender% set your gamemode to spectator"))
                                            .replaceAll("%sender%", sender.getName()));
                                    sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-others", "Set %target%s gamemdoe to spectator"))
                                            .replaceAll("%target%", target.getName()));
                                }
                            } else {
                                sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                            }
                        } else {
                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-usage-short", "/gmsp [player]")));
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                    }
                    break;
                default:
                    if (args.length > 0) {
                        switch (args[0].toLowerCase()) {
                            case "survival":
                            case "s":
                            case "0":
                                if (sender.hasPermission("phantomcore.gamemode.survival")) {
                                    if (args.length == 1) {
                                        if (sender instanceof Player) {
                                            final Player player = (Player) sender;
                                            player.setGameMode(GameMode.SURVIVAL);
                                            player.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-self", "gamemode set to survival")));
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-usage-console", "Usage (console): /gamemode survival <player>")));
                                        }
                                    } else if (args.length == 2) {
                                        if (sender.hasPermission("phantomcore.gamemode.others")) {
                                            final Player target = Bukkit.getPlayer(args[1]);
                                            if (target == null) {
                                                sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                                                        .replaceAll("%player%", args[1]));
                                            } else {
                                                target.setGameMode(GameMode.SURVIVAL);
                                                target.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-by", "%sender% updated your gamemode to survival")));
                                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-others", "Set %target%'s gamemode to survival"))
                                                        .replaceAll("%target%", target.getName()));
                                            }
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                        }
                                    } else {
                                        sender.sendMessage(instance.colorize(instance.messages.get("gamemode-survival-usage", "Usage: /gamemode survival [player]")));
                                    }
                                } else {
                                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                }
                                break;
                            case "creative":
                            case "c":
                            case "1":
                                if (sender.hasPermission("phantomcore.gamemode.creative")) {
                                    if (args.length == 1) {
                                        if (sender instanceof Player) {
                                            final Player player = (Player) sender;
                                            player.setGameMode(GameMode.CREATIVE);
                                            player.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-self", "gamemode set to creative")));
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-usage-console", "Usage (console): /gamemode creative <player>")));
                                        }
                                    } else if (args.length == 2) {
                                        if (sender.hasPermission("phantomcore.creative.others")) {
                                            final Player target = Bukkit.getPlayer(args[1]);
                                            if (target == null) {
                                                sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                                                        .replaceAll("%player%", args[1]));
                                            } else {
                                                target.setGameMode(GameMode.CREATIVE);
                                                target.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-by", "%sender% updated your gamemode to creative")));
                                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-others", "Set %target%'s gamemode to creative"))
                                                        .replaceAll("%target%", target.getName()));
                                            }
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                        }
                                    } else {
                                        sender.sendMessage(instance.colorize(instance.messages.get("gamemode-creative-usage", "Usage: /gamemode creative [player]")));
                                    }
                                } else {
                                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                }
                                break;
                            case "adventure":
                            case "a":
                            case "2":
                                if (sender.hasPermission("phantomcore.gamemode.adventure")) {
                                    if (args.length == 1) {
                                        if (sender instanceof Player) {
                                            final Player player = (Player) sender;
                                            player.setGameMode(GameMode.ADVENTURE);
                                            player.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-self", "gamemode set to adventure")));
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-usage-console", "Usage (console): /gamemode adventure <player>")));
                                        }
                                    } else if (args.length == 2) {
                                        if (sender.hasPermission("phantomcore.gamemode.others")) {
                                            final Player target = Bukkit.getPlayer(args[1]);
                                            if (target == null) {
                                                sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                                                        .replaceAll("%player%", args[1]));
                                            } else {
                                                target.setGameMode(GameMode.ADVENTURE);
                                                target.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-by", "%sender% updated your gamemode to adventure")));
                                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-others", "Set %target%'s gamemode to adventure"))
                                                        .replaceAll("%target%", target.getName()));
                                            }
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                        }
                                    } else {
                                        sender.sendMessage(instance.colorize(instance.messages.get("gamemode-adventure-usage", "Usage: /gamemode adventure [player]")));
                                    }
                                } else {
                                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                }
                                break;
                            case "spectator":
                            case "sp":
                            case "spec":
                            case "3":
                                if (sender.hasPermission("phantomcore.gamemode.spectator")) {
                                    if (args.length == 1) {
                                        if (sender instanceof Player) {
                                            final Player player = (Player) sender;
                                            player.setGameMode(GameMode.SPECTATOR);
                                            player.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-self", "gamemode set to spectator")));
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-usage-console", "Usage (console): /gamemode spectator <player>")));
                                        }
                                    } else if (args.length == 2) {
                                        if (sender.hasPermission("phantomcore.gamemode.others")) {
                                            final Player target = Bukkit.getPlayer(args[1]);
                                            if (target == null) {
                                                sender.sendMessage(instance.colorize(instance.messages.get("player-not-online", "%player% not online"))
                                                        .replaceAll("%player%", args[1]));
                                            } else {
                                                target.setGameMode(GameMode.SPECTATOR);
                                                target.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-by", "%sender% updated your gamemode to spectator")));
                                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-others", "Set %target%'s gamemode to spectator"))
                                                        .replaceAll("%target%", target.getName()));
                                            }
                                        } else {
                                            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                        }
                                    } else {
                                        sender.sendMessage(instance.colorize(instance.messages.get("gamemode-spectator-usage", "Usage: /gamemode spectator [player]")));
                                    }
                                } else {
                                    sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
                                }
                                break;
                            default:
                                sender.sendMessage(instance.colorize(instance.messages.get("gamemode-usage", "/gamemode <gamemode> [player]")));
                                break;
                        }
                    } else {
                        sender.sendMessage(instance.colorize(instance.messages.get("gamemode-usage", "/gamemode <gamemode> [player]")));
                    }
                    break;
            }
        } else {
            sender.sendMessage(instance.colorize(instance.messages.get("no-permission", "no permission")));
        }
        return true;
    }
}
