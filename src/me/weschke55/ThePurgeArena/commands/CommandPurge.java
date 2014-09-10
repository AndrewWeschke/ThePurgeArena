package me.weschke55.ThePurgeArena.commands;

import java.util.List;

import me.weschke55.ThePurgeArena.ThePurgeArena;
import me.weschke55.ThePurgeArena.arena.Arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPurge implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("purge")) {
			
			
			if(args.length == 0) {
				sender.sendMessage(MessageHandler.getMessage("prefix") + "Version " + ThePurgeArena.instance.getDescription().getVersion() + " §7§m--§r §ePlugin developed by maker56");
				
				if(PermissionHandler.hasPermission(sender, Permission.JOIN)) {
					sender.sendMessage("§4/§8purge join [LOBBY] §6- Join a game!");
					sender.sendMessage("§4/§8purge leave §6- Leave a game!");
					sender.sendMessage("§4/§8purge vote <ID> §6- Vote for an arena!");
				}
				
				sender.sendMessage("§4/§8purge stats [NAME] §6- Show you statistics of a player");
				
				if(PermissionHandler.hasPermission(sender, Permission.LIST)) {
					sender.sendMessage("§4/§8purge list §7- §eList of all available lobbys!");
				}
				
				if(PermissionHandler.hasPermission(sender, Permission.START)) {
					sender.sendMessage("§4/§8purge start §7- §eForce a lobby to start!");
				}
				
				if(PermissionHandler.hasPermission(sender, Permission.GAME)) {
					sender.sendMessage("§4/§8purge lobby §7- §eShows the lobby helpsite!");
				}
				
				if(PermissionHandler.hasPermission(sender, Permission.ARENA)) {
					sender.sendMessage("§4/§8purge arena §7- §eShows the arena helpsite!");
				}
				
				if(PermissionHandler.hasPermission(sender, Permission.CONFIG)) {
					sender.sendMessage("§4/§8purge config §7- §eShows the configuration management helpsite!");
				}
			} else {
				
				if(args[0].equalsIgnoreCase("arena")) {
					return new ArenaArgument(sender, args).execute();
					
				} else if(args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("game")) {
					return new LobbyArgument(sender, args).execute();
				} else if(args[0].equalsIgnoreCase("stats")) {
					return new StatsArgument(sender, args).execute();
				} else if(args[0].equalsIgnoreCase("config")) {
					return new ConfigArgument(sender, args).execute();
				}
				
				// JOIN
				if(args[0].equalsIgnoreCase("join")) {
					
					Player p = (Player)sender;
					
					if(args.length == 1 && ThePurgeArena.gameManager.getGames().size() == 1) {
						ThePurgeArena.userManger.joinGame(p, ThePurgeArena.gameManager.getGames().get(0).getName());
						return true;
					} else if(args.length == 1) {
						p.sendMessage(MessageHandler.getMessage("game-must-enter").replace("%0%", "/purge join <GAMENAME>"));
						return true;
					}
					
					ThePurgeArena.userManger.joinGame(p, args[1]);
					return true;
					
				// LEAVE
				} else if(args[0].equalsIgnoreCase("leave")) {
					Player p = (Player)sender;
					UserState user = ThePurgeArena.userManger.getUser(p.getName());
					
					if(user == null) {
						user = ThePurgeArena.userManger.getSpectator(p.getName());
						if(user == null) {
							p.sendMessage(MessageHandler.getMessage("leave-not-playing"));
							return true;
						}
					}
					
					Game game = user.getGame();
					
					if(game.getState() != GameState.INGAME && game.getState() != GameState.DEATHMATCH) {
						ThePurgeArena.userManger.leaveGame(p);
						return true;
					} else if(user instanceof User){
						IngamePhase ip = game.getIngamePhrase();
						ip.killUser((User)user, null, true, false);
					}
					
					return true;
					
				// VOTE
				} else if(args[0].equalsIgnoreCase("vote")) {
					Player p = (Player)sender;
					
					if(!ThePurgeArena.userManger.isPlaying(p.getName())) {
						p.sendMessage(MessageHandler.getMessage("leave-not-playing"));
						return true;
					}
					
					if(args.length == 1) {
						p.sendMessage(MessageHandler.getMessage("cmd-error").replace("%0%", "You must specify a Arena-ID!"));
						return true;
					}
					
					User user = ThePurgeArena.userManger.getUser(p.getName());
					
					if(!user.getGame().isVotingEnabled()) {
						p.sendMessage(MessageHandler.getMessage("game-no-voting-enabled"));
						return true;
					}
					
					if(user.getGame().getState() != GameState.VOTING) {
						p.sendMessage(MessageHandler.getMessage("game-no-vote"));
						return true;
					}
					
					VotingPhase vp = user.getGame().getVotingPhrase();
					
					if(!vp.canVote(p.getName())) {
						p.sendMessage(MessageHandler.getMessage("game-already-vote"));
						return true;
					}
					
					
					int mapid = 0;
					
					try {
						mapid = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						p.sendMessage(MessageHandler.getMessage("cmd-error").replace("%0%", args[1] + " ist not a valid number!"));
						return true;
					}
					

					Arena arena = vp.vote(p, mapid);
					
					if(arena == null) {
						p.sendMessage(MessageHandler.getMessage("game-bad-vote"));
						return true;
					}
					return true;
					
				// LIST
				} else if(args[0].equalsIgnoreCase("list")) {
					if(!PermissionHandler.hasPermission(sender, Permission.LIST)) {
						sender.sendMessage(MessageHandler.getMessage("no-permission"));
						return true;
					}
					
					List<Game> games = ThePurgeArena.gameManager.getGames();
					sender.sendMessage(MessageHandler.getMessage("prefix") + "List of all loaded lobbys§4: §6(§4" + games.size() + "§6)");
					for(Game game : games) {
						sender.sendMessage("§4- §6" + game.getName() + "§4: §6" + game.getState().toString() + " §4(§6" + game.getPlayingUsers() + "§4/§6" + game.getMaximumPlayers() + "§4)");
					}
					return true;
					
				// FORCE START
				} else if(args[0].equalsIgnoreCase("start")) {
					if(!PermissionHandler.hasPermission(sender, Permission.START)) {
						sender.sendMessage(MessageHandler.getMessage("no-permission"));
						return true;
					}
					Player p = (Player) sender;
					
					Game game = null;
					if(args.length > 1) {
						game = ThePurgeArena.gameManager.getGame(args[1]);
					} else {
						UserManager um = ThePurgeArena.userManger;
						User u = um.getUser(p.getName());
						if(u != null) {
							game = u.getGame();
						}
					}
					
					if(game == null) {
						p.sendMessage(MessageHandler.getMessage("game-not-found").replace("%0%", (args.length <= 1 ? "" : args[1])));
						return true;
					}
					
					game.forceStart(p);
					return true;
					
				// DEBUG
				} else if(args[0].equalsIgnoreCase("debug")) {
					if(!PermissionHandler.hasPermission(sender, Permission.LOBBY)) {
						sender.sendMessage(MessageHandler.getMessage("no-permission"));
						return true;
					}
					boolean nV = !Util.debug;
					Util.debug = nV;
					sender.sendMessage(MessageHandler.getMessage("prefix") + "Debug Mode§4: " + (nV ? "§6ENABLED" : "§4DISABLED"));
					return true;
					
				}
				
				sender.sendMessage(MessageHandler.getMessage("prefix") + "§4Command not found! Type /purge for help!");
				return true;
			}
			
			
		}
		
		return false;
	}
	

}
