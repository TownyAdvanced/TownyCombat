package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains utility functions related to blocks 
 *
 * @author Goosius
 */
public class TownyCombatBlockUtil {

	private static Set<Player> pendingAntiGlitchTeleports = new HashSet<>();

	/**
	 * Apply block glitching as follows:
	 *
	 * 1. If the player is has no pending anti-glitch-teleport, schedule one in 1 second, back to their current location
	 * 2. If the player has a pending anti-glitch teleport, do nothing.
	 *
	 * @param player
	 */
	public static void applyBlockGlitchingPrevention(Player player) {
		if(!pendingAntiGlitchTeleports.contains(player)) {
			//Add player to the pending list
			pendingAntiGlitchTeleports.add(player);
			//Schedule the teleport
			Location targetLocation = player.getLocation();
			int delayTimeTicks = (int)(20d * TownyCombatSettings.getBlockGlitchingTeleportDelayMillis() / 1000);
			Towny.getPlugin().getServer().getScheduler().runTaskLater(Towny.getPlugin(), () -> {
				//Note: We set the cause to "Unknown" so that SW's own teleport blocker won't stop it.
				player.teleport(targetLocation, PlayerTeleportEvent.TeleportCause.UNKNOWN);
				pendingAntiGlitchTeleports.remove(player);
			}, delayTimeTicks);
		}
	}
}
