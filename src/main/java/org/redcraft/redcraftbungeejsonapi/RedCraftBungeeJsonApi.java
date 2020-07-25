package org.redcraft.redcraftbungeejsonapi;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ServerPing;

public class RedCraftBungeeJsonApi extends Plugin implements Listener {

	private static String software_version = "Minecraft 1.0";
	private static HttpApiServer http = null;
	private static PlayerList scrap = null;

	@Override
	public void onEnable() {
		Configuration config = YamlConfig.getConfig(this);

		software_version = config.getString("reported-version", software_version);
		http = new HttpApiServer(config.getInt("http-api-port", 8000));
		scrap = new PlayerList();
		getProxy().getScheduler().schedule(this, scrap, 1, 5, TimeUnit.SECONDS);
		getProxy().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onPing(ProxyPingEvent e) {
		ServerPing packet = e.getResponse();
		packet.getVersion().setName(software_version);
		e.setResponse(packet);
	}

	static public HttpApiServer getHttpServer() {
		return http;
	}

	static public PlayerList getScraper() {
		return scrap;
	}
}