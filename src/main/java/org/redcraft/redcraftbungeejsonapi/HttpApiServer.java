package org.redcraft.redcraftbungeejsonapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import org.redcraft.redcraftbungeejsonapi.routes.PlayersRoute;
import org.redcraft.redcraftbungeejsonapi.routes.VersionsRoute;

@SuppressWarnings("restriction")
public class HttpApiServer {

	private HttpServer server;

	HttpApiServer() {
		try {
			InetAddress address = InetAddress.getByName(Config.httpApiBind);
			InetSocketAddress socketAddress = new InetSocketAddress(address, Config.httpApiPort);
			server = HttpServer.create(socketAddress, 0);
			server.createContext("/players.json", new PlayersRoute());
			server.createContext("/versions.json", new VersionsRoute());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		// Gracefully stop the server and give 1 second to handle current requests
		server.stop(1);
	}

	static public void respondJson(HttpExchange exchange, String json) throws IOException {
		Headers headers = exchange.getResponseHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Cache-Control", "no-cache");
		exchange.sendResponseHeaders(200, json.length());
		OutputStream os = exchange.getResponseBody();
		os.write(json.getBytes());
		os.close();
	}
}
