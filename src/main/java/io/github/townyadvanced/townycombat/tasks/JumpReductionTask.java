package io.github.townyadvanced.townycombat.tasks;

import com.palmergames.bukkit.towny.Towny;
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
        Map<Player, Double> playerArmourSlowPercentages = TownyCombatMovementUtil.getPlayerArmourSlowPercentages();
        Double playerArmourSlowPercentage;
        for(Player player: Bukkit.getOnlinePlayers()) {
            velocityY = player.getVelocity().getY();  
            if(velocityY != GRAVITY_VELOCITY && velocityY != LADDER_VELOCITY && velocityY > 0) {
                //Player is jumping or ascending an incline                
                /*
                 * If they have heavy armour, apply a temporary slow + jump-stop
                 * Effects starts at armour sloe 16%, when it is 0.5 seconds
                 * Every 4% slow after that, adds 0.5 seconds
                 * These numbers are hardcoded for simplicity & to avoid server misconfiguration.
                 */
                playerArmourSlowPercentage = playerArmourSlowPercentages.get(player);                
                if(playerArmourSlowPercentage != null && playerArmourSlowPercentage >= 16) {
                     final int effectDurationTicks = (int)((((playerArmourSlowPercentage / 4) -3) * 10) + 0.5);  
                    Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), new Runnable() {
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectDurationTicks, 2));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, effectDurationTicks, -2));                            
                        }
                    });
                }
            }
        }
    }
}
