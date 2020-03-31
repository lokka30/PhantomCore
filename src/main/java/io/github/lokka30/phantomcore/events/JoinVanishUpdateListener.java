package io.github.lokka30.phantomcore.events;

import io.github.lokka30.phantomcore.PhantomCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinVanishUpdateListener implements Listener {

    private PhantomCore instance;

    public JoinVanishUpdateListener(final PhantomCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        instance.manager.updateVanished();
    }
}
