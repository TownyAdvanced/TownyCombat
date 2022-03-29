package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBlockUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatTownyEventListener implements Listener {

	@SuppressWarnings("unused")
	private final TownyCombat plugin;
	
	public TownyCombatTownyEventListener(TownyCombat instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void on (TownyDestroyEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() && TownyCombatSettings.isBlockGlitchingPreventionEnabled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}

	@EventHandler
	public void on (TownyBuildEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() && TownyCombatSettings.isBlockGlitchingPreventionEnabled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}
}
