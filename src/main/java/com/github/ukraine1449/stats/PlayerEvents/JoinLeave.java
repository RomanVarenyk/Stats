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
            plugin.onlinePlayers.add(player);
            CachedPlayer cp = new CachedPlayer(player);
            cp.playerStats.add(0, 0);
            cp.playerStats.add(1, 0);
            cp.playerStats.add(2, 0);
            cp.playerStats.add(3, 0);
            cp.playerStats.add(4, 0);
            cp.playerStats.add(5, 0);
        }else{
            plugin.onlinePlayers.add(player);
            CachedPlayer cp = new CachedPlayer(player);
            cp.loadFromDB();
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        plugin.onlinePlayers.remove(player);
        CachedPlayer cp = CachedPlayer.get(player);
        cp.loadToDB();
    }
}
