package org.redcraft.redcraftbungeejsonapi;

import java.util.UUID;

public class PlayerInfo {
	public UUID uuid;
	public String name, displayName;

	public PlayerInfo(UUID uuid, String name, String displayName) {
		this.uuid = uuid;
		this.name = name;
		this.displayName = displayName;
	}
}
