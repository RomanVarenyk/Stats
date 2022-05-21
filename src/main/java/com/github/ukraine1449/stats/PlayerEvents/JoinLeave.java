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
        plugin.playerJoinQuery(player.getUniqueId().toString());//Every time a player joins, adds to SQL DB, just in case with null (0) values.
        if(!plugin.UUIDs.contains(player.getUniqueId().toString())){ //checks if player hasnt played before or isnt in the pre-loaded db contents list.
            plugin.onlinePlayers.add(player); //Adds player to online list, creates new cached player object and sets stat values to 0, to be modified on stat update.
            CachedPlayer cp = new CachedPlayer(player);
            cp.playerStats.add(0, 0);
            cp.playerStats.add(1, 0);
            cp.playerStats.add(2, 0);
            cp.playerStats.add(3, 0);
            cp.playerStats.add(4, 0);
            cp.playerStats.add(5, 0);
        }else{
            //same thing as above, except instead of setting 0 stat vals it loads from db
            plugin.onlinePlayers.add(player);
            CachedPlayer cp = new CachedPlayer(player);
            cp.loadFromDB();
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event){//On player leave, remove from online player list, and load all data to DB.
        Player player = event.getPlayer();
        plugin.onlinePlayers.remove(player);
        CachedPlayer cp = CachedPlayer.get(player);
        cp.loadToDB();
        cp.remove();
    }
}
