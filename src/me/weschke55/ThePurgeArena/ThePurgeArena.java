package me.weschke55.ThePurgeArena;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePurgeArena extends JavaPlugin{
	
	private static final Logger log = Logger.getLogger("Minecraft");

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
			System.err.println("[SurvivalGames] Cannot load metrics: " + e.getMessage());
		}
		
		if(getWorldEdit() != null) {
			System.out.println("[PurgeArena] Plugin enabled. WorldEdit found!");
		} else {
			System.out.println("[PurgeArena] The commencement of the annual purge has begun....");
		}
		
		signManager.updateSigns();
	}

	
	{	
		
}


	public void onDisable()
	{
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		return false;
		
	}
}
