package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import com.palmergames.bukkit.towny.event.player.PlayerKeepsExperienceEvent;
import com.palmergames.bukkit.towny.event.player.PlayerKeepsInventoryEvent;
import com.palmergames.bukkit.towny.event.time.NewShortTimeEvent;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatBlockUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatDistanceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatInventoryUtil;
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

			if (!event.getMaterial().isSolid())
				return;

            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void on (TownyBuildEvent event) {
        if(TownyCombatSettings.isTownyCombatEnabled() 
        		&& TownyCombatSettings.isBlockGlitchingPreventionEnabled()
        		&& event.isCancelled()) {

        	if (!event.getMaterial().isSolid() && !event.getMaterial().name().endsWith("BUCKET"))
				return;

            TownyCombatBlockUtil.applyBlockGlitchingPrevention(event.getPlayer());
        }
	}

	@EventHandler
	public void on(NewDayEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() 
				&& TownyCombatSettings.isBattlefieldRolesEnabled() 
				&& TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
			TownyCombatItemUtil.grantSuperPotionsToOnlinePlayers();
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
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() 
				&& TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.giveRoleBasedDamageResistance();
		}
    }

	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerDeathInventory(PlayerKeepsInventoryEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatSettings.isKeepInventoryOnDeathEnabled())
			return;	
		if(!TownyCombatDistanceUtil.isCloseToATown(event.getPlayer(), TownyCombatSettings.getKeepStuffOnDeathTownProximityBlocks()))
			return;
		//Keep inv functions
		TownyCombatInventoryUtil.degradeInventory(event.getPlayer());
		event.setCancelled(false);
	}

	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerDeathExperience(PlayerKeepsExperienceEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatSettings.isKeepExperienceOnDeathEnabled())
			return;	
		if(!TownyCombatDistanceUtil.isCloseToATown(event.getPlayer(), TownyCombatSettings.getKeepStuffOnDeathTownProximityBlocks()))
			return;
		event.setCancelled(false);
	}

}
