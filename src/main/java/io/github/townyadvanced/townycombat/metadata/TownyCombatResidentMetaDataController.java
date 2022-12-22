package io.github.townyadvanced.townycombat.metadata;


import com.palmergames.bukkit.towny.object.Resident;

import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import com.palmergames.bukkit.towny.utils.MetaDataUtil;
import javax.annotation.Nullable;
import java.util.*;

/**
 * 
 * @author Goosius
 *
 * FYI This class just exists to cleanup the legacy horse-speed-registration pattern
 * It should be removed when everyone has upgrade to 0.2.2 or above.
 */
public class TownyCombatResidentMetaDataController {

	//This map records the original/base speeds of the horses the player/resident trained 
	private static StringDataField horseSpeedMap = new StringDataField("townycombat_horsespeedmap", ""); 

	public static Map<UUID, Double> getHorseSpeedMap(Resident resident) {
		Map<UUID, Double> result = new HashMap<>();
		String horseSpeedMapAsString = getHorseSpeedMapAsString(resident);
		if(horseSpeedMapAsString != null && horseSpeedMapAsString.length() != 0) {
			String[] entries = horseSpeedMapAsString.replaceAll(" ","").split(",");
			String[] entryArray;
			for(String entryString: entries) {
				entryArray = entryString.split(":");
				result.put(UUID.fromString(entryArray[0]), Double.parseDouble(entryArray[1]));
			}
		}
		return result;
	}

	@Nullable
	private static String getHorseSpeedMapAsString(Resident resident) {
		StringDataField sdf = (StringDataField) horseSpeedMap.clone();
		if (resident.hasMeta(sdf.getKey()))
			return MetaDataUtil.getString(resident, sdf);
		return null;
	}

	public static void removeHorseSpeedMap(Resident resident) {
		StringDataField sdf = (StringDataField) horseSpeedMap.clone();
		if (resident.hasMeta(sdf.getKey()))
			resident.removeMetaData(sdf);
	}
}
