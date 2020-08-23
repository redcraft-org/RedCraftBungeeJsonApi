# RedCraftBungeeJsonApi

A basic BungeeCord plugin to get stats about servers and change the displayed version when querying the server

## How it works

### The JSON HTTP API

It opens a port (by default 8000, can be changed in the config with the `http-api-port` parameter) to serve a JSON API to query the player list of each server attached to the BungeeCord server.

Example with `curl localhost:8000/players`:

```json
{
    "players": {
        "fake_server": [{
            "uuid": "ffffffff-ffff-ffff-ffff-ffffffffffff",
            "name": "fake_player",
            "displayName": "§4fake_player"
        }]
    }
}
```

### The displayed version spoofing

You can also report a different software ID and version for ranking sites using the `reported-version` config key, that can be useful if you support newer client versions on an older server.

## Contributing

You are free to suggest changes by opening an issue ticket.

You can also open PRs, remember to bump the version in `pom.xml` and `plugin.yml` before opening a pull request.
