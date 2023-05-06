package io.github.townyadvanced.townycombat.settings;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.objects.HeldItemsCombination;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatSettings {

	private static List<HeldItemsCombination> tacticalInvisibilityItems = new ArrayList<>();
  	private static int cavalryChargeEffectDurationTicks = 0;
	private static int cavalryChargeCooldownMilliseconds = 0;

	public static void loadReloadCachedSetting() {
		//Load cavalry charge ticks
		cavalryChargeEffectDurationTicks = (int)((TownyCombatSettings.getCavalryStrengthBonusCooldownSeconds() + 5) * 20);
		cavalryChargeCooldownMilliseconds = (int)(TownyCombatSettings.getCavalryStrengthBonusCooldownSeconds() * 1000);

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
	}

	public static boolean isTownyCombatEnabled() {
		return Settings.getBoolean(ConfigNodes.TOWNY_COMBAT_ENABLED);
	}

	public static boolean isTeleportMountWithPlayerEnabled() {
		return Settings.getBoolean(ConfigNodes.CAVALRY_ENHANCEMENTS_TELEPORT_MOUNT_WITH_PLAYER_ENABLED);
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
	
	public static double getAttackDamageResistanceHorsesPercent() {
		return Settings.getDouble(ConfigNodes.CAVALRY_ENHANCEMENTS_ATTACK_DAMAGE_RESISTANCE_PERCENT);
	}

	public static boolean isNewItemsSpearEnabled() {
		return Settings.getBoolean(ConfigNodes.NEW_ITEMS_SPEAR_ENABLED);
	}

	public static String getNewItemsSpearLore() {
		return Settings.getString(ConfigNodes.NEW_ITEMS_SPEAR_LORE);
	}

	public static int getNewItemsSpearCustomModelDataID() {
		return Settings.getInt(ConfigNodes.NEW_ITEMS_SPEAR_CUSTOM_MODEL_DATA_ID);
	}

	public static int getNewItemsSpearBonusDamageVsCavalry() {
		return Settings.getInt(ConfigNodes.NEW_ITEMS_SPEAR_BONUS_DAMAGE_VS_CAVALRY);
	}

    public static boolean isNewItemsSpearNativeWeaponEnabled() {
		return Settings.getBoolean(ConfigNodes.NEW_ITEMS_SPEAR_NATIVE_WEAPON_ENABLED);
	}

    public static String getNewItemsSpearNativeWeaponName() {
		return Settings.getString(ConfigNodes.NEW_ITEMS_SPEAR_NATIVE_WEAPON_NAME);
	}

	public static boolean isCavalryMissileShieldEnabled() {
		return Settings.getBoolean(ConfigNodes.CAVALRY_ENHANCEMENTS_CAVALRY_MISSILE_SHIELD_ENABLED);
	}
	
	public static boolean isHorseRearingPreventionEnabled() {
		return Settings.getBoolean(ConfigNodes.CAVALRY_ENHANCEMENTS_HORSE_REARING_PREVENTION_ENABLED);
	}

	public static boolean isCavalryStrengthBonusEnabled() {
		return Settings.getBoolean(ConfigNodes.CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_ENABLED);
	}

	public static int getCavalryChargeStrengthBonusEffectLevel() {
		return Settings.getInt(ConfigNodes.CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_EFFECT_LEVEL);
	}

	public static double getCavalryStrengthBonusCooldownSeconds() {
		return Settings.getDouble(ConfigNodes.CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_COOLDOWN_SECONDS);
	}

	public static int getCavalryChargeCooldownMilliseconds() {
		return cavalryChargeCooldownMilliseconds;
	}

	public static int getCavalryChargeEffectDurationTicks() {
		return cavalryChargeEffectDurationTicks;
	}
	
	public static boolean isUnlockCombatForRegularPlayersEnabled() {
		return Settings.getBoolean(ConfigNodes.UNLOCK_PVP_COMBAT_FOR_REGULAR_PLAYERS_ENABLED);
	}

	public static boolean isBattlefieldRolesEnabled() {
		return Settings.getBoolean(ConfigNodes.BATTLEFIELD_ROLES_ENABLED);
	}

	public static boolean isBattlefieldRolesSuperPotionsEnabled() {
		return getBattlefieldRolesSuperPotionsDailyGenerationRate() > 0;
	}

	public static double getBattlefieldRolesMinimumTimeBetweenRoleChangesDays() {
		return Settings.getDouble(ConfigNodes.BATTLEFIELD_ROLES_MINIMUM_TIME_BETWEEN_ROLE_CHANGES_DAYS);
	}

	public static int getBattlefieldRolesSuperPotionsDailyGenerationRate() {
		return Settings.getInt(ConfigNodes.BATTLEFIELD_ROLES_SUPER_POTIONS_DAILY_GENERATION_RATE);
	}

	public static int getSuperPotionsScheduledGrantHour() {
		return Settings.getInt(ConfigNodes.BATTLEFIELD_ROLES_SUPER_POTIONS_SCHEDULED_GRANT_HOUR);
	}
}
