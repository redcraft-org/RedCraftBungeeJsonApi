package org.redcraft.redcraftbungeejsonapi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerList implements Runnable {

	private transient boolean locked;
	public Hashtable<String, ArrayList<PlayerInfo>> players = new Hashtable<String, ArrayList<PlayerInfo>>();

	@Override
	public void run() {
		Hashtable<String, ArrayList<PlayerInfo>> currentPlayerList = new Hashtable<String, ArrayList<PlayerInfo>>();
		for (Entry<String, ServerInfo> s : ProxyServer.getInstance().getServers().entrySet()) {
			ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
			for (ProxiedPlayer p : s.getValue().getPlayers()) {
				players.add(new PlayerInfo(p.getUniqueId(), p.getName(), p.getDisplayName().replace('§', '&')));
			}
			currentPlayerList.put(s.getValue().getName(), players);
		}
		locked = true;
		players.clear();
		players = currentPlayerList;
		locked = false;
	}

	public Hashtable<String, ArrayList<PlayerInfo>> getOnlinePlayers() {
		int tries = 0;
		while (locked) {
			try {
				Thread.sleep(1);
				tries++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (tries > 0) {
			ProxyServer.getInstance().getLogger().warning("getOnlinePlayers() had to hang the server for " + tries + " milliseconds");
		}
		return players;
	}

	public String getOnlinePlayersJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

}
