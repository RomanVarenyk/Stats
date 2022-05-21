package com.github.ukraine1449.stats.Blocks;

import com.github.ukraine1449.stats.Player.CachedPlayer;
import com.github.ukraine1449.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    Stats plugin;

    public BlockBreak(Stats plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        CachedPlayer cp = CachedPlayer.get(player);
        cp.playerStats.set(3, cp.playerStats.get(3)+1);
    }
}
//