package org.redcraft.redcraftbungeejsonapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class YamlConfig {
	public static Configuration getConfig(Plugin plugin) {
		Configuration configuration;
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
		File configFile = new File(plugin.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				try (InputStream is = plugin.getResourceAsStream("config.yml");
					OutputStream os = new FileOutputStream(configFile)) {
					ByteStreams.copy(is, os);
				}
			} catch (IOException e) {
				throw new RuntimeException("Unable to create configuration file", e);
			}
		}
		return configuration;
	}
}
