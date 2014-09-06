package me.weschke55.ThePurgeArena;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ThePurgeArena extends JavaPlugin{
	
	private static final Logger log = Logger.getLogger("Minecraft");

	public void onEnable()
	{	
		log.info("[PurgeArena] The commencement of the annual purge has begun...");
	}

	public void onDisable()
	{
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		return false;
		
	}
}
