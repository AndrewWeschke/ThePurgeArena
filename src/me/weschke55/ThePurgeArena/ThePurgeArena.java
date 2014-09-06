package me.weschke55.ThePurgeArena;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePurgeArena extends JavaPlugin{
	
	public static ThePurgeArena instance;
	public static FileConfiguration messages, database, signs, reset, chestloot, scoreboard, barapi, kits;
	
	public static ArenaManager arenaManager;
	public static GameManager gameManager;
	public static ChestManager chestManager;
	public static UserManager userManger;
	public static SignManager signManager;
	public static ScoreBoardManager scoreBoardManager;
	
	public static Economy econ;
	
	public static String version = "PurgeArena - Version ";
	
	private static PluginManager pm = Bukkit.getPluginManager();
	
	public void onDisable() {
		if(gameManager != null) {
			for(Game game : gameManager.getGames()) {
				game.kickall();
			}
		}
		DatabaseManager.close();
	}

	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("Worldedit")){
			System.err.println("[PurgeArena] ##########################################################");
			System.err.println("[PurgeArena] ######### NO WORLDEDIT FOUND! DISABLE PLUGIN... ##########");
			System.err.println("[PurgeArena] ##########################################################");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		instance =this;
		version += getDescription().getVersion();
		
		new ConfigLoader().load();
		DatabaseManager.open();
		DatabaseManager.load();
		
		startUpdateChecker();
		
		PermissionHandler.reinitializeDatabse();
		Game.reinitializeDatabase();
		MessageHandler.reload();
		BarAPIManager.reinitializeDatabse();
		
		if(setupEconomy())
			System.out.println("[PurgeArena] Vault found!");
		
		//TEMPORARY
		Util.checkforOutdatedArenaSaveFiles();
		
		chestManager = new ChestManager();
		scoreBoardManager = new ScoreBoardManager();
		arenaManager = new ArenaManager();
		gameManager = new GameManager();
		userManager = new UserManager();
		signManager = new SignManager();
		
		getCommand("purge").setExecutor(new CommandPurge());
		
		pm.registerEvents(new SelectionListener(), this);
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new ChestListener(), this);
		pm.registerEvents(new SignListener(), this);
		pm.registerEvents(new ResetListener(), this);
		pm.registerEvents(new UpdateListener(), this);
		pm.registerEvents(new SpectatorListener(), this);
		pm.registerEvents(new ChatListener(), this);
		
		try {
			new Metrics(this).start();
		} catch (IOException e) {
			System.err.println("[PurgeArena] Cannot load metrics: " + e.getMessage());
		}
		
		if(getWorldEdit() != null) {
			System.out.println("[PurgeArena] Plugin enabled. WorldEdit found!");
		} else {
			System.out.println("[PurgeArena] The commencement of the annual purge has begun....");
		}
		
		signManager.updateSigns();
	}
	

// UPDATE CHECKING

public void startUpdateChecker() {
	Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
		public void run() {
			new UpdateCheck(ThePurgeArena.instance, 61788);
		}
	}, 0L, 216000);
}

// VAULT

private boolean setupEconomy() {
	if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
	}

    return (econ != null);
}

// FILECONFIGURATION SAVE

public static void saveMessages() {
	try {
		messages.save("plugins/ThePurgeArena/messages.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveDataBase() {
	try {
		database.save("plugins/ThePurgeArena/database.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveSigns() {
	try {
		signs.save("plugins/ThePurgeArena/signs.yml");
	} catch(IOException e) {
		e.printStackTrace();
	}
}

public static void saveReset() {
	try {
		reset.save("plugins/ThePurgeArena/reset.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveChests() {
	try {
		chestloot.save("plugins/ThePurgeArena/chestloot.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveScoreboard() {
	try {
		scoreboard.save("plugins/ThePurgeArena/scoreboard.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveBarAPI() {
	try {
		barapi.save("plugins/ThePurgeArena/barapi.yml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void saveKits() {
	try {
		kits.save("plugins/ThePurgeArena/kits.yml");
	} catch(IOException e) {
		e.printStackTrace();
	}
}

// WORLDEDIT

public static WorldEditPlugin getWorldEdit() {
	if(!pm.isPluginEnabled("WorldEdit")) {
		return null;
	} else {
		return (WorldEditPlugin) pm.getPlugin("WorldEdit");
	}
}

// API

public static GameManager getGameManager() {
	return gameManager;
}

public static ArenaManager getArenaManager() {
	return arenaManager;
}

public static ChestManager getChestManager() {
	return chestManager;
}

public static UserManager getUserManager() {
	return userManger;
}

public static SignManager getSignManager() {
	return signManager;
}

public static ScoreBoardManager getScoreboardManager() {
	return scoreBoardManager;
}

public static SurvivalGames getInstance() {
	return instance;
}


}
