package com.libus.nohunger.commands;

import com.libus.nohunger.NoHunger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class ToggleHunger implements CommandExecutor {

    private NoHunger plugin;
    private FileConfiguration playerStates;
    private FileConfiguration config;
    private List<String> playerList;

    public ToggleHunger(NoHunger pl){
        plugin = pl;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();

        playerStates = plugin.playerStates;
        config = plugin.config;

        if(!player.hasPermission("nohunger.toggle")){
            String permissionMessage = config.getString("no_permission").replace("{permission}", "nohunger.toggle");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', permissionMessage));
            return true;
        }

        playerList = playerStates.getStringList("players");
        if(playerList.contains(playerUUID)){
            playerList.remove(playerUUID);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hunger_enabled")));
        }
        else {
            playerList.add(playerUUID);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hunger_disabled")));
        }
        playerStates.set("players", playerList);
        try {
            playerStates.save(plugin.playerStatesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
