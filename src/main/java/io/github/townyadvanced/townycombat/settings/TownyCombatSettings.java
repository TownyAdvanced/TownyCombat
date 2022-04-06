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
    private static Map<Material, Double> materialSlowPercentageMap = new HashMap<>();

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

		//Load/reload material slow items
		materialSlowPercentageMap.clear();

        materialSlowPercentageMap.put(Material.SHIELD, TownyCombatSettings.getArmourSlowingBaseItemPercentageShield()); 

        materialSlowPercentageMap.put(Material.LEATHER_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageLeather() / 100); 
        materialSlowPercentageMap.put(Material.LEATHER_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageLeather() / 100); 
        materialSlowPercentageMap.put(Material.LEATHER_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageLeather() / 100); 
        materialSlowPercentageMap.put(Material.LEATHER_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageLeather() / 100); 

        materialSlowPercentageMap.put(Material.CHAINMAIL_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageChainmail() / 100); 
        materialSlowPercentageMap.put(Material.CHAINMAIL_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageChainmail() / 100); 
        materialSlowPercentageMap.put(Material.CHAINMAIL_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageChainmail() / 100); 
        materialSlowPercentageMap.put(Material.CHAINMAIL_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageChainmail() / 100); 

        materialSlowPercentageMap.put(Material.TURTLE_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageTurtleShell() / 100); 

        materialSlowPercentageMap.put(Material.GOLDEN_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageGold() / 100); 
        materialSlowPercentageMap.put(Material.GOLDEN_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageGold() / 100); 
        materialSlowPercentageMap.put(Material.GOLDEN_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageGold() / 100); 
        materialSlowPercentageMap.put(Material.GOLDEN_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageGold() / 100); 

        materialSlowPercentageMap.put(Material.IRON_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageIron() / 100); 
        materialSlowPercentageMap.put(Material.IRON_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageIron() / 100); 
        materialSlowPercentageMap.put(Material.IRON_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageIron() / 100); 
        materialSlowPercentageMap.put(Material.IRON_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageIron() / 100); 

        materialSlowPercentageMap.put(Material.DIAMOND_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageDiamond() / 100); 
        materialSlowPercentageMap.put(Material.DIAMOND_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageDiamond() / 100); 
        materialSlowPercentageMap.put(Material.DIAMOND_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageDiamond() / 100); 
        materialSlowPercentageMap.put(Material.DIAMOND_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageDiamond() / 100); 

        materialSlowPercentageMap.put(Material.NETHERITE_HELMET, TownyCombatSettings.getArmourSlowingBaseItemPercentageHelmet() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageNetherite() / 100); 
        materialSlowPercentageMap.put(Material.NETHERITE_CHESTPLATE, TownyCombatSettings.getArmourSlowingBaseItemPercentageChestplate() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageNetherite() / 100); 
        materialSlowPercentageMap.put(Material.NETHERITE_LEGGINGS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageLeggings() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageNetherite() / 100); 
        materialSlowPercentageMap.put(Material.NETHERITE_BOOTS, TownyCombatSettings.getArmourSlowingBaseItemSlowPercentageBoots() * TownyCombatSettings.getArmourSlowingMaterialModificationPercentageNetherite() / 100); 		
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

	public static boolean isArmourSlowingEnabled() {
		return Settings.getBoolean(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_ENABLED);
	}
	
	public static double getArmourSlowingBaseItemPercentageShield() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_SHIELD);
	}
	
	public static double getArmourSlowingBaseItemPercentageHelmet() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_HELMET);
	}
	
	public static double getArmourSlowingBaseItemPercentageChestplate() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_CHESTPLATE);
	}
	
	public static double getArmourSlowingBaseItemSlowPercentageLeggings() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_LEGGINGS);
	}
	
	public static double getArmourSlowingBaseItemSlowPercentageBoots() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_BOOTS);
	}
	
	public static double getArmourSlowingMaterialModificationPercentageLeather() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_LEATHER);
	}
	
	public static double getArmourSlowingMaterialModificationPercentageChainmail() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_CHAINMAIL);
	}

	public static double getArmourSlowingMaterialModificationPercentageTurtleShell() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_TURTLE_SHELL);
	}

	public static double getArmourSlowingMaterialModificationPercentageGold() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_GOLD);
	}		

	public static double getArmourSlowingMaterialModificationPercentageIron() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_IRON);
	}
	
	public static double getArmourSlowingMaterialModificationPercentageDiamond() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_DIAMOND);
	}		

	public static double getArmourSlowingMaterialModificationPercentageNetherite() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_NETHERITE);
	}		

	public static Map<Material, Double> getMaterialSlowPercentageMap() {
		return materialSlowPercentageMap;
	}

	public static double getGenericInfantrySpeedAdjustmentPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_INFANTRY_GENERIC_ADJUSTMENT_PERCENTAGE);		
	}
}
