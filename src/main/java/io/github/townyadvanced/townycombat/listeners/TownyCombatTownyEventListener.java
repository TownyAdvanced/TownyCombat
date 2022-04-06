package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import com.palmergames.bukkit.towny.event.time.NewShortTimeEvent;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatBlockUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMapUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
        if(TownyCombatSettings.isTownyCombatEnabled() 
	        	&& TownyCombatSettings.isBlockGlitchingPreventionEnabled()
    	    	&& event.isCancelled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}

	@EventHandler
	public void on (TownyBuildEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() 
        		&& TownyCombatSettings.isBlockGlitchingPreventionEnabled()
        		&& event.isCancelled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}
	
    @EventHandler
    public void onShortTime(NewShortTimeEvent event) {
        if (TownyCombatSettings.isTownyCombatEnabled()) {
        	if(TownyCombatSettings.isArmourSlowingEnabled()) {
        		TownyCombatMovementUtil.adjustPlayerSpeed();
			}
			if(TownyCombatSettings.isTacticalInvisibilityEnabled()) { 
	            TownyCombatMapUtil.evaluateTacticalInvisibility();
				TownyCombatMapUtil.applyTacticalInvisibilityToPlayers();
			}
        }
    }
    
    @EventHandler (ignoreCancelled = true)
    public void on (EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			event.setDamage(event.getDamage() + (event.getDamage() * (TownyCombatSettings.getDamageModificationAllWeaponsPercentage() / 100)));
		}
	}
}
