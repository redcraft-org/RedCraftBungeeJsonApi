package org.redcraft.redcraftbungeejsonapi.routes;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.redcraft.redcraftbungeejsonapi.HttpApiServer;
import org.redcraft.redcraftbungeejsonapi.RedCraftBungeeJsonApi;

@SuppressWarnings("restriction")
public class PlayersRoute implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String json = RedCraftBungeeJsonApi.getPlayerList().getOnlinePlayersJson();
		HttpApiServer.respondJson(exchange, json);
	}
}