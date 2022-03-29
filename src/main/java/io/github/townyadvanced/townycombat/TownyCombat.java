package io.github.townyadvanced.townycombat;

import java.io.File;
import java.io.IOException;

import io.github.townyadvanced.townycombat.listeners.TownyCombatBukkitEventListener;
import io.github.townyadvanced.townycombat.listeners.TownyCombatNationEventListener;
import io.github.townyadvanced.townycombat.listeners.TownyCombatTownEventListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.townyadvanced.townycombat.db.Database;
import io.github.townyadvanced.townycombat.settings.Settings;
import io.github.townyadvanced.townycombat.settings.Translation;

public class TownyCombat extends JavaPlugin {
	
	private static TownyCombat plugin;
	
    @Override
    public void onEnable() {
    	
    	plugin = this;
    	
        if (!loadSettings()) {
        	onDisable();
			return;
		}

        loadDatabase();
		registerListeners();

    }
    
	public String getVersion() {
		return this.getDescription().getVersion();
	}
	
	public static TownyCombat getPlugin() {
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
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();		
		pm.registerEvents(new TownyCombatBukkitEventListener(this), this);
		pm.registerEvents(new TownyCombatTownEventListener(this), this);		
		pm.registerEvents(new TownyCombatNationEventListener(this), this);
	}
}
