package com.github.ukraine1449.stats.Player;

import com.github.ukraine1449.stats.Database.Mongo;
import com.github.ukraine1449.stats.Stats;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CachedPlayer {
    public CachedPlayer(Player p){
        p = player;
        map.put(p, this);
    }
    public Player player;
    public ArrayList<Integer> playerStats = new ArrayList<>();
    public static Map<Player, CachedPlayer> map = new HashMap<>();
    public static CachedPlayer get(Player p) {
        return map.get(p);
    }
    public CachedPlayer remove(Player p) {
        return map.remove(p);
    }
    public void loadFromDB(){
        DBObject query = new BasicDBObject("_id", player.getUniqueId());
        Mongo mc = Stats.instance.mongoConnection;
        DBCursor cursor = mc.loadFromDB(query);
        playerStats.add((Integer) cursor.one().get("Kills"));
        playerStats.add((Integer) cursor.one().get("Walked"));
        playerStats.add((Integer) cursor.one().get("Deaths"));
        playerStats.add((Integer) cursor.one().get("Broken"));
        playerStats.add((Integer) cursor.one().get("Placed"));
        playerStats.add((Integer) cursor.one().get("Expirience"));
    }
    public void loadToDB(){
        if(!playerStats.isEmpty()){
            Mongo mc = Stats.instance.mongoConnection;
            DBObject dbo = new BasicDBObject("_id", player.getUniqueId())
                    .append("Kills", playerStats.get(0))
                    .append("Walked", playerStats.get(1))
                    .append("Deaths", playerStats.get(2))
                    .append("Broken", playerStats.get(3))
                    .append("Placed", playerStats.get(4))
                    .append("Expirience", playerStats.get(5));
            mc.insertToDB(dbo);
        }else{
            System.out.println("Stats list is empty");
        }
    }
}
