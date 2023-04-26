package io.github.townyadvanced.townycombat.listeners;


import com.palmergames.adventure.text.Component;
import com.palmergames.adventure.text.event.HoverEvent;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.event.statusscreen.NationStatusScreenEvent;
import com.palmergames.bukkit.towny.event.statusscreen.ResidentStatusScreenEvent;
import com.palmergames.bukkit.towny.event.statusscreen.TownStatusScreenEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyWorld;
import com.palmergames.bukkit.towny.object.Translation;
import com.palmergames.bukkit.towny.object.Translator;
import com.palmergames.util.StringMgmt;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TownyCombatStatusScreenListener implements Listener {

	/**
	 * Show the resident's military role
	 * 
	 * @param event the ResidentStatusScreenEvent
	 */
	@EventHandler
	public void onResidentStatusScreen(ResidentStatusScreenEvent event) {
		if(TownyCombatSettings.isTownyCombatEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {

			//Create the hover item subcomponents
			BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(event.getResident());
			final Translator translator = Translator.locale(event.getCommandSender());
			Component hoverItemContentSubComponent = getBattlefieldRoleHoverItemContentComponent(battlefieldRole, translator);
			Component hoverItemButtonSubComponent = getBattlefieldRoleHoverItemButtonComponent(battlefieldRole, translator);

			//Create the hover item
			Component hoverItemComponent = Component.empty()
					.append(hoverItemButtonSubComponent)
					.hoverEvent(HoverEvent.showText(hoverItemContentSubComponent));

			//Add the hover item to the screen
			event.getStatusScreen().addComponentOf("townyCombat_battlefieldRole", hoverItemComponent);
		}
	}

	private Component getBattlefieldRoleHoverItemContentComponent(BattlefieldRole battlefieldRole, Translator translator) {
		Component text = Component.empty();
		switch(battlefieldRole) {
			case LIGHT:
				text = text.append(Component.text(translator.of("status_resident_content_light_line_1")));
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_light_line_2")));
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_light_line_3")));
				if(TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
					text = text.append(Component.newline());
					text = text.append(Component.text(translator.of("status_resident_content_light_super_potion_line")));
				}
				break;
			default:
				throw new RuntimeException("Unknown battlefield role");
		}
		return text;
	}

	private Component getBattlefieldRoleHoverItemButtonComponent(BattlefieldRole battlefieldRole, Translator translator) {
		String rawString = translator.of("status_resident_hover_title_battlefield_role");
		String battlefieldRoleString = translator.of(battlefieldRole.getNameKey());
		return Component.text(String.format(rawString,
				Translation.of("status_format_hover_bracket_colour"),
				Translation.of("status_format_hover_key"),
				battlefieldRoleString,
				Translation.of("status_format_hover_bracket_colour")));
	}

}
