package io.github.townyadvanced.townycombat.tasks;

import com.palmergames.bukkit.towny.Towny;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

/**
 * This class/task exists to compensate for a bug in vanilla Mineceaft,
 * in which players can bypass slow-effects by jumping forward.
 * 
 * This class compensates by applying a temporary extra slow, and a jump nerf,
 * if a player in heavy armour jumps or ascends an incline.
 */
public class TownyCombatTask extends BukkitRunnable {

    public void run() {
        if(TownyCombatSettings.isEncumbranceEnabled()) {
            TownyCombatMovementUtil.slowEncumberedJumpingPlayers();
        }
        if(TownyCombatSettings.isCavalryChargingEnabled()) {
            //TownyCombatMovementUtil.slowEncumberedJumpingPlayers();
        }
        
    
    }
}
