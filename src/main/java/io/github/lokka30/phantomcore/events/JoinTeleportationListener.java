package io.github.lokka30.phantomcore.events;

import io.github.lokka30.phantomcore.PhantomCore;
import io.github.lokka30.phantomcore.utils.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinTeleportationListener implements Listener {

    private PhantomCore instance;

    public JoinTeleportationListener(final PhantomCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final boolean hasPlayedBefore = player.hasPlayedBefore();

        if (instance.settings.get("join-teleportation.teleport-to-spawn-on-join.enabled", true)) {
            final boolean isOnlyOnFirstJoin = instance.settings.get("join-teleportation.teleport-to-spawn-on-join.only-first-join", true);
            if ((hasPlayedBefore && !isOnlyOnFirstJoin) || (!hasPlayedBefore && isOnlyOnFirstJoin)) {
                final double x = instance.data.get("spawn.x", 0);
                final double y = instance.data.get("spawn.y", 0);
                final double z = instance.data.get("spawn.z", 0);
                final float yaw = instance.data.get("spawn.yaw", 0);
                final float pitch = instance.data.get("spawn.pitch", 0);
                final String worldName = instance.data.get("spawn.world", null);

                if (worldName == null || Bukkit.getWorld(worldName) == null) {
                    instance.log(LogLevel.WARNING, "Setting 'Teleport to Spawn on Join' (location: 'join-teleportation.teleport-to-spawn-on-join') is enabled but spawn has either not been set or its location is no longer valid.");
                } else {
                    player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
                }
            }
        } else if (instance.settings.get("join-teleportation.teleport-to-warp-on-join.enabled", false)) {
            final boolean isOnlyOnFirstJoin = instance.settings.get("join-teleportation.teleport-to-warp-on-join.only-first-join", true);
            if ((hasPlayedBefore && !isOnlyOnFirstJoin) || (!hasPlayedBefore && isOnlyOnFirstJoin)) {
                final String warpName = instance.settings.get("join-teleportation.teleport-to-warp-on-join.warp", null);
                if (warpName == null) {
                    instance.log(LogLevel.WARNING, "Setting 'Teleport to Warp on Join' (location: 'join-teleportation.teleport-to-warp-on-join') is enabled but the warp name isn't specified.");
                } else {
                    final double x = instance.data.get("warps." + warpName + ".x", 123.45D);
                    final double y = instance.data.get("warps." + warpName + ".y", 123.45D);
                    final double z = instance.data.get("warps." + warpName + ".z", 123.45D);
                    final float yaw = instance.data.get("warps." + warpName + ".yaw", -180F);
                    final float pitch = instance.data.get("warps." + warpName + ".pitch", 2.0F);
                    final String worldName = instance.data.get("warps." + warpName + ".world", null);
                    if (worldName == null) {
                        instance.log(LogLevel.WARNING, "Setting 'Teleport to Warp on Join' (location: 'join-teleportation.teleport-to-warp-on-join') is enabled but the world name isn't specified.");
                    } else if (Bukkit.getWorld(worldName) == null) {
                        instance.log(LogLevel.WARNING, "Setting 'Teleport to Warp on Join' (location: 'join-teleportation.teleport-to-warp-on-join') is enabled but the world specified doesn't exist.");
                    } else {
                        player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
                    }
                }
            }
        } else if (instance.settings.get("join-teleportation.teleport-to-location-on-join.enabled", false)) {
            final boolean isOnlyOnFirstJoin = instance.settings.get("join-teleportation.teleport-to-location-on-join.only-first-join", true);
            if ((hasPlayedBefore && !isOnlyOnFirstJoin) || (!hasPlayedBefore && isOnlyOnFirstJoin)) {
                final double x = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.x", 123.45D);
                final double y = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.y", 123.45D);
                final double z = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.z", 123.45D);
                final float yaw = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.yaw", -180F);
                final float pitch = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.pitch", 2.0F);
                final String worldName = instance.settings.get("join-teleportation.teleport-to-location-on-join.location.world", null);
                if (worldName == null) {
                    instance.log(LogLevel.WARNING, "Setting 'Teleport to Location on Join' (location: 'join-teleportation.teleport-to-location-on-join') is enabled but the world name isn't specified.");
                } else if (Bukkit.getWorld(worldName) == null) {
                    instance.log(LogLevel.WARNING, "Setting 'Teleport to Location on Join' (location: 'join-teleportation.teleport-to-location-on-join') is enabled but the world specified doesn't exist.");
                } else {
                    player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
                }
            }
        }
    }
}
