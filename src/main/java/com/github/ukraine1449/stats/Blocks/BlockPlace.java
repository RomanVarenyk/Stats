package com.github.ukraine1449.stats.Blocks;

import com.github.ukraine1449.stats.Player.CachedPlayer;
import com.github.ukraine1449.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    Stats plugin;

    public BlockPlace(Stats plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)// Sets the block break counter up one whenever block is placed by player.
        public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        CachedPlayer cp = CachedPlayer.get(player);
        cp.playerStats.set(4, cp.playerStats.get(4)+1);
    }
}
