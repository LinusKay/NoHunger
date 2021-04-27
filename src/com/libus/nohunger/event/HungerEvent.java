package com.libus.nohunger.event;

import com.libus.nohunger.NoHunger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class HungerEvent implements Listener {

    private static NoHunger plugin;
    private static FileConfiguration playerStates;
    private static List<String> playerList;
    private static FileConfiguration config;

    public HungerEvent(NoHunger pl){
        plugin = pl;
    }

    @EventHandler
    public static void onHungerLoss(FoodLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)){ return; }
        Player player = (Player) event.getEntity();
        String playerUUID = player.getUniqueId().toString();

        playerStates = plugin.playerStates;
        playerList = playerStates.getStringList("players");
        if(playerList.contains(playerUUID)){
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event){
        Player player = (Player) event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        playerStates = plugin.playerStates;
        playerList = playerStates.getStringList("players");
        config = plugin.config;

        if(Boolean.parseBoolean(config.getString("message_on_join"))){
            if(playerList.contains(playerUUID)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hunger_disabled")));
            }
            else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hunger_enabled")));
            }
        }
    }
}
