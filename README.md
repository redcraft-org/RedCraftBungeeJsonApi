# RedCraftBungeeJsonApi

A basic BungeeCord plugin to get stats about servers and change the displayed version when querying the server

## How it works

### Software spoofing

You can enable `spoof-reported-software` and set `reported-software` to a custom value.
That is useful if you want to have a custom software name when [querying a server](https://dinnerbone.com/minecraft/tools/status/) and avoid the boring "BungeeCord" or "Waterfall".

### Version spoofing

You can enable `spoof-reported-version` and set `reported-version` to `ViaVersion` to use the latest supported ViaVersion client version or set it to a custom value.
This is very useful when you want to report a custom version to voting websites, for example advertise as 1.16.2 even if your server runs 1.15.2 with a compatibility layer.

:warning: ViaVersion will report that all versions are compatible from 1.8 to the latest, but if you don't have ViaBackwards installed, older versions might actually not be supported

### JSON HTTP API

You can enable `http-api-enabled` to open a HTTP server to serve a JSON API to query various information.
By default, port `25580` is used, but can be customized with `http-api-bind`.
Also, by default this API is wide open as it listens on `0.0.0.0`, but can be restricted by updating `http-api-bind`.
I would recommend to set the bind to `127.0.0.1` and use something like nginx to do a reverse proxy to avoid easy DDoS of that port.

:warning: There is no authentication whatsoever and the API _may_ contain vanished players.

#### Routes

`curl localhost:25580/players.json`:

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

`curl localhost:25580/versions.json`:

```json
{
    "serverSoftware": "RedCraft",
    "mainVersion": "1.16.2",
    "supportedVersions": ["1.8.x", "1.9", "1.9.1", "1.9.2", "1.9.3/4", "1.10", "1.11", "1.11.1", "1.12", "1.12.1", "1.12.2", "1.13", "1.13.1", "1.13.2", "1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4", "1.15", "1.15.1", "1.15.2", "1.16", "1.16.1", "1.16.2"]
}
```

## Contributing

You are free to suggest changes by opening an issue ticket.

You can also open PRs, remember to bump the version in `pom.xml` and `plugin.yml` before opening a pull request.
