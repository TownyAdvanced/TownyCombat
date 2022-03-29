package io.github.townyadvanced.townycombat;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.townyadvanced.townycombat.db.Database;
import io.github.townyadvanced.townycombat.settings.Settings;
import io.github.townyadvanced.townycombat.settings.Translation;

public class TownyCombatPlugin extends JavaPlugin {
	
	private static TownyCombatPlugin plugin;
	
    @Override
    public void onEnable() {
    	
    	plugin = this;
    	
        if (!loadSettings())
        	onDisable();

        loadDatabase();

    }
    
	public String getVersion() {
		return this.getDescription().getVersion();
	}
	
	public static TownyCombatPlugin getPlugin() {
		return plugin;
	}
	
	public static String getPrefix() {
		return Translation.language != null ? Translation.of("plugin_prefix") : "[" + plugin.getName() + "]";
	}
	
	private void loadDatabase() {
		new Database(plugin);
	}
	
	private boolean loadSettings() {
		try {
			Settings.loadConfig(this.getDataFolder().getPath() + File.separator + "config.yml", getVersion());
			Translation.loadLanguage(this.getDataFolder().getPath() + File.separator , "english.yml");
		} catch (IOException e) {
            e.printStackTrace();
            severe("Config.yml failed to load! Disabling!");
            return false;
        }
		info("Config.yml loaded successfully.");		
		return true;
	}
	
	public static void info(String message) {
		plugin.getLogger().info(message);
	}
	
	public static void severe(String message) {
		plugin.getLogger().severe(message);
	}
}
