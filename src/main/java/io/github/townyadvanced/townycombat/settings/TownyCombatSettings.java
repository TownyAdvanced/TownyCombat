package io.github.townyadvanced.townycombat.settings;

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

}
