package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.bukkit.towny.object.Translation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.util.Colors;

import io.github.townyadvanced.townycombat.TownyCombat;

import java.util.List;

public class Messaging {

    final static String prefix = Translation.of("plugin_prefix");
    
	public static void sendErrorMsg(CommandSender sender, String message) {
		//Ensure the sender is not null (i.e. is an online player who is not an npc)
        if(sender != null)
	        sender.sendMessage(prefix + Colors.Red + message);
	}

	public static void sendMsg(CommandSender sender, String message) {
        //Ensure the sender is not null (i.e. is an online player who is not an npc)
        if(sender != null)
    		sender.sendMessage(prefix + Colors.White + message);
	}
	
	public static void sendGlobalMessage(String message) {
		TownyCombat.info(message);
		Bukkit.getOnlinePlayers().stream()
				.filter(p -> p != null)
				.filter(p -> TownyAPI.getInstance().isTownyWorld(p.getLocation().getWorld()))
				.forEach(p -> sendMsg(p, message));
	}

	public static void sendGlobalMessage(String header, List<String> lines) {
		TownyCombat.info(header);
		for(String line: lines) {
			TownyCombat.info(line);
		}
		for(Player player: Bukkit.getOnlinePlayers()) {
			if(player != null && TownyAPI.getInstance().isTownyWorld(player.getLocation().getWorld())) {
				player.sendMessage(prefix + header);
				for(String line: lines) {
					player.sendMessage(line);
				}
			}
		}
	}
	
	public static void sendErrorMsg(CommandSender sender, Translatable... messages) {
		// Ensure the sender is not null (i.e. is an online player who is not an npc)
		if (sender != null)
			sender.sendMessage(prefix + Colors.Red + Translation.translateTranslatables(sender, messages));
	}

	public static void sendMsg(CommandSender sender, Translatable... messages) {
		// Ensure the sender is not null (i.e. is an online player who is not an npc)
		if (sender != null)
			sender.sendMessage(prefix + Colors.White + Translation.translateTranslatables(sender, messages));
	}
	
	public static void sendGlobalMessage(Translatable message) {
		TownyCombat.info(message.defaultLocale());
		Bukkit.getOnlinePlayers().stream()
				.filter(p -> p != null)
				.filter(p -> TownyAPI.getInstance().isTownyWorld(p.getLocation().getWorld()))
				.forEach(p -> sendMsg(p, message));
	}

	public static void sendGlobalMessage(Translatable header, List<Translatable> lines) {
		TownyCombat.info(header.defaultLocale());
		for(Translatable line: lines) {
			TownyCombat.info(line.defaultLocale());
		}
		for(Player player: Bukkit.getOnlinePlayers()) {
			if(player != null && TownyAPI.getInstance().isTownyWorld(player.getLocation().getWorld())) {
				player.sendMessage(prefix + header.forLocale(player));
				for(Translatable line: lines) {
					sendMsg(player, line);
				}
			}
		}
	}
	
	public static void sendGlobalMessage(Translatable[] lines) {
		for(Translatable line: lines) {
			TownyCombat.info(line.defaultLocale());
		}
		for(Player player: Bukkit.getOnlinePlayers()) {
			if(player != null && TownyAPI.getInstance().isTownyWorld(player.getLocation().getWorld())) {
				for(Translatable line: lines) {
					sendMsg(player, line);
				}
			}
		}
	}
}
