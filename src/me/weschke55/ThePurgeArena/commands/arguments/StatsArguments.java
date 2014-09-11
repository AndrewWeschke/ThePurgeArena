package me.weschke55.ThePurgeArena.commands.arguments;

import me.weschke55.ThePurgeArena.commands.messages.MessageHandler;
import me.weschke55.ThePurgeArena.commands.permission.Permission;
import me.weschke55.ThePurgeArena.commands.permission.PermissionHandler;

import org.bukkit.command.CommandSender;

public class StatsArguments {
	
	private CommandSender sender;
	private String[] args;
	
	public StatsArguments(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}
	
	public boolean execute() {
		if(!PermissionHandler.hasPermission(sender, Permission.JOIN)) {
			sender.sendMessage(MessageHandler.getMessage("no-permission"));
			return true;
		}
		
		String name = sender.getName();
		
		if(args.length > 1) {
			name = args[1];
		}
		
		StatisticManager.sendStatistics(sender, name);
		return true;
	}


}
