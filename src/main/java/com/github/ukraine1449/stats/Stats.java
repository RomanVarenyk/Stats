package com.github.ukraine1449.stats;

import com.github.ukraine1449.stats.Database.Mongo;
import com.github.ukraine1449.stats.Player.CachedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public final class Stats extends JavaPlugin {
    public static Stats instance;
    public Mongo mongoConnection;
    public ArrayList<Player> onlinePlayers =  new ArrayList<>();
    @Override
    public void onEnable() {
        instance =this;
        try {
            createTableUserList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        for(Player p : onlinePlayers){
            CachedPlayer cp = CachedPlayer.get(p);
            cp.loadToDB();
            onlinePlayers.remove(p);
        }
        mongoConnection.close();
    }
    private void loadMongo() {
        try {
            mongoConnection = new Mongo(getConfig().getString("Mongo.host"), getConfig().getInt("Mongo.port"), getConfig().getString("Mongo.database"), getConfig().getString("Mongo.collection"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws Exception{
        String ip = getConfig().getString("ip");
        String password = getConfig().getString("password");
        String username = getConfig().getString("username");
        String dbn = getConfig().getString("database name");//these 4 strings get the login info from config.yml file, and use that for DB connections
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://"+ ip + ":3306/" + dbn;
            System.out.println(url);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        }catch(Exception e){
            System.out.println("Unable to connect to SQL server.");
        }
        return null;
    }
    public void createTableUserList()throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS userList(UUID varchar(255), PRIMARY KEY (UUID))");
            create.executeUpdate();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void playerJoinQuery(String UUID){
        try{//executed when a player is joining in playerJoinEvent, basically if the players UUID isnt already in the database it adds it with all stats of 0
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO userList(UUID) VALUES ('"+UUID+"')ON DUPLICATE KEY UPDATE UUID='"+UUID+"'");
            posted.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
//TODO Stats: Kills, Deaths, Expirience gained total, Blocks mined, Blocks placed, Walked