package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import com.palmergames.bukkit.towny.event.time.NewShortTimeEvent;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatBlockUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatItemUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMapUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void on (TownyDestroyEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() 
	        	&& TownyCombatSettings.isBlockGlitchingPreventionEnabled()
    	    	&& event.isCancelled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void on (TownyBuildEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() 
        		&& TownyCombatSettings.isBlockGlitchingPreventionEnabled()
        		&& event.isCancelled()) {
            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}
	
    @EventHandler
    public void onShortTime(NewShortTimeEvent event) {
        if (!TownyCombatSettings.isTownyCombatEnabled())
        	return;
		if(TownyCombatSettings.isTacticalInvisibilityEnabled()) {
			TownyCombatMapUtil.evaluateTacticalInvisibility();
			TownyCombatMapUtil.applyTacticalInvisibilityToPlayers();
		}
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.giveEffectsToHeavyPlayersWearingArmour();
			if(TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
				TownyCombatItemUtil.grantSuperPotionsAtScheduledHourOfDay();
			}
		}
    }
}
