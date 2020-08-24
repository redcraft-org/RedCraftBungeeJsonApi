package org.redcraft.redcraftbungeejsonapi;

import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

import org.redcraft.redcraftbungeejsonapi.runnables.PlayerList;
import org.redcraft.redcraftbungeejsonapi.listeners.PingListener;

public class RedCraftBungeeJsonApi extends Plugin {

	private static Plugin instance;

	private static HttpApiServer http;
	private static PlayerList playerList;
	private static PingListener pingListener;

	@Override
	public void onEnable() {
		instance = this;

		Config.readConfig(this);

		if (Config.httpApiEnabled) {
			http = new HttpApiServer();
		}
		playerList = new PlayerList();
		pingListener = new PingListener();
		getProxy().getScheduler().schedule(this, playerList, 1, 5, TimeUnit.SECONDS);
		getProxy().getPluginManager().registerListener(this, pingListener);
	}

	@Override
	public void onDisable() {
		if (http != null) {
			http.stop();
		}
		getProxy().getScheduler().cancel(this);
		getProxy().getPluginManager().unregisterListeners(this);
	}

	static public HttpApiServer getHttpServer() {
		return http;
	}

	static public PlayerList getPlayerList() {
		return playerList;
	}

	static public Plugin getInstance() {
		return instance;
	}
}