package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.TownSpawnEvent;
import io.github.townyadvanced.townycombat.TownyCombatPlugin;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatHorseUtil;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatTownEventListener implements Listener {

	@SuppressWarnings("unused")
	private final TownyCombatPlugin plugin;
	
	public TownyCombatTownEventListener(TownyCombatPlugin instance) {
		plugin = instance;
	}

	@EventHandler
	public void on(TownSpawnEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(event.getTo() == null)
			return; // Can't proceed without this
		//If player has a mount, register it, otherwise unregister
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()) {
			if(event.getPlayer().isInsideVehicle() && event.getPlayer().getVehicle() instanceof AbstractHorse) {
				TownyCombatHorseUtil.registerPlayerMount(event.getPlayer(), (AbstractHorse)event.getPlayer().getVehicle());
			} else {
				TownyCombatHorseUtil.deregisterPlayerMount(event.getPlayer());
			}
		}
	}
}
