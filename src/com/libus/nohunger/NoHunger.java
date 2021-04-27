package com.libus.nohunger;

import com.libus.nohunger.commands.ToggleHunger;
import com.libus.nohunger.event.HungerEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NoHunger extends JavaPlugin {
    public File playerStatesFile = new File(getDataFolder(), "playerstates.yml");
    public FileConfiguration playerStates = YamlConfiguration.loadConfiguration(playerStatesFile);
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable(){
        getCommand("togglehunger").setExecutor(new ToggleHunger(this));
        getServer().getPluginManager().registerEvents(new HungerEvent(this), this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        System.out.println("[NoHunger] Plugin enabled.");
    }

    @Override
    public void onDisable(){
        System.out.println("[NoHunger] Plugin disabled.");
    }
}
