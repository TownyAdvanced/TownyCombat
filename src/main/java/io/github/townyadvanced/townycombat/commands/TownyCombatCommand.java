package io.github.townyadvanced.townycombat.commands;


import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.bukkit.towny.utils.NameUtil;
import com.palmergames.bukkit.util.ChatTools;
import io.github.townyadvanced.townycombat.enums.TownyCombatPermissionNodes;
import io.github.townyadvanced.townycombat.settings.Settings;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.Messaging;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TownyCombatCommand implements TabExecutor {

	private static final List<String> tabCompletes = Arrays.asList("changerole");

	private static final List<String> changeRoleTabCompletes = Arrays.asList("light", "medium", "heavy");
	
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args[0].toLowerCase()) {
			case "changerole":
				if (args.length == 2)
					return NameUtil.filterByStart(changeRoleTabCompletes, args[1]);
				break;
		}
		if (args.length == 1)
			return NameUtil.filterByStart(tabCompletes, args[0]);
		else
			return Collections.emptyList();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		parseCommand(sender, args);
		return true;
	}

	private void parseCommand(CommandSender sender, String[] args) {
		//Return if there is no command
		if (args.length == 0) {
			if (sender instanceof Player
					&& !(sender.hasPermission(TownyCombatPermissionNodes.TOWNYCOMBAT_COMMAND.getNode()))) {
				Messaging.sendErrorMsg(sender, Translatable.of("msg_err_command_disable"));
				return;
			}
			showHelp(sender);
			return;
		}
		//Return if the player doesn't have permission for the command
		if (sender instanceof Player && !sender.hasPermission(TownyCombatPermissionNodes.TOWNYCOMBAT_COMMAND.getNode(args[0]))) {
			Messaging.sendErrorMsg(sender, Translatable.of("msg_err_command_disable"));
			return;
		}
		//Process the command
		switch (args[0]) {
			case "changerole":
				parseChangeRoleCommand(sender, args);
				break;
			default:
				//Show help if no command found
				showHelp(sender);
		}
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(ChatTools.formatTitle("/townycombat"));
		sender.sendMessage(ChatTools.formatCommand("Eg", "/tcm", "changerole", Translatable.of("command_help_changerole").forLocale(sender)));
	}

	private void parseChangeRoleCommand(CommandSender sender, String[] args) {
		if(args.length < 2) {
			showHelp(sender);
			return; //No role included
		}
		if(!changeRoleTabCompletes.contains(args[0].toLowerCase())) {
			showHelp(sender);
			return; //Given role was not valid
		}
		try {
			TownyCombatBattlefieldRoleUtil.processChangeRoleAttempt(sender, args[1]);
		} catch (TownyException townyException) {
			sender.sendMessage(townyException.getMessage());
			townyException.printStackTrace();
		}
	}
}

