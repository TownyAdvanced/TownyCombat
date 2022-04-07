package io.github.townyadvanced.townycombat.commands;


import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.bukkit.towny.utils.NameUtil;
import com.palmergames.bukkit.util.ChatTools;
import io.github.townyadvanced.townycombat.enums.TownyCombatPermissionNodes;
import io.github.townyadvanced.townycombat.settings.Settings;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TownyCombatAdminCommand implements TabExecutor {

	private static final List<String> townyCombatAdminTabCompletes = Arrays.asList("reload");

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args[0].toLowerCase()) {
		default:
			if (args.length == 1)
				return NameUtil.filterByStart(townyCombatAdminTabCompletes, args[0]);
			else
				return Collections.emptyList();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		parseAdminCommand(sender, args);
		return true;
	}

	private void parseAdminCommand(CommandSender sender, String[] args) {
		/*
		 * Parse Command.
		 */
		if (args.length > 0) {
			if (sender instanceof Player && !((Player)sender).hasPermission(TownyCombatPermissionNodes.TOWNYCOMBAT_ADMIN_COMMAND.getNode(args[0]))) {
				Messaging.sendErrorMsg(sender, Translatable.of("msg_err_command_disable"));
				return;
			}
			switch (args[0]) {
				case "reload":
					parseReloadCommand(sender);
				break;
				/*
				 * Show help if no command found.
				 */
				default:
					showHelp(sender);
			}
		} else {
			if (sender instanceof Player
					&& !((Player)sender).hasPermission(TownyCombatPermissionNodes.TOWNYCOMBAT_ADMIN_COMMAND.getNode())) {
				Messaging.sendErrorMsg(sender, Translatable.of("msg_err_command_disable"));
				return;
			}
			showHelp(sender);
		}
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(ChatTools.formatTitle("/townycombatadmin"));
		sender.sendMessage(ChatTools.formatCommand("Eg", "/tca", "reload", Translatable.of("admin_help_reload").forLocale(sender)));
	}

	private void parseReloadCommand(CommandSender sender) {
		try {
			Settings.loadConfig();
			Settings.loadLanguages();
			TownyCombatSettings.loadReloadCachedSetting();
			Messaging.sendMsg(sender, Translatable.of("config_and_lang_file_reloaded_successfully"));			
		} catch (Exception e) {
			Messaging.sendErrorMsg(sender, Translatable.of("config_and_lang_file_could_not_be_reloaded"));
			e.printStackTrace();
		}		
	}
}

