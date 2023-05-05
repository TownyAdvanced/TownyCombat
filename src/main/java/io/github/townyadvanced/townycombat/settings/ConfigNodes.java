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
			"# etc."),
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
	UNLOCK_PVP_COMBAT_FOR_REGULAR_PLAYERS(
			"unlock_pvp_combat_for_regular_players",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |        UNLOCK PVP COMBAT FOR REGULAR PLAYERS         | #",
			"# +------------------------------------------------------+ #",
			"# + In early 2023, PVP Combat has become so advanced,    + #",
			"# + that players without elite training / equipment,     + #",
			"# + are effectively locked out from participating in     + #",
			"# + meaningful combat.                                   + #",
			"# +                                                      + #",
			"# + This is slowing down the growth of many servers,     + #",
			"# + as many old/new players cannot participate in wars   + #",
			"# +                                                      + #",
			"# + This Feature Fixes the Problem:                      + #",
			"# +                                                      + #",
			"# + 1. Removes excessive weapons/armour grinding time:   + #",
			"# +    Fighters can choose 'lighter' battlefield roles,  + #",
			"# +    in which they don't need expensive equipment.     + #",
			"# +                                                      + #",
			"# + 2. Removes excessive potion grinding time:           + #",
			"# +    Healing potions last longer, so fewer are needed. + #",
			"# +                                                      + #",
			"# + 3. Removes excessive combat-training time:           + #",
			"# +    Healing potions are much easier to use.           + #",
			"############################################################",
			""),
	UNLOCK_PVP_COMBAT_FOR_REGULAR_PLAYERS_ENABLED(
			"unlock_pvp_combat_for_regular_players.enabled",
			"true",
			"",
			"# If true, PVP combat is unlocked for regular players. See user guide for details."),
	BATTLEFIELD_ROLES(
			"unlock_pvp_combat_for_regular_players.battlefield_roles",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                 BATTLEFIELD ROLES                    | #",
			"# +------------------------------------------------------+ #",
			""),
	BATTLEFIELD_ROLES_ENABLED(
			"unlock_pvp_combat_for_regular_players.battlefield_roles.enabled",
			"true",
			"",
			"# If true, battlefield roles are enabled. See user guide for details."),
	BATTLEFIELD_ROLES_MINIMUM_TIME_BETWEEN_ROLE_CHANGES_DAYS(
			"unlock_pvp_combat_for_regular_players.battlefield_roles.time_between_role_changes_days",
			"7.0",
			"",
			"# This value determines the minimum time between each role-change."),
	BATTLEFIELD_ROLES_SUPER_POTIONS_ENABLED(
			"unlock_pvp_combat_for_regular_players.battlefield_roles.super_potions_enabled",
			"true",
			"",
			"# If true, super potions are enabled for players with battlefield roles. See user guide for details."),
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
			"Warhammer",
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
