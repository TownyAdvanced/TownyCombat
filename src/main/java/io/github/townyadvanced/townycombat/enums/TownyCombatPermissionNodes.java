package io.github.townyadvanced.townycombat.enums;

/**
 * 
 * @author Goosius
 *
 */
public enum TownyCombatPermissionNodes {

	// ----- Admin Command Nodes -----
	TOWNYCOMBAT_ADMIN_COMMAND("townycombat.admin.command.*"),
		TOWNYCOMBAT_ADMIN_COMMAND_RELOAD("townycombat.admin.command.reload");

	private String value;

	/**
	 * Constructor
	 * 
	 * @param permission - Permission.
	 */
	TownyCombatPermissionNodes(String permission) {
		this.value = permission;
	}

	/**
	 * Retrieves the permission node
	 * 
	 * @return The permission node
	 */
	public String getNode() {
		return value;
	}

	/**
	 * Retrieves the permission node
	 * replacing the character *
	 * 
	 * @param replace - String
	 * @return The permission node
	 */
	public String getNode(String replace) {
		return value.replace("*", replace);
	}

	public String getNode(int replace) {
		return value.replace("*", replace + "");
	}

}
