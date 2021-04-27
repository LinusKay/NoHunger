package com.libus.testplugin;

import com.libus.testplugin.commands.Feed;
import com.libus.testplugin.commands.Heal;
import com.libus.testplugin.events.TestEvents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable(){
        getCommand("heal").setExecutor(new Heal());
        getCommand("feed").setExecutor(new Feed());
        getServer().getPluginManager().registerEvents(new TestEvents(), this);
        System.out.println(ChatColor.GREEN + "[TestPlugin] Plugin is enabled");
    }

    @Override
    public void onDisable(){
        System.out.println(ChatColor.RED + "[TestPlugin] Plugin is disabled");

    }
}
