package com.github.ukraine1449.stats.Player;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerWalk implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWalk(PlayerMoveEvent e){
            Player player = e.getPlayer();
            CachedPlayer cp = CachedPlayer.get(player);
            cp.playerStats.set(1, player.getStatistic(Statistic.WALK_ONE_CM) / 100);
    }//Every time a player moves, take CM and divide by 100 (returns int, instead of double, ik weird), sets to the arraylist.
}
