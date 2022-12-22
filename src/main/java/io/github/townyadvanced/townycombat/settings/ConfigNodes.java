package io.github.townyadvanced.townycombat.settings;

public enum ConfigNodes {
	
	VERSION_HEADER("version", "", ""),
	VERSION(
			"version.version",
			"",
			"# This is the current version.  Please do not edit."),
	LAST_RUN_VERSION(
			"version.last_run_version",
			"",
			"# This is for showing the changelog on updates.  Please do not edit."),
	LANGUAGE("language",
			"english.yml",
			"# The language file you wish to use"),
	TOWNY_COMBAT_ENABLED(
			"towny_combat_enabled",
			"true",
			"",
			"# Enables the entire plugin."),
	TOWNY_COMBAT_MOVEMENT_MODIFICATIONS_ENABLED(
			"movement_modifications_enabled",
			"true",
			"",
			"# Enables the entire movements-modifications system."),

	CAVALRY_ENHANCEMENTS(
		"cavalry_enhancements",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                   CAVALRY ENHANCEMENTS                 | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	CAVALRY_ENHANCEMENTS_TELEPORT_MOUNT_WITH_PLAYER_ENABLED(
			"cavalry_enhancements.teleport_mount_with_player_enabled",
			"true",
			"",
			"# If true, then when a player uses /n or /t spawn, their mount (e.g. a horse) comes with them.",
			"# After the player spawns, the mount takes 5 seconds to arrive.",
			"# TIP: The setting is essential to allow cavalry to get to battlefields."),
	CAVALRY_ENHANCEMENTS_GENERIC_CAVALRY_ADJUSTMENT_PERCENTAGE(
			"cavalry_enhancements.generic_speed_adjustment_percent",
			"12",
			"",
			"# Adjusts the walking speed of all player mounts.",
			"# TIP: Giving all player mounts a small speed boost helps sugar-coat the concept of encumbrance."),
	CAVALRY_ENHANCEMENTS_ATTACK_DAMAGE_RESISTANCE_PERCENT(
			"cavalry_enhancements.attack_damage_resistance_percent",
			"60",
			"",
			"# This setting makes mounted-horses more resistant to attack damage from players.",
			"# TIP: This setting is essential to make cavalry viable in battle, because in vanilla MC they are very weak."),
	CAVALRY_ENHANCEMENTS_KNOCKBACK_RESISTANCE_PERCENT(
			"cavalry_enhancements.knockback_resistance_percent",
			"75",
			"",
			"# This setting makes mounted-horses resistant to knockback.",
			"# TIP: This setting helps cavalry to avoid getting knocked into holes/ganks etc."),
	CAVALRY_ENHANCEMENTS_CAVALRY_MISSILE_SHIELD_ENABLED(
			"cavalry_enhancements.cavalry_missile_shield_enabled",
			"true",
			"",
			"# If this setting is true, then horses and riders are both shielded from arrows, except from crossbows.",
			"# TIP: This setting is essential to allow cavalry to perform the 'Tank' role, because due to lag effects, horses would otherwise be quite vulnerable to arrows."),
	CAVALRY_ENHANCEMENTS_HORSE_REARING_PREVENTION_ENABLED(
			"cavalry_enhancements.horse_rearing_prevention_enabled",
			"true",
			"",
			"# If this setting is true, then horses do not rear up when damaged.",
			"# TIP: This setting is essential as otherwise horses become useless if set on fire, poisoned etc."),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS(
			"cavalry_enhancements.cavalry_strength_bonus",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                 CAVALRY STRENGTH BONUS               | #",
			"# +------------------------------------------------------+ #",
			""),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_ENABLED(
			"cavalry_enhancements.cavalry_strength_bonus.enabled",
			"true",
			"",
			"# If this setting is true, then the Cavalry Strength Bonus is enabled:",
			"# - Effects:",
			"#   - Strength v.s. infantry.",
			"# - Cooldown:",
			"#   - Starts in cooldown when a player mounts a horse.",
			"#   - Goes into cooldown when the rider strikes an unmounted player/mob with their weapon. (melee or ranged)",
			"# - TIP: This setting, in combination with the above ones, gives cavalry the role of 'Tanks' on the battlefield:",
			"#   - Fast.",
			"#   - Tough.",
			"#   - Powerful Shot with long reload time.",
			"#   - Good vs infantry",
			"#   - Counterable by other cavalry, or specialized infantry (spearmen)",
			"#"),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_EFFECT_LEVEL(
			"cavalry_enhancements.cavalry_strength_bonus.effect_level",
			"3",
			"",
			"# The level of the bonus strength effect."),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_COOLDOWN_SECONDS(
			"cavalry_enhancements.cavalry_strength_bonus.cooldown_time_seconds",
			"10",
			"",
			"# The bonus effect cooldown."),
	INFANTRY_ENHANCEMENTS(
		"infantry_enhancements",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                   INFANTRY ENHANCEMENTS                 | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	INFANTRY_ENHANCEMENTS_GENERIC_SPEED_ADJUSTMENT_PERCENT(
			"infantry_enhancements.generic_speed_adjustment_percent",
			"12",
			"",
			"# Adjusts the walking speed of all players.",
			"# TIP: Giving all players a small speed boost helps sugar-coat the concept of encumbrance."),
	INFANTRY_ENHANCEMENTS_KNOCKBACK_RESISTANCE_PERCENT(
			"infantry_enhancements.knockback_resistance_percent",
			"25",
			"",
			"# This setting makes players resistant to knockback.",
			"# TIP: This setting helps players to avoid getting knocked into holes/ganks etc."),
	BLOCK_GLITCHING_PREVENTION(
		"block_glitching_prevention",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |             BLOCK GLITCHING PREVENTION               | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	BLOCK_GLITCHING_PREVENTION_ENABLED(
			"block_glitching_prevention.enabled",
			"true",
			"",
			"# If this value is true, then some block glitching is prevented.",
			"# Any player whose block-place-or-destroy action is cancelled, gets teleported back to where they were when they attempted the action.",
			"# TIP: This feature will not prevent all wall-bypass cheating methods, but it should help."),
	BLOCK_GLITCHING_PREVENTION_TELEPORT_DELAY_MILLIS(
			"block_glitching_prevention.teleport_delay_millis",
			"500",
			"",
			"# This value determines the delay before teleport.",
			"# TIP: If you set this value very low, you might get lag."),
	KEEP_STUFF_ON_DEATH(
		"keep_stuff_on_death",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                  KEEP STUFF ON DEATH                 | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	KEEP_STUFF_ON_DEATH_KEEP_INVENTORY_ENABLED(
			"keep_stuff_on_death.keep_inventory_enabled",
			"true",
			"",
			"# If this value is true, players who die near to towns, keep inventory.",
			"# TIP: This is great for a more casual, less hardcore battle experience",
			"# TIP: This also helps to develop a healthier geopolitics, as powerful nations snowball less"),
	KEEP_STUFF_ON_DEATH_KEEP_EXPERIENCE_ENABLED(
			"keep_stuff_on_death.keep_level_enabled",
			"true",
			"",
			"# If this value is true, players who die near to towns, keep level.",
			"# TIP: This is great for a more casual, less hardcore battle experience",
			"# TIP: This also helps to develop a healthier geopolitics, as powerful nations snowball less"),
	KEEP_STUFF_ON_DEATH_TOOLS_DEGRADE_PERCENTAGE(
			"keep_stuff_on_death.tools_degrade_percentage",
			"20",
			"",
			"# The percentage by which a player's tools (including swords & armour) degrade when they die.",
		    "# TIP: Don't set this too low or battles could get crezzy."),
	KEEP_STUFF_ON_DEATH_TOWN_PROXIMITY_BLOCKS(
			"keep_stuff_on_death.town_proximity_blocks",
			"400",
			"",
			"# A player must be at least this close to a non-ruined town to keep inventory+experience on death."),			
	TACTICAL_INVISIBILITY(
			"tactical_invisibility",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |           TACTICAL INVISIBILITY (T.I.V)              | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	TACTICAL_INVISIBILITY_ENABLED(
			"tactical_invisibility.enabled",
			"false",
			"",
			"# If this setting is true, then the Tactical Invisibility (T.I.V) feature is enabled.",
			"# This feature allow players to disappear from the dynmap, allowing military tactics.",
			"# PREREQUISITE: Dynmap plugin",
			"# ACTIVATION_TIME: When a player meets the conditions for map-hiding, the effect takes a few seconds to activate (same for de-activation)."),
	TACTICAL_INVISIBILITY_AUTOMATIC_MODE_ENABLED(
			"tactical_invisibility.automatic_mode.enabled",
			"true",
			"",
			"# If this setting is true, then the automatic Tactical Invisibility mode is enabled"),
	TACTICAL_INVISIBILITY_AUTOMATIC_MODE_SCOPE_WILDERNESS(
			"tactical_invisibility.automatic_mode.scope.wilderness",
			"true",
			"",
			"# If true, then players are hidden when in wilderness."),
	TACTICAL_INVISIBILITY_AUTOMATIC_MODE_SCOPE_RUINS(
			"tactical_invisibility.automatic_mode.scope.ruins",
			"false",
			"",
			"# If true, then players are hidden when in ruins."),
	TACTICAL_INVISIBILITY_TRIGGERING_ENABLED(
			"tactical_invisibility.triggering.enabled",
			"false",
			"",
			"# If this setting is true, then it is possible for individual players to trigger Tactical Invisibility,",
			"# by equipping a specific combination of items."),
	TACTICAL_INVISIBILITY_TRIGGERING_ITEMS(
			"tactical_invisibility.triggering.items",
			"diamond_sword|diamond_sword, diamond_axe|diamond_axe, diamond_shovel|diamond_shovel, bow|bow",
			"",
			"# This config only applies when triggering is enabled",
			"# This list specifies the item combinations which allow players to map-hide.",
			"# Each list entry is in the form of <off-hand>|<main-hand>.",
			"# ",
			"# To specify that both items are required - e.g. 'compass|painting'", 
			"# To specify that only one item is required - e.g. 'compass|any'",
			"# To specify that one hand must be empty - e.g. 'compass|empty'",
			"# ",
			"# This list allows a server to grant usage of the feature to different categories of players.",
			"# Example 1:  An entry with 'shield|diamond_sword' grants the feature to soldiers.",
			"# Example 2:  An entry with 'compass|diamond_sword' grants the feature to scouts/explorers.",
			"# Example 3:  An entry with 'compass|air' grants the feature to very peaceful explorers.",
			"# Example 4:  An entry with 'compass|any' grants the feature to many players including builders/miners/lumberjacks."),	
	ENCUMBRANCE(
			"encumbrance",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                     ENCUMBRANCE                      | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	ENCUMBRANCE_ENABLED(
			"encumbrance.enabled",
			"true",
			"",
			"# If true, encumbrance is enabled."),
	ENCUMBRANCE_INFANTRY(
			"encumbrance.infantry",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                      INFANTRY                        | #",
			"# +------------------------------------------------------+ #",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE(
			"encumbrance.infantry.base_percentage",
			"",
			"",
			"# The base slow percentage for each type of item."),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_BOW(
			"encumbrance.infantry.base_percentage.bow",
			"3",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SHIELD(
			"encumbrance.infantry.base_percentage.shield",
			"4",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SPEAR(
			"encumbrance.infantry.base_percentage.spear",
			"5",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_CROSSBOW(
			"encumbrance.infantry.base_percentage.cross_bow",
			"12",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_WARHAMMER(
			"encumbrance.infantry.base_percentage.warhammer",
			"12",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_SHULKER_BOX(
			"encumbrance.infantry.base_percentage.shulker_box",
			"15",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_ENDER_CHEST(
			"encumbrance.infantry.base_percentage.ender_chest",
			"30",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_HELMET(
			"encumbrance.infantry.base_percentage.helmet",
			"0.6",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_CHESTPLATE(
			"encumbrance.infantry.base_percentage.chestplate",
			"1.6",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_LEGGINGS(
			"encumbrance.infantry.base_percentage.leggings",
			"1.2",
			""),
	ENCUMBRANCE_INFANTRY_BASE_ITEM_PERCENTAGE_BOOTS(
			"encumbrance.infantry.base_percentage.boots",
			"0.6",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE(
			"encumbrance.infantry.modification_percentage",
			"",
			"",
			"# The modification to encumbrance, of each material."),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_LEATHER(
			"encumbrance.infantry.modification_percentage.leather",
			"100",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_CHAINMAIL(
			"encumbrance.infantry.modification_percentage.chainmail",
			"200",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_TURTLE_SHELL(
			"encumbrance.infantry.modification_percentage.turtle_shell",
			"300",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_GOLD(
			"encumbrance.infantry.modification_percentage.gold",
			"300",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_IRON(
			"encumbrance.infantry.modification_percentage.iron",
			"400",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_DIAMOND(
			"encumbrance.infantry.modification_percentage.diamond",
			"500",
			""),
	ENCUMBRANCE_INFANTRY_MATERIAL_MODIFICATION_PERCENTAGE_NETHERITE(
			"encumbrance.infantry.modification_percentage.netherite",
			"600",
			""),
	ENCUMBRANCE_INFANTRY_JUMP_DAMAGE(
			"encumbrance.infantry.jump_damage",
			"",
			""),
	ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_ENABLED(
			"encumbrance.infantry.jump_damage.enabled",
			"true",
			"",
			"# If enabled, player with heavy armour sometimes take damage when they jump or ascend an incline.",
			"# TIP: This setting stop players from exploiting/encumbrance-bypassing by 'bunny hopping' their way all over the battlefield."),
	ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_THRESHOLD(
			"encumbrance.infantry.jump_damage.threshold",
			"8",
			"",
			"# Integer only",
			"# Players only take jump damage when their encumbrance is above this threshold.",
			"# TIP: With default settings, jump damage will only apply to players carrying the equivalent of a full chain-mail set or heavier."),
	ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_DAMAGE_PER_ENCUMBRANCE_PERCENT(
			"encumbrance.infantry.jump_damage.damage_per_encumbrance_percent",
			"0.07",
			""),
	ENCUMBRANCE_INFANTRY_JUMP_DAMAGE_WARNING_INTERVAL_MINUTES(
			"encumbrance.infantry.jump_damage.warning_interval_minutes",
			"60",
			""),
	ENCUMBRANCE_CAVALRY(
			"encumbrance.cavalry",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                      CAVALRY                         | #",
			"# +------------------------------------------------------+ #",
			""),
	ENCUMBRANCE_CAVALRY_REDUCTION_PERCENTAGE(
			"encumbrance.cavalry.reduction_percentage",
			"50",
			"",
			"# Horses, being stronger, can carry more weight, thus their encumbrance is reduced"),
	ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE(
			"encumbrance.cavalry.percentage",
			"",
			"",
			"# The slow percentage for each type of cavalry armour item."),
	ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_LEATHER_HORSE_ARMOUR(
			"encumbrance.cavalry.percentage.leather_horse_armour",
			"8",
			""),
	ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_GOLD_HORSE_ARMOUR(
			"encumbrance.cavalry.percentage.gold_horse_armour",
			"24",
			""),
	ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_IRON_HORSE_ARMOUR(
			"encumbrance.cavalry.percentage.iron_horse_armour",
			"32",
			""),
	ENCUMBRANCE_CAVALRY_ITEM_PERCENTAGE_DIAMOND_HORSE_ARMOUR(
			"encumbrance.cavalry.percentage.diamond_horse_armour",
			"40",
			""),
	NEW_ITEMS(
		"new_items",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                     NEW ITEMS                        | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	NEW_ITEMS_SPEAR(
			"new_items.spear",
			"",
			""),
	NEW_ITEMS_SPEAR_ENABLED(
			"new_items.spear.enabled",
			"true",
			"",
			"# if true, then the spear effect is enabled."),
	NEW_ITEMS_SPEAR_LORE(
			"new_items.spear.lore",
			"+9 Damage v.s. Cavalry",
			"",
			"# It an item has this text for any of its lore lines, it gets the spear effect."),
	NEW_ITEMS_SPEAR_CUSTOM_MODEL_DATA_ID(
			"new_items.spear.custom_model_data_id",
			"-1",
			"",
			"# If this setting is not -1, then the identification-by-lore is disabled, and spears are located by the given customModelData id."),
	NEW_ITEMS_SPEAR_BONUS_DAMAGE_VS_CAVALRY(
			"new_items.spear.bonus_damage_vs_cavalry",
			"9",
			"",
			"# Bonus damage to cavalry (horse or rider). Integer only"),
	NEW_ITEMS_SPEAR_NATIVE_WEAPON_ENABLED(
			"new_items.spear.native_weapon_enabled",
			"true",
			"",
			"# If true, then native spears TCM can be crafted, and regular wooden swords cannot.",
			"# See user guide for recipe."),
	NEW_ITEMS_SPEAR_NATIVE_WEAPON_NAME(
			"new_items.spear.native_weapon_name",
			"Spear",
			"",
			"# The name of the native weapon, when crafted."),
	NEW_ITEMS_WARHAMMER(
			"new_items.warhammer",
			"",
			""),
	NEW_ITEMS_WARHAMMER_ENABLED(
			"new_items.warhammer.enabled",
			"true",
			"",
			"# if true, then the warhammer effect is enabled."),
	NEW_ITEMS_WARHAMMER_LORE(
			"new_items.warhammer.lore",
			"15% Chance to Break Shield",
			"",
			"# It an item has this text for any of its lore lines, it gets the warhammer effect"),
	NEW_ITEMS_WARHAMMER_CUSTOM_MODEL_DATA_ID(
			"new_items.spear.custom_model_data_id",
			"-1",
			"",
			"# If this setting is not -1, then the identification-by-lore is disabled, and warhammers are located by the given customModelData id."),
	NEW_ITEMS_WARHAMMER_SHERLD_BREAK_CHANCE_PERCENT(
			"new_items.warhammer.shield_break_chance_percent",
			"15",
			"",
			"# Percentage chance to break shield. Integer only."), 
	NEW_ITEMS_WARHAMMER_NATIVE_WEAPON_ENABLED(
			"new_items.warhammer.native_weapon_enabled",
			"true",
			"",
			"# If true, then native spears TCM warhammers be crafted, and regular wooden axes cannot.",
			"# See user guide for recipe."),
	NEW_ITEMS_WARHAMMER_NATIVE_WEAPON_NAME(
			"new_items.warhammer.native_weapon_name",
			"Spear",
			"",
			"# The name of the native weapon, when crafted."),
	AUTOPOTTING(
		"autopotting",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                    AUTO-POTTING                      | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	AUTOPOTTING_ENABLED(
			"autopotting.enabled",
			"true",
			"",
			"# If enabled, splash health potions (I and II) are automatically consumed if a player's health falls below a certain threshold.",
			"# ",
			"# TIP 1: ",
			"# This setting unlocks battlefield participation for the majority of Towny players,",
			"# specifically new-players/casual-players/traders/builders/roleplayers,",
			"# who are otherwise almost completely excluded from battles, by an unreasonably high inventory-management skill bar.",
			"# ",
			"# TIP 2: ",
			"# Protests can be expected from a vocal minority",
			"# namely those members of the elite PVP caste who have already cleared the participation bar, ",
			"# and are willing to continue excluding the majority of players from battles, in exchange for holding on to their own power.",
			"# ",
			"# TIP 3: ",
			"# More progressive PVP'ers will recognize both a challenge and an opportunity,",
			"# to dominate the battlefield less by twitchy young fingers,",
			"# and more by solid army organization, reliable logistics and clever military tactics."),
	AUTOPOTTING_HEALTH_THRESHOLD(
			"autopotting.health_threshold",
			"10",
			"",
			"# Once health falls below this threshold, splash-health potions will be automatically consumed just before the next damage event.");

	private final String Root;
	private final String Default;
	private String[] comments;

	ConfigNodes(String root, String def, String... comments) {

		this.Root = root;
		this.Default = def;
		this.comments = comments;
	}

	/**
	 * Retrieves the root for a config option
	 *
	 * @return The root for a config option
	 */
	public String getRoot() {

		return Root;
	}

	/**
	 * Retrieves the default value for a config path
	 *
	 * @return The default value for a config path
	 */
	public String getDefault() {

		return Default;
	}

	/**
	 * Retrieves the comment for a config path
	 *
	 * @return The comments for a config path
	 */
	public String[] getComments() {

		if (comments != null) {
			return comments;
		}

		String[] comments = new String[1];
		comments[0] = "";
		return comments;
	}

}
