package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.NationSpawnEvent;
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
public class TownyCombatNationEventListener implements Listener {

	@SuppressWarnings("unused")
	private final TownyCombat plugin;
	
	public TownyCombatNationEventListener(TownyCombat instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void on(NationSpawnEvent event) {
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
