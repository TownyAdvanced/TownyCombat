package io.github.townyadvanced.townycombat.metadata;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.LongDataField;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import com.palmergames.bukkit.towny.utils.MetaDataUtil;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author Goosius
 *
 * FYI This class just exists to cleanup the legacy horse-speed-registration pattern
 * It should be removed when everyone has upgrade to 0.2.2 or above.
 */
public class TownyCombatResidentMetaDataController {

	private static StringDataField battlefieldRole = new StringDataField("townycombat_battlefieldrole", "Light");
	private static LongDataField lastBattlefieldRoleSwitchTime = new LongDataField("townycombat_lastbattlefieldroleswitchtime", 0L);

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
	
	public static String getBattlefieldRole(Resident resident) {
		StringDataField sdf = (StringDataField) battlefieldRole.clone();
		if (resident.hasMeta(sdf.getKey()))
			return MetaDataUtil.getString(resident, sdf);
		return "Light";
	}

	public static void setBattlefieldRole(Resident resident, String newBattlefieldRole) {
		StringDataField sdf = (StringDataField) battlefieldRole.clone();
		if (resident.hasMeta(sdf.getKey())) {
			resident.removeMetaData(sdf);
		}
		resident.addMetaData(new StringDataField("townycombat_battlefieldrole", newBattlefieldRole));
	}
	
	public static long getLastBattlefieldRoleSwitchTime(Resident resident) {
		LongDataField ldf = (LongDataField) lastBattlefieldRoleSwitchTime.clone();
		if (resident.hasMeta(ldf.getKey()))
			return MetaDataUtil.getLong(resident, ldf);
		return 0;
	}

	public static void setLastBattlefieldRoleSwitchTime(Resident resident, long switchTime) {
		LongDataField idf = (LongDataField) lastBattlefieldRoleSwitchTime.clone();
		if (resident.hasMeta(idf.getKey())) {
			resident.removeMetaData(idf);
		} 
		resident.addMetaData(new LongDataField("townycombat_lastbattlefieldroleswitchtime", switchTime));
	}

	public static LocalDate getDateOfLastSuperPotionGrant(Resident resident) {
		return LocalDate.now().minusDays(1);	
	}
}
