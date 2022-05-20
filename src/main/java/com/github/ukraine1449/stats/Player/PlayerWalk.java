package com.github.ukraine1449.stats.Player;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class PlayerWalk implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWali(PlayerStatisticIncrementEvent e){
        if(e.getStatistic() == Statistic.WALK_ONE_CM){
            Player player = e.getPlayer();
            CachedPlayer cp = CachedPlayer.get(e.getPlayer());
            cp.playerStats.set(1, player.getStatistic(Statistic.WALK_ONE_CM) / 100);
        }
    }
}
