package com.github.ukraine1449.stats.Player;

import com.github.ukraine1449.stats.Stats;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class CachedPlayer {
    public Player player;
    public ArrayList<Integer> playerStats = new ArrayList<>();
    public static Map<Player, CachedPlayer> map = new HashMap<>();
    public static CachedPlayer get(Player p) {
        return map.get(p);
    }
    public void remove(){//Removes instance from map
        map.remove(player);
    }
    public CachedPlayer(Player p){
        player = p;
        map.put(p, this);
    }//Creates new instance of CachedPlayer and stores in map for usage until player leaves or server shuts down.
    public void loadFromDB(){
        Bukkit.getScheduler().runTaskAsynchronously(getServer().getPluginManager().getPlugin("Stats"), new Runnable() {//Async run
            @Override
            public void run () {
                try { //Gets data for player if present in DB check returned that they are. Gets all data and adds to specific indexes
                    String UUID = player.getUniqueId().toString();
                    Connection con = Stats.instance.getConnection();
                    PreparedStatement statement = con.prepareStatement("SELECT * FROM userList WHERE UUID='"+UUID+"'");
                    ResultSet result = statement.executeQuery();
                    while(result.next()){
                        playerStats.add(0, result.getInt("kills"));
                        playerStats.add(1, result.getInt("walked"));
                        playerStats.add(2, result.getInt("deaths"));
                        playerStats.add(3, result.getInt("mined"));
                        playerStats.add(4, result.getInt("placed"));
                        playerStats.add(5, result.getInt("exp"));
                    }
                    con.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void loadToDB(){
        String UUID = player.getUniqueId().toString();
        if(!playerStats.isEmpty()){
            Bukkit.getScheduler().runTaskAsynchronously(getServer().getPluginManager().getPlugin("Stats"), new Runnable() {
                @Override
                public void run () {
                    try{//On player leave or server shuts down, updates user stats so not to spam with SQL queries
                        Connection con = Stats.instance.getConnection();
                        PreparedStatement Kills = con.prepareStatement("UPDATE userList SET kills="+playerStats.get(0)+" WHERE UUID='"+UUID+"'");
                        PreparedStatement Walked = con.prepareStatement("UPDATE userList SET walked="+playerStats.get(1)+" WHERE UUID='"+UUID+"'");
                        PreparedStatement Deaths = con.prepareStatement("UPDATE userList SET deaths="+playerStats.get(2)+" WHERE UUID='"+UUID+"'");
                        PreparedStatement Broken = con.prepareStatement("UPDATE userList SET mined="+playerStats.get(3)+" WHERE UUID='"+UUID+"'");
                        PreparedStatement Placed = con.prepareStatement("UPDATE userList SET placed="+playerStats.get(4)+" WHERE UUID='"+UUID+"'");
                        PreparedStatement Exp = con.prepareStatement("UPDATE userList SET exp="+playerStats.get(5)+" WHERE UUID='"+UUID+"'");
                        Kills.executeUpdate();
                        Walked.executeUpdate();
                        Deaths.executeUpdate();
                        Broken.executeUpdate();
                        Placed.executeUpdate();
                        Exp.executeUpdate();
                        con.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else{
            System.out.println("Stats list is empty");
        }
    }
}
