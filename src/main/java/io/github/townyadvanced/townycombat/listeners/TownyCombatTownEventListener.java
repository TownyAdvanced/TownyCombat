package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.TownSpawnEvent;
import io.github.townyadvanced.townycombat.TownyCombat;
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
	private final TownyCombat plugin;
	
	public TownyCombatTownEventListener(TownyCombat instance) {
		plugin = instance;
	}

	@EventHandler (ignoreCancelled = true)
	public void on(TownSpawnEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(event.getTo() == null)
			return; // Can't proceed without this
		//If player has a mount, register it, otherwise unregister
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()) {
			if(event.getPlayer().isInsideVehicle() && event.getPlayer().getVehicle() instanceof AbstractHorse) {
				TownyCombatHorseUtil.registerPlayerMountForTeleport(event.getPlayer(), (AbstractHorse)event.getPlayer().getVehicle());
			} else {
				TownyCombatHorseUtil.deregisterPlayerMountForTeleport(event.getPlayer());
			}
		}
	}
}
