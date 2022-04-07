package io.github.townyadvanced.townycombat.metadata;


import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.CustomDataField;
import com.palmergames.bukkit.towny.object.metadata.IntegerDataField;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import com.palmergames.bukkit.towny.utils.MetaDataUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 
 * @author LlmDl
 *
 */
public class TownyCombatResidentMetaDataController {

	private static StringDataField trainedHorseBaseSpeedMap = new StringDataField("townycombat_trainedhorsebasespeedmap", ""); 



	public static void setBoolean(Resident resident, String key, boolean bool) {
		if (resident.hasMeta(key)) {
			if (bool == false)
				resident.removeMetaData(resident.getMetadata(key));
			else {
				CustomDataField<?> cdf = resident.getMetadata(key);
				if (cdf instanceof BooleanDataField) {
					((BooleanDataField) cdf).setValue(bool);
					resident.save();
				}
				return;
			}
		} else if (bool)
			resident.addMetaData(new BooleanDataField(key, bool));
	}

	public static boolean getBoolean(Resident resident, String key) {
		if (resident.hasMeta(key)) {
			CustomDataField<?> cdf = resident.getMetadata(key);
			if (cdf instanceof BooleanDataField)
				return ((BooleanDataField) cdf).getValue();
		}
		return false;
	}


	

	private static Map<UUID, Double> getTrainedHorseBaseSpeedMap(Resident resident) {	
		Map<UUID, Double> result = new HashMap<>();
		String trainedHorseBaseSpeedMapAsString = getTrainedHorseBaseSpeedMapAsString(resident);
		if(trainedHorseBaseSpeedMapAsString != null && trainedHorseBaseSpeedMapAsString.length() == 0) {
			String[] entries = trainedHorseBaseSpeedMapAsString.replaceAll(" ","").split(",");
			String[] entryArray;
			for(String entryString: entries) {
				entryArray = entryString.split(":");
				result.put(UUID.fromString(entryArray[0]), Double.parseDouble(entryArray[1]));
			}
		}
		return result;
	}

	@Nullable
	private static String getTrainedHorseBaseSpeedMapAsString(Resident resident) {
		StringDataField sdf = (StringDataField) trainedHorseBaseSpeedMap.clone();
		if (resident.hasMeta(sdf.getKey()))
			return MetaDataUtil.getString(resident, sdf);
		return null;
	}

	public static double registerTrainedHorse(Resident resident, AbstractHorse horse) {
		Map<UUID, Double> trainedHorseBaseSpeedMap = getTrainedHorseBaseSpeedMap(resident);
		double baseHorseSpeed = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		trainedHorseBaseSpeedMap.put(horse.getUniqueId(), baseHorseSpeed);
		setTrainedHorseBaseSpeedMap(resident, trainedHorseBaseSpeedMap);
		return baseHorseSpeed;		
	}

	private static void setTrainedHorseBaseSpeedMap(Resident resident, Map<UUID,Double> mapToSet) {
		//Build map
		StringBuilder stringBuilderForMap = new StringBuilder();
		for(Map.Entry<UUID, Double> mapEntry: mapToSet.entrySet()) {
			stringBuilderForMap.append(mapEntry.getKey().toString());
			stringBuilderForMap.append(":");
			stringBuilderForMap.append(mapEntry.getValue().toString());			
		}
		//Set value		
		StringDataField sdf = (StringDataField) trainedHorseBaseSpeedMap.clone();
		if (resident.hasMeta(sdf.getKey())) {
			MetaDataUtil.setString(resident, sdf, stringBuilderForMap.toString(), true);
		} else {
			resident.addMetaData(new StringDataField("townycombat_trainedhorsebasespeedmap", stringBuilderForMap.toString()));
		}
	}

	public static Double getTrainedHorseBaseSpeed(Resident resident, UUID horseUUID) {
		return getTrainedHorseBaseSpeedMap(resident).get(horseUUID);
	}
}
