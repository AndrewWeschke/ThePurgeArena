package me.weschke55.ThePurgeArena.commands.arguments;

import me.weschke55.ThePurgeArena.chat.Helper;
import me.weschke55.ThePurgeArena.commands.messages.MessageHandler;
import me.weschke55.ThePurgeArena.commands.permission.Permission;
import me.weschke55.ThePurgeArena.commands.permission.PermissionHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigArgument {
	
	private CommandSender sender;
	private String[] args;
	
	public ConfigArgument(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}
	
	public boolean execute() {
		if(!(sender instanceof Player)) {
			sender.sendMessage("�4The config argument can only execute as a Player!");
			return true;
		}
		
		Player p = (Player)sender;
		
		if(!PermissionHandler.hasPermission(p, Permission.CONFIG)) {
			p.sendMessage(MessageHandler.getMessage("no-permission"));
			return true;
		}
		
		if(args.length == 1) {
			Helper.showConfigHelpsite(p);
		} else {
			if(args[1].equalsIgnoreCase("reload")) {
				if(args.length == 2) {
					ConfigReloader.reloadMessage();
					ConfigReloader.reloadConfig();
					ConfigReloader.reloadDatabase();
					ConfigReloader.reloadSigns();
					ConfigReloader.reloadChestloot();
					ConfigReloader.reloadScoreboard();
					ConfigReloader.reloadBarAPI();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded all configuration files successfully!");
					return true;
				}
				String con = args[2];
				if(con.equalsIgnoreCase("messages")) {
					ConfigReloader.reloadMessage();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the messages.yml successfully!");
			
				} else if(con.equalsIgnoreCase("signs")) {
					ConfigReloader.reloadSigns();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the signs.yml successfully!");
					
				} else if(con.equalsIgnoreCase("database")) {
					ConfigReloader.reloadDatabase();
					p.sendMessage(MessageHandler.getMessage("prefix") + "The settings are applied to a lobby after a lobby-reload or the end of a survival game.");
				} else if(con.equalsIgnoreCase("config")) {
					ConfigReloader.reloadConfig();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the config.yml successfully!");
				} else if(con.equalsIgnoreCase("chestloot")) {
					ConfigReloader.reloadChestloot();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the chestloot.yml successfully!");
				} else if(con.equalsIgnoreCase("scoreboard")) {
					ConfigReloader.reloadScoreboard();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the scoreboard.yml successfully!");
				} else if(con.equalsIgnoreCase("barapi")) {
					if(!Bukkit.getPluginManager().isPluginEnabled("BarAPI")) {
						p.sendMessage(MessageHandler.getMessage("prefix") + "�4The plugin BarAPI isn't loaded!");
						return true;
					}
					ConfigReloader.reloadBarAPI();
					p.sendMessage(MessageHandler.getMessage("prefix") + "You've reloaded the barapi.yml successfully!");
				} else {
					p.sendMessage(MessageHandler.getMessage("config-error-name").replace("%0%", "/purge config reload [MESSAGES/SIGNS/DATABASE/CONFIG/CHESTLOOT]"));
					return true;
				}
				
			}
		}
		return true;
	}


}
