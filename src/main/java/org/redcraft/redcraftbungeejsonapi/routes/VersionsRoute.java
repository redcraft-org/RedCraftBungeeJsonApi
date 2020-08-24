package org.redcraft.redcraftbungeejsonapi.routes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

import org.redcraft.redcraftbungeejsonapi.Config;
import org.redcraft.redcraftbungeejsonapi.HttpApiServer;
import org.redcraft.redcraftbungeejsonapi.RedCraftBungeeJsonApi;
import org.redcraft.redcraftbungeejsonapi.models.VersionInfo;

import net.md_5.bungee.api.ProxyServer;

@SuppressWarnings("restriction")
public class VersionsRoute implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String json = getVersionInfo().getVersionsJson();
		HttpApiServer.respondJson(exchange, json);
	}

	public static VersionInfo getVersionInfo() {
		ProxyServer proxy = RedCraftBungeeJsonApi.getInstance().getProxy();

		String serverSoftware = proxy.getName();

		if (Config.spoofReportedSoftware) {
			serverSoftware = Config.reportedSoftware;
		}

		// We have to use deprecations, it's how Bungee works internally
		@SuppressWarnings("deprecation")
		String[] supportedBungeeVersions = proxy.getGameVersion().split(", ");

		ArrayList<String> supportedVersions = new ArrayList<String>(Arrays.asList(supportedBungeeVersions));
		String mainVersion = supportedBungeeVersions[supportedBungeeVersions.length - 1];

		// If we have ViaVersion, we can report the supported versions
		if (proxy.getPluginManager().getPlugin("ViaVersion") != null) {
			mainVersion = getLastSupportedViaVersion();
			supportedVersions = getAllSupportedViaVersion();
		}

		return new VersionInfo(serverSoftware, mainVersion, supportedVersions);
	}

	public static String getLastSupportedViaVersion() {
		// Get the last element of the supported versions reported by ViaVersion
		Integer lastSupportedVersionId = getSupportedViaVersion().last();
		ProtocolVersion lastSupportedVersion = ProtocolVersion.getProtocol(lastSupportedVersionId);
		return lastSupportedVersion.getName();
	}

	public static ArrayList<String> getAllSupportedViaVersion() {
		// Format every protocol version as client versions
		ArrayList<String> supportedVersions = new ArrayList<String>();

		Iterator<Integer> it = getSupportedViaVersion().iterator();

		while (it.hasNext()) {
			Integer supportedVersionId = it.next();
			ProtocolVersion supportedVersion = ProtocolVersion.getProtocol(supportedVersionId);
			supportedVersions.add(supportedVersion.getName());
		}

		return supportedVersions;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static SortedSet<Integer> getSupportedViaVersion() {
		// Call ViaVersion API to get all the supported protocol versions
		ViaAPI viaVersionApi = Via.getAPI();
		SortedSet<Integer> supportedVersions = viaVersionApi.getSupportedVersions();

		return supportedVersions;
	}
}