package me.weschke55.ThePurgeArena.commands.permission;

public enum Permission {
	
	ARENA("sg.arena"),
	
	CONFIG("sg.config"),
	
	START("sg.start"),
	
	GAME("sg.game"),
	LOBBY("sg.lobby"),
	
	LIST("sg.list"),
	
	JOIN("sg.join"),
	
	STATS("sg.stats"),
	
	SPECTATE("sg.spectate");
	
	private final String permission;
	
	private Permission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}


}
