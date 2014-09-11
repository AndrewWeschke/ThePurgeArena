package me.weschke55.ThePurgeArena.database;

import me.weschke55.ThePurgeArena.ThePurgeArena;
import me.weschke55.ThePurgeArena.arena.ArenaManager;
import me.weschke55.ThePurgeArena.arena.chest.ChestManager;
import me.weschke55.ThePurgeArena.barapi.BarAPIManager;
import me.weschke55.ThePurgeArena.commands.messages.MessageHandler;
import me.weschke55.ThePurgeArena.commands.permission.PermissionHandler;

public class ConfigReloader {
	
	public static void reloadConfig() {
		ConfigLoader.reloadConfig();
		PermissionHandler.reinitializeUsePermission();
		VotingPhase.reinitializeDatabase();
		Game.reinitializeDatabase();
        PermissionHandler.reinitializeDatabase();
        PlayerListener.reinitializeDatabase();
        ChatListener.reinitializeConfig();
	}
	
	public static void reloadDatabase() {
		ConfigLoader.reloadDatabase();
		GameManager.reinitializeDatabase();
		ArenaManager.reinitializeDatabase();
	}
	
	public static void reloadSigns() {
		ConfigLoader.reloadSigns();
		ThePurgeArena.signManager.reload();
		ThePurgeArena.signManager.updateSigns();
	}
	
	public static void reloadMessage() {
		ConfigLoader.reloadMessages();
		MessageHandler.reload();
	}
	
	public static void reloadChestloot() {
		ConfigLoader.reloadChests();
		ChestManager.reinitializeConfig();
	}
	
	public static void reloadScoreboard() {
		ConfigLoader.reloadScoreboard();
		ScoreBoardManager.reinitializeDatabase();
	}
	
	public static void reloadBarAPI() {
		BarAPIManager.reinitializeDatabase();
	}

}
