package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import com.palmergames.bukkit.towny.event.time.NewShortTimeEvent;
import com.palmergames.util.TimeTools;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatBlockUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMapUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
		TownyCombatMovementUtil.adjustAllPlayerAndMountSpeeds();
		if(TownyCombatSettings.isTacticalInvisibilityEnabled()) {
			TownyCombatMapUtil.evaluateTacticalInvisibility();
			TownyCombatMapUtil.applyTacticalInvisibilityToPlayers();
		}
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.giveEffectsToHeavyPlayersWearingArmour();
		}
    }
}
