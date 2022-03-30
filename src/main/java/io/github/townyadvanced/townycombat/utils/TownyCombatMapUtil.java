package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.integrations.dynmap.DynmapIntegration;
import io.github.townyadvanced.townycombat.objects.HeldItemsCombination;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * This class contains utility functions related to the online map integrations
 *
 * @author Goosius
 */
public class TownyCombatMapUtil {

	public static final String TACTICALLY_INVISIBLE_METADATA_ID = "tacticallyInvisible";
	public static final FixedMetadataValue TACTICAL_INVISIBILITY_FIXED_METADATA_VALUE = new FixedMetadataValue(Towny.getPlugin(), true);
	
	/**
	 * Evaluate players to see if they are should be hidden on the map.
	 */
	public static void evaluateTacticalInvisibility() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			try {
				if(shouldPlayerBeMapHidden(player)) {
					if(!player.hasMetadata(TACTICALLY_INVISIBLE_METADATA_ID)) {
						player.setMetadata(TACTICALLY_INVISIBLE_METADATA_ID, TACTICAL_INVISIBILITY_FIXED_METADATA_VALUE);
					}
				} else {
					if (player.hasMetadata(TACTICALLY_INVISIBLE_METADATA_ID)) {
						player.removeMetadata(TACTICALLY_INVISIBLE_METADATA_ID, Towny.getPlugin());
					}
				}
			} catch (Exception e) {
				try {
					TownyCombat.severe("Problem evaluating tactical invisibility for player " + player.getName());
				} catch (Exception e2) {
					TownyCombat.severe("Problem evaluating tactical invisibility for a player (could not read player name)");
				}
				e.printStackTrace();
			}
		}
	}

	/**
	 * Determine if a player should be hidden on the map
	 *
	 * @param player the player
	 * @return true if they should be hidden
	 */
	private static boolean shouldPlayerBeMapHidden(Player player) {
		//Evaluate automatic mode
		if (TownyCombatSettings.getTacticalInvisibilityModeAutomaticModeEnabled()) {
			Town town = TownyAPI.getInstance().getTown(player.getLocation());

			if(town == null) {
				//Wilderness
				if(TownyCombatSettings.isTacticalInvisibilityModeAutomaticModeScopeWilderness())
					return true;
			} else {
				//Ruins
				if(TownyCombatSettings.isTacticalInvisibilityModeAutomaticModeScopeRuins() && town.isRuined())
					return true;
			}
		}
		//Evaluate manual mode / triggering
		if(TownyCombatSettings.isTacticalInvisibilityTriggeringEnabled()) {
			for(HeldItemsCombination heldItemsCombination: TownyCombatSettings.getTacticalInvisibilityTriggeringItems()) {
				//Off Hand
				if(!heldItemsCombination.isIgnoreOffHand() && player.getInventory().getItemInOffHand().getType() != heldItemsCombination.getOffHandItemType())
					continue;  //off hand does not match. Try next combo
				//Main hand
				if(!heldItemsCombination.isIgnoreMainHand() && player.getInventory().getItemInMainHand().getType() != heldItemsCombination.getMainHandItemType())
					continue; //main hand does not match. Try next combo
				//Hide player
				return true;
			}
		}
		//Player has not met any conditions for tactical invisibility. Do not hide them.
		return false;
	}
	
	public static void applyTacticalInvisibilityToPlayers() {
		DynmapIntegration dynmapIntegration = TownyCombat.getDynmapIntegration();
		if(dynmapIntegration != null)
			dynmapIntegration.applyTacticalInvisibilityToPlayers();
	}

}
