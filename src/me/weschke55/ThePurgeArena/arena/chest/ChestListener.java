package me.weschke55.ThePurgeArena.arena.chest;

import me.weschke55.ThePurgeArena.ThePurgeArena;
import me.weschke55.ThePurgeArena.arena.Arena;
import net.minecraft.util.com.mojang.authlib.yggdrasil.response.User;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestListener implements Listener {
	
	private UserManager um = ThePurgeArena.userManger;
	private ChestManager cm = ThePurgeArena.chestManager;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			
			if(um.isPlaying(p.getName())) {
				
				User user = um.getUser(p.getName());
				Game game = user.getGame();
				
				if(game.getState() != GameState.INGAME && game.getState() != GameState.DEATHMATCH) {
					event.setCancelled(true);
					return;
				}
				
				Arena arena = game.getCurrentArena();
				Block b = event.getClickedBlock();
				Material type = b.getType();
				
				if((type == arena.getChestType() && ((arena.getChestData() >= 0 && b.getData() == arena.getChestData()) || arena.getChestData() < 0)) || b.getType() == Material.ENDER_CHEST) {
					Location loc = b.getLocation();
					event.setCancelled(true);
					if(game.isChestRegistered(loc)) {
						p.openInventory(game.getChest(loc).getInventory());
					} else {
						Chest chest = cm.getRandomChest(p, loc, type == Material.ENDER_CHEST);
						game.registerChest(chest);
						user.setCurrentChest(chest);
						b.getState().update(true);
						
						p.openInventory(chest.getInventory());
						loc.getWorld().playSound(loc, Sound.CHEST_OPEN, 1.0F, 1.0F);
					}
				}
			}
		}
	}

}
