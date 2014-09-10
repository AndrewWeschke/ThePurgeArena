package me.weschke55.ThePurgeArena.barapi;

import java.util.HashMap;

import me.weschke55.ThePurgeArena.ThePurgeArena;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class BarAPIManager {
	
public static HashMap<GameState, String> messages = new HashMap<>();
	
	public static void reinitializeDatabase() {
		FileConfiguration c = ThePurgeArena.barapi;
		messages.clear();
		
		if(c != null) {
			for(String key : c.getConfigurationSection("State.").getKeys(false)) {
				String str = ChatColor.translateAlternateColorCodes('&', c.getString("State." + key));
				if(str.length() > 64)
					str = str.substring(0, 64);
				
				if(str.isEmpty())
					continue;
				messages.put(GameState.valueOf(key), str);
			}
			System.out.println("[PurgeArena] " + messages.size() + " bossbar messages loaded!");
		}
			
	}

}
