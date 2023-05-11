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
			"# |                   CAVALRY ENHANCEMENTS               | #",
			"# |                                                      | #",
			"# | Overview: Cavalry combat in vanilla minecraft is     | #",
			"# | very poor. This feature resolves that, and makes     | #",
			"# | cavalry a powerful force on the battlefield.         | #",
			"# |                                                      | #",
			"# | IMPORTANT TIP:                                       | #",
			"# | 1. Even with this feature enabled, cavalry will      | #",
			"# | often be ineffective in melee, due to horse-head     | #",
			"# | obstruction and lag.                                 | #",
			"# | 2. So players should not expect to be doing          | #",
			"# | regular traditional cavalry charges.                 | #",
			"# | 3. Instead, this feature empowers cavalry as         | #",
			"# | fast and tough missile-based-troops,                 | #",
			"# | similar to tanks on a modern battlefield.            | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	CAVALRY_ENHANCEMENTS_ENABLED(
			"cavalry_enhancements.enabled",
			"false",
			"",
			"# If true, then the below cavalry enhancements are enabled.",
			"# TIP: Due to limitations in vanilla minecraft, particularly the horses head obstructing the view, and lag,",
			"# this "),
	CAVALRY_ENHANCEMENTS_TELEPORT_MOUNT_WITH_PLAYER_ENABLED(
			"cavalry_enhancements.teleport_mount_with_player_enabled",
			"true",
			"",
			"# If true, then when a player uses /n or /t spawn, their mount (e.g. a horse) comes with them.",
			"# After the player spawns, the mount takes 5 seconds to arrive.",
			"# TIP: The setting is essential to allow cavalry to get to battlefields."),
	CAVALRY_ENHANCEMENTS_ATTACK_DAMAGE_RESISTANCE_PERCENT(
			"cavalry_enhancements.attack_damage_resistance_percent",
			"70",
			"",
			"# This setting makes mounted-horses more resistant to attack damage from players.",
			"# TIP: This setting is essential to make cavalry viable in battle, because in vanilla MC they are very weak."),
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
			"# If this setting is true, then horses do not rear up when damaged, except from fire or bushes.",
			"# TIP: This setting is essential as otherwise horses become useless if hit by a flaming or poisoned arrow etc."),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS(
			"cavalry_enhancements.cavalry_power_shot",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                    CAVALRY POWER SHOT                | #",
			"# +------------------------------------------------------+ #",
			""),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_ENABLED(
			"cavalry_enhancements.cavalry_power_shot.enabled",
			"true",
			"",
			"# If this setting is true, then the Cavalry Power Shot is enabled:",
			"# - Effects:",
			"#   - Increased Strength for a single shot",
			"# - Cooldown:",
			"#   - Starts in cooldown when a player mounts a horse.",
			"#   - Goes into cooldown when the rider strikes a player/mob with their weapon.",
			"# - TIP: This setting, in combination with the above ones, gives cavalry the role of 'Tanks' on the battlefield:",
			"#   - Fast.",
			"#   - Tough.",
			"#   - Powerful Shot with long reload time.",
			"#"),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_EFFECT_LEVEL(
			"cavalry_enhancements.cavalry_power_shot.effect_level",
			"3",
			"",
			"# The level of the Cavalry Power Shot Strength bonus.",
			"# Note: If the Battlefield Roles feature is enabled, this setting is ignored."),
	CAVALRY_ENHANCEMENTS_CAVALRY_STRENGTH_BONUS_COOLDOWN_SECONDS(
			"cavalry_enhancements.cavalry_power_shot.cooldown_time_seconds",
			"10",
			"",
			"# The cooldown of the Cavalry Power Shot."),
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
			"false",
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
			"false",
			"",
			"# If this value is true, players who die near to towns, keep inventory.",
			"# TIP: This is great for a more casual, less hardcore battle experience",
			"# TIP: This also helps to develop a healthier geopolitics, as powerful nations snowball less"),
	KEEP_STUFF_ON_DEATH_KEEP_EXPERIENCE_ENABLED(
			"keep_stuff_on_death.keep_level_enabled",
			"false",
			"",
			"# If this value is true, players who die near to towns, keep level.",
			"# TIP: This is great for a more casual, less hardcore battle experience",
			"# TIP: This also helps to develop a healthier geopolitics, as powerful nations snowball less"),
	KEEP_STUFF_ON_DEATH_TOOLS_DEGRADE_PERCENTAGE(
			"keep_stuff_on_death.tools_degrade_percentage",
			"5",
			"",
			"# The percentage by which a player's tools (including swords & armour) degrade when they die."),
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
			"false",
			"",
			"# If this value is true, PVP combat is unlocked for regular players. See user guide for details."),
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
	BATTLEFIELD_ROLES_SUPER_POTIONS_DAILY_GENERATION_RATE(
			"unlock_pvp_combat_for_regular_players.battlefield_roles.super_potions_daily_generation_rate",
			"3",
			"",
			"# This value determines how many super potions are generated per day.",
			"# A value of 0 will disable super potions completely."),
	POTION_TRANSMUTER(
			"unlock_pvp_combat_for_regular_players.potion_transmuter",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                 POTION TRANSMUTER                    | #",
			"# +------------------------------------------------------+ #",
			""),
	POTION_TRANSMUTER_ENABLED(
			"unlock_pvp_combat_for_regular_players.potion_transmuter.enabled",
			"true",
			"",
			"# If true, the potion transmuter feature is enabled. See user guide for details."),
	POTION_TRANSMUTER_AMPLIFICATION_ADJUSTMENT(
			"unlock_pvp_combat_for_regular_players.potion_transmuter.amplification_adjustment",
			"0",
			"",
			"# This value determines the adjustment to the amplification on potion transmute.",
			"# A value of 0 means Healing-I/Healing-II potions become Regen-I/Regen-II",
			"# A value of 1 means Healing-I/Healing-II potions become Regen-II/Regen-III",
			"# A value of -1 means means Healing-I/Healing-II potions become Regen-I/Regen-I"),
	POTION_TRANSMUTER_TRANSMUTED_POTION_DURATION_SECONDS(
			"unlock_pvp_combat_for_regular_players.potion_transmuter.transmuted_potion_duration_seconds",
			"15",
			"",
			"# This value determines the duration of the transmuted potions.",
			"# TIP 1: A value of 15 means that each potions restores +50% than its source potion",
			"# TIP 2: Do not set this value below 10, or you will start to negate the point of this feature,",
			"#        by making potion usage a fast-usage-and-high-skill activity"),
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
			"false",
			"",
			"# if true, then the spear effect is enabled."),
	NEW_ITEMS_SPEAR_CUSTOM_MODEL_DATA_ID(
			"new_items.spear.custom_model_data_id",
			"-1",
			"",
			"# If this setting is not -1, then the identification-by-lore is disabled, and spears are located by the given customModelData id."),
	NEW_ITEMS_SPEAR_BONUS_DAMAGE_VS_CAVALRY(
			"new_items.spear.bonus_damage_vs_cavalry",
			"9",
			"",
			"# Bonus damage to cavalry (horse or rider). Integer only,",
			"# If Battlefield roles are enabled, this config is ignored, and the value is set at 9"),
	NEW_ITEMS_SPEAR_NATIVE_WEAPON_ENABLED(
			"new_items.spear.native_weapon_enabled",
			"true",
			"",
			"# If true, then whenever a player crafts using the vanilla wooden sword recipe, they get a spear instead.");
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
