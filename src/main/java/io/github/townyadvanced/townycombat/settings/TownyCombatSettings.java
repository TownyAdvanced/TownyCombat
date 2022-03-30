package io.github.townyadvanced.townycombat.settings;

import java.util.Set;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatSettings {

	public static boolean isTownyCombatEnabled() {
		return Settings.getBoolean(ConfigNodes.TOWNY_COMBAT_ENABLED);
	}

	public static boolean isTeleportMountWithPlayerEnabled() {
		return Settings.getBoolean(ConfigNodes.TELEPORT_MOUNT_WITH_PLAYER_ENABLED);
	}

	public static boolean isBlockGlitchingPreventionEnabled() {
		return Settings.getBoolean(ConfigNodes.BLOCK_GLITCHING_PREVENTION_ENABLED);
	}

	public static int getBlockGlitchingTeleportDelayMillis() {
		return Settings.getInt(ConfigNodes.BLOCK_GLITCHING_PREVENTION_TELEPORT_DELAY_MILLIS);
	}

	public static boolean isKeepInventoryOnDeathEnabled() {
		return Settings.getBoolean(ConfigNodes.KEEP_STUFF_ON_DEATH_KEEP_INVENTORY_ENABLED);
	}

	public static int getKeepInventoryOnDeathToolsDegradePercentage() {
		return Settings.getInt(ConfigNodes.KEEP_STUFF_ON_DEATH_TOOLS_DEGRADE_PERCENTAGE);
	}

	public static boolean isKeepExperienceOnDeathEnabled() {
		return Settings.getBoolean(ConfigNodes.KEEP_STUFF_ON_DEATH_KEEP_EXPERIENCE_ENABLED);
	}

	public static int getKeepStuffOnDeathTownProximityBlocks() {
		return Settings.getInt(ConfigNodes.KEEP_STUFF_ON_DEATH_TOWN_PROXIMITY_BLOCKS);
	}
}
