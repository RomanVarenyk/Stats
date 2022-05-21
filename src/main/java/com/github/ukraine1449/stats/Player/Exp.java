package com.github.ukraine1449.stats.Player;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class Exp implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExpGain(PlayerLevelChangeEvent e){
        Player player = e.getPlayer();
        CachedPlayer cp = CachedPlayer.get(e.getPlayer());
        if(cp.playerStats.get(5) < player.getLevel()){
            cp.playerStats.set(5, player.getLevel());
        }//Gets if current level is highest then stored, if it is, then update it.
    }
}
