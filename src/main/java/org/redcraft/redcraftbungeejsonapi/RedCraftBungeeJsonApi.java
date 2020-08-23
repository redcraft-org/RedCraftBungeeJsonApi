package org.redcraft.redcraftbungeejsonapi;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ServerPing;

public class RedCraftBungeeJsonApi extends Plugin implements Listener {

	private static HttpApiServer http = null;
	private static PlayerList scrap = null;

	@Override
	public void onEnable() {
		Config.readConfig(this);

		http = new HttpApiServer(Config.httpApiPort);
		scrap = new PlayerList();
		getProxy().getScheduler().schedule(this, scrap, 1, 5, TimeUnit.SECONDS);
		getProxy().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onPing(ProxyPingEvent e) {
		ServerPing packet = e.getResponse();
		packet.getVersion().setName(Config.reportedVersion);
		e.setResponse(packet);
	}

	static public HttpApiServer getHttpServer() {
		return http;
	}

	static public PlayerList getScraper() {
		return scrap;
	}
}