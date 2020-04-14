package io.github.lokka30.phantomcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private PhantomCore instance;

    public Manager(final PhantomCore instance) {
        this.instance = instance;
    }

    public int getDistanceBetween(final Player player1, final Player player2) {
        return (int) player1.getLocation().distance(player2.getLocation());
    }

    public void updateVanished() {
        ArrayList<Player> vanishedPlayers = new ArrayList<>();
        ArrayList<Player> nonVanishedPlayers = new ArrayList<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            final String uuid = onlinePlayer.getUniqueId().toString();
            if (instance.data.get("players." + uuid + ".vanished", false)) {
                vanishedPlayers.add(onlinePlayer);
            } else {
                nonVanishedPlayers.add(onlinePlayer);
            }
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            for (Player vanishedPlayer : vanishedPlayers) {
                onlinePlayer.hidePlayer(instance, vanishedPlayer);
            }
            for (Player nonVanishedPlayer : nonVanishedPlayers) {
                onlinePlayer.showPlayer(instance, nonVanishedPlayer);
            }
        }
    }

    public HashMap<Player, Integer> getNearbyPlayers(final Player player) {
        HashMap<Player, Integer> nearbyPlayers = new HashMap<>();

        //radius=200 blocks
        for (final Entity entity : player.getNearbyEntities(200, 256, 200)) {
            if (entity instanceof Player) {
                nearbyPlayers.put((Player) entity, getDistanceBetween(player, (Player) entity));
            }
        }

        return nearbyPlayers;
    }
}
