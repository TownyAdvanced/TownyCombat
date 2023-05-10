package io.github.townyadvanced.townycombat.listeners;


import com.palmergames.adventure.text.Component;
import com.palmergames.adventure.text.event.HoverEvent;
import com.palmergames.bukkit.towny.event.statusscreen.ResidentStatusScreenEvent;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translation;
import com.palmergames.bukkit.towny.object.Translator;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyCombatStatusScreenListener implements Listener {

	@SuppressWarnings("unused")
	private final TownyCombat plugin;

	public TownyCombatStatusScreenListener(TownyCombat instance) {
		this.plugin = instance;
	}
	/**
	 * Show the resident's military role
	 * 
	 * @param event the ResidentStatusScreenEvent
	 */
	@EventHandler
	public void onResidentStatusScreen(ResidentStatusScreenEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled() || !TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() || !TownyCombatSettings.isBattlefieldRolesEnabled())
			return;

		//Create the hover item subcomponents
		final Translator translator = Translator.locale(event.getCommandSender());
		BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(event.getResident());
		Component hoverItemContentSubComponent = getBattlefieldRoleHoverItemContentComponent(event.getResident(), battlefieldRole, translator);
		Component hoverItemButtonSubComponent = getBattlefieldRoleHoverItemButtonComponent(battlefieldRole, translator);

		//Create the hover item
		Component hoverItemComponent = Component.empty()
				.append(hoverItemButtonSubComponent)
				.hoverEvent(HoverEvent.showText(hoverItemContentSubComponent));

		//Add the hover item to the screen
		event.getStatusScreen().addComponentOf("townyCombat_battlefieldRole", hoverItemComponent);
	}

	private Component getBattlefieldRoleHoverItemContentComponent(Resident resident, BattlefieldRole battlefieldRole, Translator translator) {
		Component text = Component.empty();
		int numSuperPotions = TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate();
		String translationKey = battlefieldRole.toString().toLowerCase();
		
		//Armour & Weapons
		text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_armour")));
		text = text.append(Component.newline());
		text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_melee_weapons")));
		text = text.append(Component.newline());
		text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_missile_weapons")));
		
		//Passive(s)
		switch(battlefieldRole) {
			case LIGHT_INFANTRY:
			case LIGHT_CAVALRY:
			case HEAVY_CAVALRY:
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_passive_ability_A")));
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_passive_ability_B")));
				break;
			default:
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_passive_ability")));
				break;
		}

		//Disadvantage
		switch(battlefieldRole) {
			case MEDIUM_INFANTRY:
			case MEDIUM_CAVALRY:
			case HEAVY_INFANTRY:
			case HEAVY_CAVALRY:
				text = text.append(Component.newline());
				text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_disadvantage")));
				break;
		}

		//Super Potions
		if (TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
			text = text.append(Component.newline());
			text = text.append(Component.text(translator.of("status_resident_content_" + translationKey + "_super_potion", numSuperPotions)));
		}
		
		//Time Until Next Role Change
		long timeUntilNextRoleChange = TownyCombatBattlefieldRoleUtil.getTimeUntilNextRoleChange(resident);
		if(timeUntilNextRoleChange > 0) {
			String timeUntilNextRoleChangeFormatted = TimeMgmt.getFormattedTimeValue(timeUntilNextRoleChange);
			text = text.append(Component.newline());
			text = text.append(Component.text(translator.of("status_resident_content_time_until_next_role_change", timeUntilNextRoleChangeFormatted)));
		}
		return text;
	}

	private Component getBattlefieldRoleHoverItemButtonComponent(BattlefieldRole battlefieldRole, Translator translator) {
		String rawString = translator.of("status_resident_hover_title_battlefield_role");
		String battlefieldRoleString = translator.of(battlefieldRole.getNameKey());
		return Component.text(String.format(rawString,
				Translation.of("status_format_hover_bracket_colour"),
				battlefieldRoleString,
				Translation.of("status_format_hover_bracket_colour")));
	}

}
