package io.github.townyadvanced.townycombat.metadata;


import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import com.palmergames.bukkit.towny.utils.MetaDataUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 
 * @author Goosius
 *
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

	public static double registerTrainedHorse(Resident resident, AbstractHorse horse) {
		Map<UUID, Double> horseSpeedMap = getHorseSpeedMap(resident);
		double baseHorseSpeed = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		//Compensate for speed effect
		for(PotionEffect potionEffect: horse.getActivePotionEffects()) {
			if(potionEffect.getType() == PotionEffectType.SPEED) {
				baseHorseSpeed = baseHorseSpeed / (100 + (potionEffect.getAmplifier() * 20)) * 100;
			}
			if(potionEffect.getType() == PotionEffectType.SLOW) {
				baseHorseSpeed = baseHorseSpeed / (100 - (potionEffect.getAmplifier() * 15)) * 100;
			}
		}
		//Catch all limit for edge cases
		baseHorseSpeed = Math.min(baseHorseSpeed, TownyCombatMovementUtil.VANILLA_HORSE_MAX_MOVEMENT_SPEED);
		horseSpeedMap.put(horse.getUniqueId(), baseHorseSpeed);
		setHorseSpeedMap(resident, horseSpeedMap);
		resident.save();
		return baseHorseSpeed;		
	}

	public static void setHorseSpeedMap(Resident resident, Map<UUID,Double> mapToSet) {
		//Build map
		StringBuilder stringBuilderForMap = new StringBuilder();
		boolean firstEntry = true;
		for(Map.Entry<UUID, Double> mapEntry: mapToSet.entrySet()) {
			if(firstEntry) {
				firstEntry = false;
			} else {
				stringBuilderForMap.append(", ");
			}
			stringBuilderForMap.append(mapEntry.getKey().toString());
			stringBuilderForMap.append(":");
			stringBuilderForMap.append(mapEntry.getValue().toString());			
		}
		//Set value		
		StringDataField sdf = (StringDataField) horseSpeedMap.clone();
		if (resident.hasMeta(sdf.getKey())) {
			MetaDataUtil.setString(resident, sdf, stringBuilderForMap.toString(), true);
		} else {
			resident.addMetaData(new StringDataField("townycombat_horsespeedmap", stringBuilderForMap.toString()));
		}
	}

	public static Double getTrainedHorseBaseSpeed(Resident resident, UUID horseUUID) {
		return getHorseSpeedMap(resident).get(horseUUID);
	}
}
