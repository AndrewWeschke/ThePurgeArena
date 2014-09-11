package me.weschke55.ThePurgeArena.commands.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

import me.weschke55.ThePurgeArena.ThePurgeArena;

public class MessageHandler {

	private static HashMap<String, String> messages = new HashMap<>();
	private static List<String> withoutPrefix = new ArrayList<>();
	
	public static void reload() {
		messages.clear();
		for(String key : ThePurgeArena.messages.getConfigurationSection("").getKeys(false)) {
			messages.put(key, replaceColors(ThePurgeArena.messages.getString(key)));
		}
		
		withoutPrefix.add("prefix");
		withoutPrefix.add("stats-kills");
		withoutPrefix.add("stats-deaths");
		withoutPrefix.add("stats-kdr");
		withoutPrefix.add("stats-wins");
		withoutPrefix.add("stats-played");
		withoutPrefix.add("stats-points");
		withoutPrefix.add("stats-footer");
		
		System.out.println("[SurvivalGames] " + messages.size() + " messages loaded!");
	}
	
	public static String getMessage(String name) {
		if(messages.containsKey(name)) {
			if(withoutPrefix.contains(name)) {
				return messages.get(name);
			} else {
				return messages.get("prefix") + messages.get(name);
			}
		} else {
			return "§cMessage not found!";
		}
	}
	
	public static String replaceColors(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	
}
