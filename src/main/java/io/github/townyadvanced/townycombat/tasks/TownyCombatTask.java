package io.github.townyadvanced.townycombat.tasks;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatHorseUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * This class/task governs all TownyCombat features which require shorter intervals than the Towny-Short-Interval.
 */
public class TownyCombatTask extends BukkitRunnable {
    private boolean offTick = false;

    /**
     * Start task. Runs every 0.5 seconds
     *
     * @param plugin the plugin
     * @return task object
     */
    public static BukkitTask startTownyCombatTask(TownyCombat plugin) {
        return new TownyCombatTask().runTaskTimerAsynchronously(plugin, 400, 10);
    }

    /**
     * The required task run method
     */
    public void run() {
        if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
        //Execute the cavalry charge refreshes every 1 second.
        if(TownyCombatSettings.isCavalryEnhancementsEnabled()
            && TownyCombatSettings.isCavalryStrengthBonusEnabled()) {
            if(offTick) {
                offTick = false;
            } else {
                offTick = true;
                TownyCombatHorseUtil.refreshAllCavalryStrengthBonuses();
            }
        }
    }
}
