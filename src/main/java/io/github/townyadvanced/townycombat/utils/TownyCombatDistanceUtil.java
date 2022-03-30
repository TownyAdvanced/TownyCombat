package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.entity.Player;

/**
 * This class contains utility functions related to distances 
 *
 * @author Goosius
 */
public class TownyCombatDistanceUtil {

	/**
	 * @param player given player
	 * @param maxDistance max allowed distance
	 * 
	 * @return true if the given player is close to any non-ruined town
	 */
	public static boolean isCloseToATown(Player player, int maxDistance) {
		//True if player is in a town
		Town townPlayerIsIn = TownyAPI.getInstance().getTown(player.getLocation());
		if(townPlayerIsIn != null && !townPlayerIsIn.isRuined())
			return true;
	
		//True if player is close to a town homeblock	
		WorldCoord worldCoordOfPlayer = WorldCoord.parseWorldCoord(player);
		for(Town town: TownyAPI.getInstance().getTowns()) {
			if(!town.isRuined() 
					&& town.hasHomeBlock()
					&& areCoordsClose(
						worldCoordOfPlayer,
						town.getHomeBlockOrNull().getWorldCoord(),
						maxDistance)) {
				return true;	
			}
		}
		return false;
	}

	/**
	 * @param coord1 world coord 1
	 * @param coord2 world coord 2
	 * @param maxDistance max allowed distance in blocks
	 *
	 * @return true if the two world coords are close
	 */
	private static boolean areCoordsClose(WorldCoord coord1, WorldCoord coord2, int maxDistance) {
		int maxDistanceTownBlocks = maxDistance * TownySettings.getTownBlockSize();
		if(!coord1.getWorldName().equalsIgnoreCase(coord2.getWorldName()))
			return false;
		double distanceTownblocks = Math.sqrt(Math.pow(coord1.getX() - coord2.getX(), 2) + Math.pow(coord1.getZ() - coord2.getZ(), 2));
		return distanceTownblocks < maxDistanceTownBlocks;
	}
}
