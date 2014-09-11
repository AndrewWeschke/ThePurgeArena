package me.weschke55.ThePurgeArena.commands.arguments;

import java.util.List;

import me.weschke55.ThePurgeArena.ThePurgeArena;
import me.weschke55.ThePurgeArena.arena.Arena;
import me.weschke55.ThePurgeArena.chat.Helper;
import me.weschke55.ThePurgeArena.commands.messages.MessageHandler;
import me.weschke55.ThePurgeArena.commands.permission.Permission;
import me.weschke55.ThePurgeArena.commands.permission.PermissionHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyArgument {
	
	private CommandSender sender;
	private String[] args;
	
	public LobbyArgument(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}
	
	public boolean execute() {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("§4The lobby argument can only execute as a Player!");
			return true;
		}
		
		Player p = (Player)sender;
		
		if(!PermissionHandler.hasPermission(p, Permission.GAME) && !PermissionHandler.hasPermission(p, Permission.LOBBY)) {
			p.sendMessage(MessageHandler.getMessage("no-permission"));
			return true;
		}
		
		if(args.length == 1) {
			Helper.showLobbyHelpsite(p);
		} else {
			if(args[1].equalsIgnoreCase("delete")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/purge lobby create <NAME>"));
					return true;
				}
				
				if(ThePurgeArena.gameManager.getGame(args[2]) != null) {
					p.sendMessage(MessageHandler.getMessage("prefix") + "§4You must unload the lobby first! /purge lobby unload " + args[2]);
					return true;
				}
				
				if(!ThePurgeArena.database.contains("Games." + args[2])) {
					p.sendMessage(MessageHandler.getMessage("prefix") + "§4Lobby " + args[2] + " does not exist!");
					return true;
				}
				
				ThePurgeArena.database.set("Games." + args[2], null);
				ThePurgeArena.saveDataBase();
				p.sendMessage(MessageHandler.getMessage("prefix") + "You've removed lobby " + args[2] + " successfully!");
				return true;
			} else if(args[1].equalsIgnoreCase("create")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/purge lobby create <NAME>"));
					return true;
				}
				
				ThePurgeArena.gameManager.createGame(p, args[2]);
				return true;
				
				
			} else if(args[1].equalsIgnoreCase("setspawn")) {
				
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/sg lobby setspawn <NAME>"));
					return true;
				}
				
				ThePurgeArena.gameManager.setSpawn(p, args[2]);
				return true;
				
				
			} else if(args[1].equalsIgnoreCase("unload")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/sg lobby unload <NAME>"));
					return true;
				}
				Game game = ThePurgeArena.gameManager.getGame(args[2]);
				if(game == null) {
					p.sendMessage(MessageHandler.getMessage("game-not-loaded").replace("%0%", args[2]));
					return true;
				}
				game.sendMessage(MessageHandler.getMessage("prefix") + "§4§lYour lobby was stopped by an admin!");
				if(game.getState() == GameState.INGAME || game.getState() == GameState.DEATHMATCH) {
					p.sendMessage(MessageHandler.getMessage("prefix") + "§6It my can be that the blocks of arena " + game.getCurrentArena().getName() + " aren't reseted yet. It will reset while loading lobby.");
				}
				
				ThePurgeArena.gameManager.unload(game);
				p.sendMessage(MessageHandler.getMessage("game-success-unloaded").replace("%0%", args[2]));
				ThePurgeArena.signManager.updateSigns();
				return true;
				
				
			} else if(args[1].equalsIgnoreCase("load")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/purge lobby unload <NAME>"));
					return true;
				}
				Game game = ThePurgeArena.gameManager.getGame(args[2]);
				if(game != null) {
					p.sendMessage(MessageHandler.getMessage("game-already-loaded").replace("%0%", args[2]));
					return true;
				}
				
				boolean success = ThePurgeArena.gameManager.load(args[2]);
				
				if(!success) {
					p.sendMessage(MessageHandler.getMessage("game-load-error").replace("%0%", args[2]).replace("%1%", "See console for informations! It may can be that a few arenas have to be reset. When this happens, the game will automatically load after all arenas were reset."));
				} else {
					p.sendMessage(MessageHandler.getMessage("game-success-loaded").replace("%0%", args[2]));
					ThePurgeArena.signManager.updateSigns();
				}
				return true;
				
				
			} else if(args[1].equalsIgnoreCase("reload")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/sg lobby unload <NAME>"));
					return true;
				}
				p.performCommand("purge lobby unload " + args[2]);
				p.performCommand("purge lobby load " + args[2]);
				return true;
				
				
			} else if(args[1].equalsIgnoreCase("list")) {
				if(args.length == 2) {
					p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/purge lobby list <NAME>"));
					return true;
				}
				Game game = ThePurgeArena.gameManager.getGame(args[2]);
				if(game == null) {
					p.sendMessage(MessageHandler.getMessage("game-not-loaded").replace("%0%", args[2]));
					return true;
				}
				List<Arena> arenas = game.getArenas();
				p.sendMessage(MessageHandler.getMessage("prefix") + "Arenas in lobby " + game.getName() + "§8: §7(§b" + arenas.size() + "§7)");
				for(Arena a : arenas) {
					p.sendMessage("§7- §6" + a.getName());
				}
				return true;
			}
			
			
			p.sendMessage(MessageHandler.getMessage("prefix") + "§6Command not found! Type /purge lobby for help!");
			return true;
			
		}
		
		
		return true;
		
	}


}
