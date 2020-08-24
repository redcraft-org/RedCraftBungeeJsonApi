package org.redcraft.redcraftbungeejsonapi.runnables;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;

import org.redcraft.redcraftbungeejsonapi.models.PlayerInfo;

import com.google.gson.Gson;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerList implements Runnable {

	public Hashtable<String, ArrayList<PlayerInfo>> players = new Hashtable<String, ArrayList<PlayerInfo>>();

	@Override
	public void run() {
		// Every time run() is called, we query each server and get the player list
		Hashtable<String, ArrayList<PlayerInfo>> currentPlayerList = new Hashtable<String, ArrayList<PlayerInfo>>();
		for (Entry<String, ServerInfo> s : ProxyServer.getInstance().getServers().entrySet()) {
			ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
			for (ProxiedPlayer p : s.getValue().getPlayers()) {
				players.add(new PlayerInfo(p.getUniqueId(), p.getName(), p.getDisplayName().replace('§', '&')));
			}
			currentPlayerList.put(s.getValue().getName(), players);
		}
		players = currentPlayerList;
	}

	public String getOnlinePlayersJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
}
