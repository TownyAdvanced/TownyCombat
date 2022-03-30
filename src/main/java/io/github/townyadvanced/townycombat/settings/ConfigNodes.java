package io.github.townyadvanced.townycombat.settings;

public enum ConfigNodes {
	
	VERSION_HEADER("version", "", ""),
	VERSION(
			"version.version",
			"",
			"# This is the current version.  Please do not edit."),
	LANGUAGE("language",
			"english.yml",
			"# The language file you wish to use"),
	TOWNY_COMBAT_ENABLED(
			"towny_combat_enabled",
			"true",
			"",
			"# etc."),
	TELEPORT_MOUNT_WITH_PLAYER(
		"teleport_mount_with_player",
			"",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |             TELEPORT MOUNT WITH PLAYER               | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	TELEPORT_MOUNT_WITH_PLAYER_ENABLED(
			"teleport_mount_with_player.enabled",
			"true",
			"",
			"# If true, then when a player uses /n or /t spawn, their mount (e.g. a horse) comes with them.",
			"# After the player spawns, the mount takes 5 seconds to arrive.",
			"# TIP: The setting helps enable cavalry tactics, by making it easier to get horses to Siege-Zones."),
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
			"500",
			"",
			"# A player must be at least this close to a non-ruined town to keep inventory+experience on death.");
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
