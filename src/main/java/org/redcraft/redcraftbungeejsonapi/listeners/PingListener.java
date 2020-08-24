package org.redcraft.redcraftbungeejsonapi.listeners;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import org.redcraft.redcraftbungeejsonapi.Config;
import org.redcraft.redcraftbungeejsonapi.RedCraftBungeeJsonApi;
import org.redcraft.redcraftbungeejsonapi.routes.VersionsRoute;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;

public class PingListener implements Listener {

	@EventHandler
	public void onPing(ProxyPingEvent e) {
		ServerPing packet = e.getResponse();
		ProxyServer proxy = RedCraftBungeeJsonApi.getInstance().getProxy();

		String software = proxy.getName();
		// We have to use deprecations, it's how Bungee works internally
		@SuppressWarnings("deprecation")
		String version = proxy.getGameVersion();

		if (Config.spoofReportedSoftware) {
			software = Config.reportedSoftware;
		}

		if (Config.spoofReportedVersion) {
			// If the version is set to ViaVersion, try to report the last supported version
			if (Config.reportedVersion.equalsIgnoreCase("ViaVersion")) {
				try {
					// Check if ViaVersion is enabled to avoid a nasty stacktrace
					if (proxy.getPluginManager().getPlugin("ViaVersion") == null) {
						throw new Exception("ViaVersion is not installed");
					}
					version = VersionsRoute.getLastSupportedViaVersion();
				} catch (Exception ex) {
					// If it failed, warn to the console and use the default reported version from Bungee
					String message = "Could not get ViaVersion latest supported version: " + ex.getLocalizedMessage();
					RedCraftBungeeJsonApi.getInstance().getLogger().warning(message);
				}
			} else {
				version = Config.reportedVersion;
			}
		}

		// That's ugly but that's how MC protocol works
		packet.getVersion().setName(software + " " + version);

		e.setResponse(packet);
	}
}