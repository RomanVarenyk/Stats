package com.github.ukraine1449.stats.Death;

import com.github.ukraine1449.stats.Player.CachedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Kills implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){ // On player death, add one to death counter. Doesnt matter if by accident or by another entity/player
        Player player = event.getEntity();
        CachedPlayer cp = CachedPlayer.get(player);
        cp.playerStats.set(2, cp.playerStats.get(2)+1);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKill(EntityDeathEvent event){//Whenever anything killed by player, added to kill counter.
        if(event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();
            CachedPlayer cp = CachedPlayer.get(player);
            cp.playerStats.set(0, cp.playerStats.get(0)+1);
        }
    }
}
