package org.redcraft.redcraftbungeejsonapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpApiServer {

	HttpApiServer(int port) {
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/players", new PlayersApiHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class PlayersApiHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = RedCraftBungeeJsonApi.getScraper().getOnlinePlayersJson();
		Headers headers = exchange.getResponseHeaders();
		headers.add("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}