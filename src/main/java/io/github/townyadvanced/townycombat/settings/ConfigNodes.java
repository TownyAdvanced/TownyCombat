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
	HORSE_ENHANCEMENTS(
		"horse_enhancements",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                   HORSE ENHANCEMENTS                 | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	HORSE_ENHANCEMENTS_TELEPORT_MOUNT_WITH_PLAYER_ENABLED(
			"horse_enhancements.teleport_mount_with_player_enabled",
			"true",
			"",
			"# If true, then when a player uses /n or /t spawn, their mount (e.g. a horse) comes with them.",
			"# After the player spawns, the mount takes 5 seconds to arrive.",
			"# TIP: The setting helps enable cavalry tactics, by making it easier to bring horses to battles."),
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
	DAMAGE_ADJUSTMENTS(
		"damage_adjustments",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                 DAMAGE ADJUSTMENTS                   | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	DAMAGE_ADJUSTMENTS_ALL_WEAPONS_PERCENTAGE(
			"damage_adjustments.all_weapons_percentage",
			"-30",
			"",
			"# This setting adjusts the damage of all weapons (including fists)",
			"# TIP: If armour slowing is enabled, this setting is important, as players will generally have less armour."),
	SPEED_ADJUSTMENTS(
			"speed_adjustments",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                   SPEED ADJUSTMENTS                    | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	SPEED_ADJUSTMENTS_INFANTRY(
			"speed_adjustments.infantry",
			"",
			"",
			"",
			"# +------------------------------------------------------+ #",
			"# |                      INFANTRY                        | #",
			"# +------------------------------------------------------+ #",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_GENERIC(
			"speed_adjustments.infantry.generic",
			"",
			"",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_GENERIC_ADJUSTMENT_PERCENTAGE(
			"speed_adjustments.infantry.generic.adjustment_percentage",
			"10",
			"",
			"# Adjusts the movement speed of all players.",
			"# TIP: Giving all players a small speed boost helps sugar-coat the concept of armour-based speed-slowing."),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING(
			"speed_adjustments.infantry.armour_slowing",
			"",
			"",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_ENABLED(
			"speed_adjustments.infantry.armour_slowing.enabled",
			"true",
			"",
			"# If this value is true, armour slowing is enabled.",
			"# TIP 1: Armour-Slowing significantly boost the tactical options available to armies.",
			"# TIP 2: Armour-Slowing allows for the introduction of combat-useful national uniforms.",
			"# e.g. each nation may design its own national kit, including different materials, and (with leather items) colours."),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage",
			"",
			"",
			"# The base slow percentage for each type of armour item."),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_SHIELD(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage.shield",
			"4",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_HELMET(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage.helmet",
			"0.6",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_CHESTPLATE(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage.chestplate",
			"1.6",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_LEGGINGS(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage.leggings",
			"1.2",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_BASE_ITEM_PERCENTAGE_BOOTS(
			"speed_adjustments.infantry.armour_slowing.base_item_percentage.boots",
			"0.6",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage",
			"",
			"",
			"# The modification to slowing, of each material."),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_LEATHER(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.leather",
			"100",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_CHAINMAIL(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.chainmail",
			"200",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_TURTLE_SHELL(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.turtle_shell",
			"300",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_GOLD(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.gold",
			"400",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_IRON(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.iron",
			"500",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_DIAMOND(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.diamond",
			"600",
			""),
	SPEED_ADJUSTMENTS_INFANTRY_ARMOUR_SLOWING_MATERIAL_MODIFICATION_PERCENTAGE_NETHERITE(
			"speed_adjustments.infantry.armour_slowing.material_modification_percentage.netherite",
			"700",
			"");

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
