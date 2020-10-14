package shadowhunger.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor, Listener {
  
	public void onEnable() {
	    registerEvents();
	    registerCommands();
	    registerConfig();
	}
  
	public void registerEvents() { 
		getServer().getPluginManager().registerEvents(this, this); 
	}
  
	public void registerCommands() {
		this.getCommand("togglehunger");
	}
	
	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	List<UUID> nothungry = new ArrayList<UUID>();

//Get messages/options from config.yml
		String prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix"));
		String enabled = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("disabled"));
		String disabled = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("enabled"));
		String permission = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-permission"));
		String togglenotify = this.getConfig().getString("toggle-notify");
	
  
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.hasPermission("shadowhunger.toggle")) {
			p.sendMessage(prefix + permission.replace("PERMISSION", "shadowhunger.toggle"));
			return false;
		}
		
//if player has hunger toggled off		
		if(nothungry.contains(p.getUniqueId())) {
			nothungry.remove(p.getUniqueId());
			if(togglenotify == "true") {
				p.sendMessage(prefix + enabled);
			}
			return true;
		}
		else {
//if player has hunger toggled on
		nothungry.add(p.getUniqueId());
		if(togglenotify == "true") {
			p.sendMessage(prefix + disabled);
		}
		return true;
		}
	}
	
	
	@EventHandler
	public void onLoseHunger(FoodLevelChangeEvent e) {
	//If entity is player
	    if (e.getEntity() instanceof Player){
	    //if player has hunger toggled off
		    if(nothungry.contains(e.getEntity().getUniqueId())) {
		    //set hunger to full
			    e.setFoodLevel(20);
	    	}
    }
  }
}
