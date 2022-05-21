package com.github.ukraine1449.stats.Menu;

import com.github.ukraine1449.stats.Player.CachedPlayer;
import com.github.ukraine1449.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StatsCommand implements CommandExecutor {
Stats plugin;

    public StatsCommand(Stats plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 0){//Check if player is talking about themselves or another player. else is for self
                Player target = Bukkit.getPlayerExact(args[0]);
                Player player = (Player) sender;
                player.openInventory(getPlayerData(target));
            }else{
                Player player = (Player) sender;
                player.openInventory(getPlayerData(player));
            }
        }
        return false;
    }
    public ItemStack createItemStack(Material material, String name, String description){//Method for creating itemstacks because its too much of a pain in the ass with meta.
        ItemStack itemStack = new ItemStack(material);
        ItemMeta isl = itemStack.getItemMeta();
        isl.setDisplayName(name);
        ArrayList<String> isla = new ArrayList<>();
        isla.add(description);
        isl.setLore(isla);
        itemStack.setItemMeta(isl);
        return itemStack;
    }
    public Inventory getPlayerData(Player player){ //Gets and creates inventory for your or other player stats, method instead of being in class of event because of dual usage
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.BLUE+"Player stats");
        CachedPlayer cp = CachedPlayer.get(player);
        inv.addItem((createItemStack(Material.PLAYER_HEAD, ChatColor.BLUE+player.getDisplayName(), ChatColor.BLUE+player.getDisplayName()+" Stats")));
        inv.addItem(createItemStack(Material.ZOMBIE_HEAD, ChatColor.GREEN+"Entities killed", cp.playerStats.get(0) + " Entities killed"));
        inv.addItem(createItemStack(Material.LEATHER_BOOTS, ChatColor.GREEN+"Blocks walked", cp.playerStats.get(1) + " Blocks walked"));
        inv.addItem(createItemStack(Material.CREEPER_HEAD, ChatColor.GREEN+"Times died", cp.playerStats.get(2) + " Times died"));
        inv.addItem(createItemStack(Material.RED_TERRACOTTA, ChatColor.GREEN+"Blocks broken", cp.playerStats.get(3) + " Blocks broken"));
        inv.addItem(createItemStack(Material.GREEN_TERRACOTTA, ChatColor.GREEN+"Blocks placed", cp.playerStats.get(4) + " Blocks placed"));
        inv.addItem(createItemStack(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN+"Highest EXP level", "Highest EXP level " + cp.playerStats.get(5)));
        return inv;
    }
}
