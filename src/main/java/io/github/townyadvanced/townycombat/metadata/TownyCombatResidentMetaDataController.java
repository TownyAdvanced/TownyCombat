package io.github.townyadvanced.townycombat.metadata;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.LongDataField;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import com.palmergames.bukkit.towny.utils.MetaDataUtil;

import java.time.LocalDate;

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
	/*
	 * The last date on which the player collected their super potions.
	 * Store as YYYY-MM-DD
	 */
	private static StringDataField lastSuperPotionCollectionDate = new StringDataField("townycombat_lastsuperpotioncollectiondate", "");

	public static String getBattlefieldRole(Resident resident) {
		StringDataField sdf = (StringDataField) battlefieldRole.clone();
		if (resident.hasMeta(sdf.getKey()))
			return MetaDataUtil.getString(resident, sdf);
		return "LIGHT_INFANTRY";
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

	public static LocalDate getLastSuperPotionCollectionDate(Resident resident) {
		StringDataField sdf = (StringDataField) lastSuperPotionCollectionDate.clone();
		if (resident.hasMeta(sdf.getKey())) {
			String dateAsString = MetaDataUtil.getString(resident, sdf);
			if (dateAsString.length() > 0) {
				return LocalDate.parse(dateAsString);
			}
		}
		return null;
	}

	public static void setLastSuperPotionCollectionDate(Resident resident, LocalDate date) {
		StringDataField sdf = (StringDataField) lastSuperPotionCollectionDate.clone();
		if (resident.hasMeta(sdf.getKey())) {
			resident.removeMetaData(sdf);
		}
		String dateAsString = date.toString();
		resident.addMetaData(new StringDataField("townycombat_lastsuperpotioncollectiondate", dateAsString));
	}
}
