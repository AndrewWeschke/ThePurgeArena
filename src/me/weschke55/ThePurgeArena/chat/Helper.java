package me.weschke55.ThePurgeArena.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Helper {
	
	public static void showLobbyHelpsite(Player p) {
		p.sendMessage(MessageHandler.getMessage("prefix") + "Lobby Management §7§m---§r §6Helpsite");
		new JSONMessage("§4/§8purge lobby create <LOBBYNAME> §7- §eCreates a game with the specify name!").color(ChatColor.RED).suggest("/purge lobby create ").send(p);
		new JSONMessage("§4/§8purge lobby unload <LOBBYNAME> §7- §eUnload a lobby!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge lobby unload ").send(p);
		new JSONMessage("§4/§8purge lobby load <LOBBYNAME> §7- §eLoad a lobby!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge lobby load ").send(p);
		new JSONMessage("§4/§8purge lobby reload <LOBBYNAME> §7- §eUnload and load a lobby!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge lobby reload ").send(p);
		new JSONMessage("§4/§8purge lobby list <LOBBYNAME> §7- §eList of all loaded arenas in a lobby!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge lobby list ").send(p);
		new JSONMessage("§4/§8purge lobby delete <LOBBYNAME> §7- §eDeletes a lobby from file!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge lobby delete ").send(p);
		p.sendMessage("");
		new JSONMessage("§2Need more help? Click here!").tooltip("Click here to open the official bukkit site!").link("http://dev.bukkit.org/bukkit-plugins/purgearena/").send(p);
	}
	
	public static void showConfigHelpsite(Player p) {
		p.sendMessage(MessageHandler.getMessage("prefix") + "Configuration §7§m---§r §2Helpsite");
		new JSONMessage("§4/§8purge config reload [MESSAGES/SIGNS/DATABASE/CONFIG/CHESTLOOT/SCOREBOARD/BARAPI] §7- §eReloads the specify config!").color(ChatColor.RED).tooltip("Prepare command").suggest("/sg config reload ").send(p);
		p.sendMessage("");
		new JSONMessage("§2Need more help? Click here!").tooltip("Click here to open the official bukkit site!").link("http://dev.bukkit.org/bukkit-plugins/purgearena/").send(p);
	}
	
	public static void showArenaHelpsite(Player p) {
		p.sendMessage(MessageHandler.getMessage("prefix") + "Arena Management §7§m---§r §6Helpsite");
		new JSONMessage("§4/§8purge arena create <LOBBYNAME> <ARENA NAME> §7- §eCreates an arena in a specify game!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena create ").send(p);
		new JSONMessage("§4/§8purge arena select <LOBBYNAME> <ARENA NAME> §7- §eSelects the specify arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena select ").send(p);
		new JSONMessage("§4/§8purge arena tools §7- §eGives you the Arena-Selection Tools!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena tools").send(p);
		new JSONMessage("§4/§8purge arena check §7- §eShows whats even need to be done on the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena check").send(p);
		new JSONMessage("§4/§8purge arena addspawn §7- §eAdd a Spawn on the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena addspawn").send(p);
		new JSONMessage("§4/§8purge arena removespawn <SPAWNID> §7- §eRemoves a spawn from the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena removespawn ").send(p);
		new JSONMessage("§4/§8purge arena deathmatch §7- §eDe/activate the Deathmatch on the spelected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena deathmatch").send(p);
		new JSONMessage("§4/§8purge arena deathmatch add §7- §eAdd a Deathmatch-Spawn on the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena deathmatch add").send(p);
		new JSONMessage("§4/§8purge arena deathmatch remove <SPAWNID> §7- §eRemove an Spawn on the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena deathmatch remove ").send(p);
		new JSONMessage("§4/§8purge arena deathmatch domemiddle §7- §eSet the middle of the invisible dome of the deathmatch arena").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena deathmatch domemiddle").send(p);
		new JSONMessage("§4/§8purge arena deathmatch domeradius [RADIUS] §7- §eSet/View the radius of the ivsibile deathmatch dome").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena deathmatch domeradius").send(p);
		new JSONMessage("§4/§8purge arena finish §7- §eFinished the create-setup on the selected arena!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena finish").send(p);
		new JSONMessage("§4/§8purge arena save §7- §eSaves the blocks of an arena to file for map reset!").color(ChatColor.RED).tooltip("Prepare command").suggest("/purge arena save").send(p);
		new JSONMessage("§4/§8purge arena delete §7- §eRemoves an arena in a lobby!").color(ChatColor.RED).tooltip("Prepare command").suggest("/sg arena delete").send(p);
		p.sendMessage("");
		new JSONMessage("§7Need more help? Click here!").tooltip("Click here to open the official bukkit site!").link("http://dev.bukkit.org/bukkit-plugins/purgearena/").send(p);
	}

}
