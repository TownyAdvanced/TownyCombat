package io.github.townyadvanced.townycombat.settings;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.objects.HeldItemsCombination;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatSettings {

	private static List<HeldItemsCombination> tacticalInvisibilityItems = new ArrayList<>();
    private static Map<Material, Double> infantryMaterialEncumbrancePercentageMap = new HashMap<>();
    private static Map<Material, Double> cavalryMaterialEncumbrancePercentageMap = new HashMap<>();
    

	public static void loadReloadCachedSetting() {
		//Load/reload tactical invisibility items
		tacticalInvisibilityItems.clear();
		try {
			String itemsListAsString = Settings.getString(ConfigNodes.TACTICAL_INVISIBILITY_TRIGGERING_ITEMS);
			String[] itemsListAsArray = itemsListAsString.split(",");
			String[] itemPair;
			boolean ignoreOffHand;
			boolean ignoreMainHand;
			Material offHandItem;
			Material mainHandItem;

			for (String itemAsString : itemsListAsArray) {
				itemPair = itemAsString.trim().split("\\|");

				if(itemPair[0].equalsIgnoreCase("any")) {
					ignoreOffHand = true;
					offHandItem = null;
				} else if (itemPair[0].equalsIgnoreCase("empty")){
					ignoreOffHand = false;
					offHandItem = Material.AIR;
				} else{
					ignoreOffHand = false;
					offHandItem = Material.matchMaterial(itemPair[0]);
				}

				if(itemPair[1].equalsIgnoreCase("any")) {
					ignoreMainHand = true;
					mainHandItem = null;
				} else if (itemPair[1].equalsIgnoreCase("empty")){
					ignoreMainHand = false;
					mainHandItem = Material.AIR;
				} else{
					ignoreMainHand = false;
					mainHandItem = Material.matchMaterial(itemPair[1]);
				}

				tacticalInvisibilityItems.add(
					new HeldItemsCombination(offHandItem,mainHandItem,ignoreOffHand,ignoreMainHand));
			}
		} catch (Exception e) {
			TownyCombat.severe("Problem reading map hiding items list. The list is config.yml may be misconfigured.");
			e.printStackTrace();
		}

		//Load/reload encumbrance values
		infantryMaterialEncumbrancePercentageMap.clear();

        infantryMaterialEncumbrancePercentageMap.put(Material.SHIELD, TownyCombatSettings.getInfantryArmourEncumbranceShield()); 

        infantryMaterialEncumbrancePercentageMap.put(Material.LEATHER_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationLeather() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.LEATHER_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationLeather() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.LEATHER_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationLeather() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.LEATHER_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationLeather() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.CHAINMAIL_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationChainmail() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.CHAINMAIL_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationChainmail() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.CHAINMAIL_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationChainmail() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.CHAINMAIL_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationChainmail() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.TURTLE_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationTurtleShell() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.GOLDEN_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationGold() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.GOLDEN_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationGold() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.GOLDEN_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationGold() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.GOLDEN_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationGold() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.IRON_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.IRON_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.IRON_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.IRON_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.DIAMOND_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.DIAMOND_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.DIAMOND_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.DIAMOND_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationIron() / 100); 

        infantryMaterialEncumbrancePercentageMap.put(Material.NETHERITE_HELMET, TownyCombatSettings.getInfantryArmourEncumbranceBaseHelmet() * TownyCombatSettings.getInfantryArmourEncumbranceModificationNetherite() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.NETHERITE_CHESTPLATE, TownyCombatSettings.getInfantryArmourEncumbranceBaseChestplate() * TownyCombatSettings.getInfantryArmourEncumbranceModificationNetherite() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.NETHERITE_LEGGINGS, TownyCombatSettings.getInfantryArmourEncumbranceBaseLeggings() * TownyCombatSettings.getInfantryArmourEncumbranceModificationNetherite() / 100); 
        infantryMaterialEncumbrancePercentageMap.put(Material.NETHERITE_BOOTS, TownyCombatSettings.getInfantryArmourEncumbranceBaseBoots() * TownyCombatSettings.getInfantryArmourEncumbranceModificationNetherite() / 100); 		

		//Load/reload encumbrance values
		cavalryMaterialEncumbrancePercentageMap.clear();
		cavalryMaterialEncumbrancePercentageMap.put(Material.LEATHER_HORSE_ARMOR, 10d);
		cavalryMaterialEncumbrancePercentageMap.put(Material.GOLDEN_HORSE_ARMOR, 10d);
		cavalryMaterialEncumbrancePercentageMap.put(Material.IRON_HORSE_ARMOR, 10d);
		cavalryMaterialEncumbrancePercentageMap.put(Material.DIAMOND_HORSE_ARMOR, 10d);
	}

	public static boolean isTownyCombatEnabled() {
		return Settings.getBoolean(ConfigNodes.TOWNY_COMBAT_ENABLED);
	}

	public static boolean isTeleportMountWithPlayerEnabled() {
		return Settings.getBoolean(ConfigNodes.HORSE_ENHANCEMENTS_TELEPORT_MOUNT_WITH_PLAYER_ENABLED);
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
		
	public static boolean isTacticalInvisibilityEnabled() {
		return Settings.getBoolean(ConfigNodes.TACTICAL_INVISIBILITY_ENABLED);
	}

	public static boolean getTacticalInvisibilityModeAutomaticModeEnabled() {
		return Settings.getBoolean(ConfigNodes.TACTICAL_INVISIBILITY_AUTOMATIC_MODE_ENABLED);
	}
	
	public static boolean isTacticalInvisibilityModeAutomaticModeScopeWilderness() {
		return Settings.getBoolean(ConfigNodes.TACTICAL_INVISIBILITY_AUTOMATIC_MODE_SCOPE_WILDERNESS);
	}

	public static boolean isTacticalInvisibilityModeAutomaticModeScopeRuins() {
		return Settings.getBoolean(ConfigNodes.TACTICAL_INVISIBILITY_AUTOMATIC_MODE_SCOPE_RUINS);
	}

	public static boolean isTacticalInvisibilityTriggeringEnabled() {
		return Settings.getBoolean(ConfigNodes.TACTICAL_INVISIBILITY_TRIGGERING_ENABLED);
	}

	public static List<HeldItemsCombination> getTacticalInvisibilityTriggeringItems() {
		return tacticalInvisibilityItems;
	}

	public static boolean isEncumbranceEnabled() {
		return Settings.getBoolean(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_ENABLED);
	}
	
	public static double getInfantryArmourEncumbranceShield() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SHIELD);
	}
	
	public static double getInfantryArmourEncumbranceBaseHelmet() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_HELMET);
	}
	
	public static double getInfantryArmourEncumbranceBaseChestplate() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_CHESTPLATE);
	}
	
	public static double getInfantryArmourEncumbranceBaseLeggings() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_LEGGINGS);
	}
	
	public static double getInfantryArmourEncumbranceBaseBoots() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_BOOTS);
	}
	
	public static double getInfantryArmourEncumbranceModificationLeather() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_LEATHER);
	}
	
	public static double getInfantryArmourEncumbranceModificationChainmail() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_CHAINMAIL);
	}

	public static double getInfantryArmourEncumbranceModificationTurtleShell() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_TURTLE_SHELL);
	}

	public static double getInfantryArmourEncumbranceModificationGold() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_GOLD);
	}		

	public static double getInfantryArmourEncumbranceModificationIron() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_IRON);
	}
	
	public static double getInfantryArmourEncumbranceModificationDiamond() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_DIAMOND);
	}		

	public static double getInfantryArmourEncumbranceModificationNetherite() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_NETHERITE);
	}		

	public static Map<Material, Double> getInfantryMaterialEncumbrancePercentageMap() {
		return infantryMaterialEncumbrancePercentageMap;
	}

	public static Map<Material, Double> getCavalryMaterialEncumbrancePercentageMap() {
		return cavalryMaterialEncumbrancePercentageMap;
	}

	public static double getGenericInfantrySpeedAdjustmentPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_GENERIC_INFANTRY_ADJUSTMENT_PERCENTAGE);
	}
	
	public static double getGenericCavalrySpeedAdjustmentPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_GENERIC_CAVALRY_ADJUSTMENT_PERCENTAGE);
	}

	public static double getDamageModificationAllWeaponsPercentage() {
		return Settings.getDouble(ConfigNodes.DAMAGE_ADJUSTMENTS_ALL_WEAPONS_PERCENTAGE);
	}
	
	public static double getEncumbranceRiderContributionPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_RIDER_CONTRIBUTION_PERCENTAGE);
	}

}
