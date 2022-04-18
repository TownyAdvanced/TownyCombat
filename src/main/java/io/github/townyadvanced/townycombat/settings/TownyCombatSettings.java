package io.github.townyadvanced.townycombat.settings;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.objects.HeldItemsCombination;
import io.github.townyadvanced.townycombat.utils.TownyCombatItemUtil;
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
    private static Map<Material, Double> materialEncumbrancePercentageMap = new HashMap<>();
	private static int cavalryChargeEffectDurationTicks = 0;
	private static int cavalryChargeCooldownMilliseconds = 0;

	public static void loadReloadCachedSetting() {
		//Load cavalry charge ticks
		cavalryChargeEffectDurationTicks = (int)((TownyCombatSettings.getCavalryChargeCooldownSeconds() + 5) * 20);
		cavalryChargeCooldownMilliseconds = (int)(TownyCombatSettings.getCavalryChargeCooldownSeconds() * 1000);

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

		//Load/reload encumbrance meterials
		materialEncumbrancePercentageMap.clear();

		//Infantry Armour
        materialEncumbrancePercentageMap.put(Material.LEATHER_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationLeather() / 100); 
        materialEncumbrancePercentageMap.put(Material.LEATHER_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationLeather() / 100); 
        materialEncumbrancePercentageMap.put(Material.LEATHER_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationLeather() / 100); 
        materialEncumbrancePercentageMap.put(Material.LEATHER_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationLeather() / 100); 

        materialEncumbrancePercentageMap.put(Material.CHAINMAIL_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationChainmail() / 100); 
        materialEncumbrancePercentageMap.put(Material.CHAINMAIL_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationChainmail() / 100); 
        materialEncumbrancePercentageMap.put(Material.CHAINMAIL_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationChainmail() / 100); 
        materialEncumbrancePercentageMap.put(Material.CHAINMAIL_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationChainmail() / 100); 

        materialEncumbrancePercentageMap.put(Material.TURTLE_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationTurtleShell() / 100); 

        materialEncumbrancePercentageMap.put(Material.GOLDEN_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationGold() / 100); 
        materialEncumbrancePercentageMap.put(Material.GOLDEN_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationGold() / 100); 
        materialEncumbrancePercentageMap.put(Material.GOLDEN_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationGold() / 100); 
        materialEncumbrancePercentageMap.put(Material.GOLDEN_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationGold() / 100); 

        materialEncumbrancePercentageMap.put(Material.IRON_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationIron() / 100); 
        materialEncumbrancePercentageMap.put(Material.IRON_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationIron() / 100); 
        materialEncumbrancePercentageMap.put(Material.IRON_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationIron() / 100); 
        materialEncumbrancePercentageMap.put(Material.IRON_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationIron() / 100); 

        materialEncumbrancePercentageMap.put(Material.DIAMOND_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationDiamond() / 100); 
        materialEncumbrancePercentageMap.put(Material.DIAMOND_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationDiamond() / 100); 
        materialEncumbrancePercentageMap.put(Material.DIAMOND_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationDiamond() / 100); 
        materialEncumbrancePercentageMap.put(Material.DIAMOND_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationDiamond() / 100); 

        materialEncumbrancePercentageMap.put(Material.NETHERITE_HELMET, getEquipmentEncumbranceBaseHelmet() * getEquipmentEncumbranceModificationNetherite() / 100); 
        materialEncumbrancePercentageMap.put(Material.NETHERITE_CHESTPLATE, getEquipmentEncumbranceBaseChestplate() * getEquipmentEncumbranceModificationNetherite() / 100); 
        materialEncumbrancePercentageMap.put(Material.NETHERITE_LEGGINGS, getEquipmentEncumbranceBaseLeggings() * getEquipmentEncumbranceModificationNetherite() / 100); 
        materialEncumbrancePercentageMap.put(Material.NETHERITE_BOOTS, getEquipmentEncumbranceBaseBoots() * getEquipmentEncumbranceModificationNetherite() / 100); 		
	
		//Cavalry Armour
		materialEncumbrancePercentageMap.put(Material.LEATHER_HORSE_ARMOR, getEquipmentEncumbranceLeatherHorseArmour());
		materialEncumbrancePercentageMap.put(Material.GOLDEN_HORSE_ARMOR, getEquipmentEncumbranceGoldHorseArmour());
		materialEncumbrancePercentageMap.put(Material.IRON_HORSE_ARMOR, getEquipmentEncumbranceIronHorseArmour());
		materialEncumbrancePercentageMap.put(Material.DIAMOND_HORSE_ARMOR, getEquipmentEncumbranceDiamondHorseArmour());

		//Other Items
		materialEncumbrancePercentageMap.put(Material.BOW, getEquipmentEncumbranceBow());
		materialEncumbrancePercentageMap.put(Material.CROSSBOW, getEquipmentEncumbranceCrossbow());
		materialEncumbrancePercentageMap.put(Material.SHIELD, getEquipmentEncumbranceShield());
		materialEncumbrancePercentageMap.put(TownyCombatItemUtil.SPEAR_PLACEHOLDER_MATERIAL, getEquipmentEncumbranceSpear());
		materialEncumbrancePercentageMap.put(TownyCombatItemUtil.WARHAMMER_PLACEHOLDER_MATERIAL, getEquipmentEncumbranceWarhammer());

		//Enderchest & shulker boxes
		materialEncumbrancePercentageMap.put(Material.ENDER_CHEST, getEquipmentEncumbranceEnderChest());
		materialEncumbrancePercentageMap.put(Material.SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.BLACK_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.BLUE_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.BROWN_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.CYAN_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.GRAY_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.GREEN_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.LIGHT_BLUE_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.LIGHT_GRAY_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.LIME_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.MAGENTA_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.ORANGE_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.PINK_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.PURPLE_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.RED_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.WHITE_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
		materialEncumbrancePercentageMap.put(Material.YELLOW_SHULKER_BOX, getEquipmentEncumbranceShulkerBox());
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

	public static double getEquipmentEncumbranceBow() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_BOW);
	}

	public static double getEquipmentEncumbranceCrossbow() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_CROSSBOW);
	}

	public static double getEquipmentEncumbranceShield() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SHIELD);
	}

	public static double getEquipmentEncumbranceBaseHelmet() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_HELMET);
	}
	
	public static double getEquipmentEncumbranceBaseChestplate() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_CHESTPLATE);
	}
	
	public static double getEquipmentEncumbranceBaseLeggings() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_LEGGINGS);
	}
	
	public static double getEquipmentEncumbranceBaseBoots() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_BOOTS);
	}
	
	public static double getEquipmentEncumbranceModificationLeather() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_LEATHER);
	}
	
	public static double getEquipmentEncumbranceModificationChainmail() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_CHAINMAIL);
	}

	public static double getEquipmentEncumbranceModificationTurtleShell() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_TURTLE_SHELL);
	}

	public static double getEquipmentEncumbranceModificationGold() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_GOLD);
	}		

	public static double getEquipmentEncumbranceModificationIron() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_IRON);
	}
	
	public static double getEquipmentEncumbranceModificationDiamond() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_DIAMOND);
	}		

	public static double getEquipmentEncumbranceModificationNetherite() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_NETHERITE);
	}		
	
	public static double getEquipmentEncumbranceLeatherHorseArmour() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_LEATHER_HORSE_ARMOUR);		
	}

	public static double getEquipmentEncumbranceGoldHorseArmour() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_GOLD_HORSE_ARMOUR);		
	}

	public static double getEquipmentEncumbranceIronHorseArmour() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_IRON_HORSE_ARMOUR);		
	}

	public static double getEquipmentEncumbranceDiamondHorseArmour() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_DIAMOND_HORSE_ARMOUR);		
	}

	public static Map<Material, Double> getMaterialEncumbrancePercentageMap() {
		return materialEncumbrancePercentageMap;
	}

	public static double getGenericInfantrySpeedAdjustmentPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_GENERIC_INFANTRY_ADJUSTMENT_PERCENTAGE);
	}
	
	public static double getGenericCavalrySpeedAdjustmentPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_GENERIC_CAVALRY_ADJUSTMENT_PERCENTAGE);
	}

	public static double getDamageAdjustmentAttackOnPlayer() {
		return Settings.getDouble(ConfigNodes.DAMAGE_ADJUSTMENTS_PLAYERS_INCOMING);
	}

	public static double getDamageAdjustmentsAttackOnHorse() {
		return Settings.getDouble(ConfigNodes.DAMAGE_ADJUSTMENTS_HORSES_INCOMING);
	}

	public static double getCavalryEncumbranceReductionPercentage() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_CAVALRY_REDUCTION_PERCENTAGE);
	}

	public static double getEquipmentEncumbranceSpear() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SPEAR);
	}

	public static double getEquipmentEncumbranceWarhammer() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_WARHAMMER);
	}

	public static boolean isNewItemsSpearEnabled() {
		return Settings.getBoolean(ConfigNodes.NEW_ITEMS_SPEAR_ENABLED);
	}

	public static boolean isNewItemsWarhammerEnabled() {
		return Settings.getBoolean(ConfigNodes.NEW_ITEMS_WARHAMMER_ENABLED);
	}

	public static double getEquipmentEncumbranceShulkerBox() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SHULKER_BOX);
	}

	public static double getEquipmentEncumbranceEnderChest() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_ENDER_CHEST);
	}

	public static boolean isAutoPottingEnabled() {
		return Settings.getBoolean(ConfigNodes.AUTOPOTTING_ENABLED);
	}

	public static double getAutoPottingThreshold() {
		return Settings.getDouble(ConfigNodes.AUTOPOTTING_HEALTH_THRESHOLD);
	}

	public static boolean isHorsesImmuneToFire() {
		return Settings.getBoolean(ConfigNodes.HORSE_ENHANCEMENTS_HORSE_FIRE_IMMUNITY_ENABLED);
	}

	public static boolean isCavalryChargeEnabled() {
		return Settings.getBoolean(ConfigNodes.HORSE_ENHANCEMENTS_CAVALRY_CHARGE_ENABLED);
	}

	public static int getCavalryChargeStrengthEffectLevel() {
		return Settings.getInt(ConfigNodes.HORSE_ENHANCEMENTS_CAVALRY_STRENGTH_EFFECT_LEVEL);
	}

	public static double getCavalryChargeCooldownSeconds() {
		return Settings.getDouble(ConfigNodes.HORSE_ENHANCEMENTS_CAVALRY_CHARGE_COOLDOWN_SECONDS);
	}

	public static int getCavalryChargeCooldownMilliseconds() {
		return cavalryChargeCooldownMilliseconds;
	}

	public static int getCavalryChargeEffectDurationTicks() {
		return cavalryChargeEffectDurationTicks;
	}
	
	public static boolean isJumpDamageEnabled() {
		return Settings.getBoolean(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_ENABLED);
	}
	
	public static double getJumpDamageThreshold() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_THRESHOLD);
	}

	public static double getJumpDamageDamagePerEncumbrancePercent() {
		return Settings.getDouble(ConfigNodes.SPEED_ADJUSTMENTS_ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_DAMAGE_PER_ENCUMBRANCE_PERCENT);
	}	
}
