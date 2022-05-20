package com.github.ukraine1449.stats.PlayerEvents;

import com.github.ukraine1449.stats.Player.CachedPlayer;
import com.github.ukraine1449.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {
    Stats plugin;

    public JoinLeave(Stats plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()){
            plugin.playerJoinQuery(player.getUniqueId().toString());
        }
        plugin.onlinePlayers.add(player);
        CachedPlayer cp = new CachedPlayer(player);
        cp.loadFromDB();
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerQuitEvent event){
        Player player = event.getPlayer();
        plugin.onlinePlayers.remove(player);
        CachedPlayer cp = CachedPlayer.get(player);
        cp.loadToDB();
    }
}
