package io.github.townyadvanced.townycombat.tasks;

import com.palmergames.bukkit.towny.Towny;
import io.github.townyadvanced.townycombat.TownyCombat;
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
public class JumpReductionTask extends BukkitRunnable {

    private static final double GRAVITY_VELOCITY = -0.0784000015258789;  //A players has this when stationary or travelling horizontally
    private static final double LADDER_VELOCITY = -0.22540001022815717; //A player has this when going up OR down a ladder

    public void run() {
        double velocityY;        
        Map<Player, Double> playerEncumbrancePercentageMap = TownyCombatMovementUtil.getPlayerEncumbrancePercentageMap();
        Double playerEncumbrancePercentage;
        for(Player player: Bukkit.getOnlinePlayers()) {
            velocityY = player.getVelocity().getY();  
            if(velocityY != GRAVITY_VELOCITY && velocityY != LADDER_VELOCITY && velocityY > 0) {
                /*
                 * Player is jumping or ascending an incline
                 *
                 * If they have heavy armour, apply a temporary effect of: slow + jump-nerf
                 * Effects starts at 8% encumbrance, with an effect duration of 1 second (20 ticks)
                 * Every 4% encumbrance after that, adds a effect duration of 0.4 seconds (8 tics)
                 * These numbers are hardcoded for simplicity & to avoid server misconfiguration.
                 */
                playerEncumbrancePercentage = playerEncumbrancePercentageMap.get(player);
                if(playerEncumbrancePercentage != null && playerEncumbrancePercentage >= 8) {
                    final int effectDurationTicks = (int)(20  + (((playerEncumbrancePercentage / 4) -2) * 8));
                    TownyCombat.getPlugin().getServer().getScheduler().runTask(TownyCombat.getPlugin(), ()-> applyEffects(player, effectDurationTicks));
               }
            }
        }
    }

    private void applyEffects(Player player, int effectDurationTicks) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectDurationTicks, 4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, effectDurationTicks, -30));
    }
}
