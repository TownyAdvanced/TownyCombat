package io.github.townyadvanced.townycombat.utils;

import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatExperienceUtil {

    public static void keepExperience(PlayerDeathEvent event) {
        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }
}
